package com.bundev.gexplorer_mobile.repo

import android.util.Log
import com.bundev.gexplorer_mobile.data.ApiResource
import com.mapbox.geojson.Geometry
import me.thefen.gexplorerapi.GexplorerApi
import me.thefen.gexplorerapi.RetrofitClient
import me.thefen.gexplorerapi.dtos.DetailedTripDto
import me.thefen.gexplorerapi.dtos.DistrictDto
import me.thefen.gexplorerapi.dtos.LeaderboardEntryDto
import me.thefen.gexplorerapi.dtos.LoginDto
import me.thefen.gexplorerapi.dtos.NewTripDto
import me.thefen.gexplorerapi.dtos.RegisterDto
import me.thefen.gexplorerapi.dtos.StarStatusDto
import me.thefen.gexplorerapi.dtos.TimedPoint
import me.thefen.gexplorerapi.dtos.TripDto
import me.thefen.gexplorerapi.dtos.UserDto
import java.util.UUID

class GexplorerRepository(
    private var _token: String? = null,
    var username: String? = null,
    var id: UUID? = null,
) {
    var districts: Map<UUID, DistrictDto> = mapOf()

    val client = RetrofitClient {
        _token
    }
    val api: GexplorerApi by lazy {
        client.retrofit.create(GexplorerApi::class.java)
    }

    private suspend fun fetchDistricts() {
        if (districts.isNotEmpty())
            return
        val result = getDistricts()
        result.runIfSuccess { districts = result.data!!.associateBy { it.id } }
    }

    private suspend fun <T> apiWrapper(block: suspend (GexplorerRepository) -> T): ApiResource<T> {
        fetchDistricts()
        return ApiResource.fromResult(runCatching {
            block(this)
        })
    }


    suspend fun checkAuth(): ApiResource<Unit> =
        if (api.checkAuth().isSuccessful) {
            ApiResource.Success(Unit)
        } else {
            ApiResource.Error(Exception())
        }


    suspend fun login(loginDto: LoginDto): ApiResource<String> {
        val result = runCatching { api.login(loginDto) }
        return result.fold(
            {
                val user = api.getUser(loginDto.userName)

                _token = it.token
                Log.d("gexapi", "SETTING GODFORSAKEN TOKEN TO $_token")
                username = loginDto.userName
                id = user.id
                ApiResource.Success(it.token)
            },
            {
                Log.d("gexapi", "login failed?? $it")
                ApiResource.Error(Exception())
            }
        )
    }

    suspend fun register(registerDto: RegisterDto): ApiResource<String> {
        val result = runCatching { api.register(registerDto) }
        return result.fold({
            val user = api.getUser(registerDto.userName)

            _token = it.token
            Log.d("gexapi", "SETTING GODFORSAKEN TOKEN TO $_token")
            username = registerDto.userName
            id = user.id
            ApiResource.Success(it.token)
        },
            {
                Log.d("gexapi", "register failed?? $it")
                ApiResource.Error(Exception())
            })
    }

    fun logout() {
        _token = null
        username = null
        id = null
    }

    // this will throw an exception if username is null and therefore produce an ApiResource.Error, which is exactly what we want
    // maybe replace this with a custom error type further down the line
    suspend fun getSelf(): ApiResource<UserDto> = apiWrapper { api.getUser(username!!) }

    suspend fun getDistricts(): ApiResource<List<DistrictDto>> =
        ApiResource.fromResult(runCatching { api.getDistricts() })

    suspend fun getOverallLeaderboard(): ApiResource<Map<Int, LeaderboardEntryDto<Double>>> =
        apiWrapper { api.getOverallLeaderboard() }

    suspend fun getOverallLeaderboard(page: Int): ApiResource<Map<Int, LeaderboardEntryDto<Double>>> =
        apiWrapper { api.getOverallLeaderboard(page) }

    suspend fun getDistrictLeaderboard(districtId: UUID): ApiResource<Map<Int, LeaderboardEntryDto<Double>>> =
        apiWrapper { api.getDistrictLeaderboard(districtId) }

    suspend fun getDistrictLeaderboard(
        districtId: UUID,
        page: Int,
    ): ApiResource<Map<Int, LeaderboardEntryDto<Double>>> =
        apiWrapper { api.getDistrictLeaderboard(districtId, page) }

    suspend fun getUser(username: String): ApiResource<UserDto> =
        apiWrapper { api.getUser(username) }

    suspend fun getUser(id: UUID): ApiResource<UserDto> = apiWrapper { api.getUser(id) }

    suspend fun getUserPolygon(id: UUID): ApiResource<Geometry> =
        apiWrapper { api.getUserPolygon(id) }

    suspend fun getOwnPolygon(): ApiResource<Geometry> =
        apiWrapper { api.getUserPolygon(id!!) }

    suspend fun getTrip(tripId: String): ApiResource<DetailedTripDto> =
        apiWrapper { api.getTrip(UUID.fromString(tripId)) }

    suspend fun getStarredTrips(): ApiResource<List<TripDto>> =
        apiWrapper { api.getStarredTrips() }

    suspend fun setTripStar(tripId: UUID, starStatusDto: StarStatusDto): ApiResource<DetailedTripDto> =
        apiWrapper { api.setTripStar(tripId, starStatusDto) }

    suspend fun sendTrip(timedPoints: List<TimedPoint>): ApiResource<TripDto> =
        apiWrapper { api.sendTrip(NewTripDto(timedPoints)) }
}