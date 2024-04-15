package me.thefen.gexplorerapi

import com.mapbox.geojson.Polygon
import me.thefen.gexplorerapi.dtos.DetailedTripDto
import me.thefen.gexplorerapi.dtos.DistrictDto
import me.thefen.gexplorerapi.dtos.LeaderboardEntryDto
import me.thefen.gexplorerapi.dtos.LoginDto
import me.thefen.gexplorerapi.dtos.NewTripDto
import me.thefen.gexplorerapi.dtos.RegisterDto
import me.thefen.gexplorerapi.dtos.StarStatusDto
import me.thefen.gexplorerapi.dtos.TokenDto
import me.thefen.gexplorerapi.dtos.TripDto
import me.thefen.gexplorerapi.dtos.UserDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface GexplorerApi {
    @GET("Auth/check")
    suspend fun checkAuth(): Response<Unit>
    @GET("Auth/check")
    suspend fun checkAuthCall(): Call<Unit>
    @POST("Auth/v2/login")
    suspend fun login(@Body loginDto: LoginDto): TokenDto

    @POST("Auth/v2/register")
    suspend fun register(@Body registerDto: RegisterDto): TokenDto
    
    @GET("District")
    suspend fun getDistricts(): List<DistrictDto>
    
    @GET("Leaderboard/overall")
    suspend fun getOverallLeaderboard(): Map<Int, LeaderboardEntryDto<Double>>
    
    @GET("Leaderboard/overall/{page}")
    suspend fun getOverallLeaderboard(@Path("page") page: Int): Map<Int, LeaderboardEntryDto<Double>>

    @GET("Leaderboard/district/{id}")
    suspend fun getDistrictLeaderboard(@Path("id") districtId: UUID): Map<Int, LeaderboardEntryDto<Double>>

    @GET("Leaderboard/district/{id}/{page}")
    suspend fun getDistrictLeaderboard(@Path("id") districtId: UUID, @Path("page") page: Int): Map<Int, LeaderboardEntryDto<Double>>
    
    @GET("User/{username}")
    suspend fun getUser(@Path("username") username: String): UserDto
    @GET("User/id/{uuid}")
    suspend fun getUser(@Path("uuid") id: UUID): UserDto

    @GET("User/id/{uuid}/polygon")
    suspend fun getUserPolygon(@Path("uuid") id: UUID): List<Polygon>

    @GET("Trip/id/{tripId}")
    suspend fun getTrip(@Path("tripId") id: UUID): DetailedTripDto

    @GET("Trip/starred")
    suspend fun getStarredTrips(): List<TripDto>

    @POST("Trip/id/{tripId}/star")
    suspend fun setTripStar(@Path("tripId") id: UUID, @Body starStatusDto: StarStatusDto): DetailedTripDto

    @POST("Trip/new/mobile")
    suspend fun sendTrip(@Body newTripDto: NewTripDto): TripDto
}