package com.bundev.gexplorer_mobile.repo

import com.bundev.gexplorer_mobile.data.ApiResource
import com.mapbox.geojson.Geometry
import me.thefen.gexplorerapi.GexplorerApi
import me.thefen.gexplorerapi.RetrofitClient
import me.thefen.gexplorerapi.dtos.DetailedTripDto
import me.thefen.gexplorerapi.dtos.DistrictDto
import me.thefen.gexplorerapi.dtos.LeaderboardEntryDto
import me.thefen.gexplorerapi.dtos.LoginDto
import me.thefen.gexplorerapi.dtos.UserDto
import java.util.UUID

class GexplorerRepository(token: String?) {
    private var gexplorerApiToken: String? = null
        set(value) {
            field = value
            client.setToken(value)
        }

    var username: String? = null
    private var id: UUID? = null

    val client = RetrofitClient(gexplorerApiToken)
    val api: GexplorerApi by lazy {
        client.retrofit.create(GexplorerApi::class.java)
    }

    init {
        gexplorerApiToken = token
    }

    private suspend fun <T> apiWrapper(block: suspend (GexplorerRepository) -> T): ApiResource<T> {
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


    suspend fun login(loginDto: LoginDto): ApiResource<Unit> {
        val result = runCatching { api.login(loginDto) }.mapCatching {
            val user = api.getUser(loginDto.userName)
            Pair(it, user.id)
        }
        return result.fold(
            {
                gexplorerApiToken = it.first
                username = loginDto.userName
                id = it.second
                ApiResource.Success(Unit)
            },
            { ApiResource.Error(Exception()) }
        )
    }

    // this will throw an exception if username is null and therefore produce an ApiResource.Error, which is exactly what we want
    // maybe replace this with a custom error type further down the line
    suspend fun getSelf(): ApiResource<UserDto> = apiWrapper { api.getUser(username!!) }

    suspend fun getDistricts(): ApiResource<List<DistrictDto>> = apiWrapper { api.getDistricts() }

    suspend fun getOverallLeaderboard(): ApiResource<Map<Int, LeaderboardEntryDto<Double>>> =
        apiWrapper { api.getOverallLeaderboard() }

    suspend fun getOverallLeaderboard(page: Int): ApiResource<Map<Int, LeaderboardEntryDto<Double>>> =
        apiWrapper { api.getOverallLeaderboard(page) }

    suspend fun getDistrictLeaderboard(districtId: UUID): ApiResource<Map<Int, LeaderboardEntryDto<Double>>> =
        apiWrapper { api.getDistrictLeaderboard(districtId) }

    suspend fun getDistrictLeaderboard(
        districtId: UUID,
        page: Int
    ): ApiResource<Map<Int, LeaderboardEntryDto<Double>>> =
        apiWrapper { api.getDistrictLeaderboard(districtId, page) }

    suspend fun getUser(username: String): ApiResource<UserDto> =
        apiWrapper { api.getUser(username) }

    suspend fun getUser(id: UUID): ApiResource<UserDto> = apiWrapper { api.getUser(id) }

    suspend fun getUserPolygon(id: UUID): ApiResource<Geometry> =
        apiWrapper { api.getUserPolygon(id) }

    suspend fun getTrip(tripId: String): ApiResource<DetailedTripDto> =
        apiWrapper { api.getTrip(UUID.fromString(tripId)) }
}