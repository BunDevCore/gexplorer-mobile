package me.thefen.gexplorerapi

import com.google.gson.GsonBuilder
import com.mapbox.geojson.GeometryAdapterFactory
import com.mapbox.geojson.gson.GeoJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthorizationInterceptor(private val getToken: () -> String? = {null}) : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        if (getToken() == null) {
            return chain.proceed(chain.request())
        }

        
        val requestWithHeader = chain.request()
            .newBuilder()
            .header(
                "Authorization", "Bearer ${getToken()}"
            ).build()
        return chain.proceed(requestWithHeader)
    }
}

private const val BASE_URL = "http://fendeavour:5107/"

class RetrofitClient(private var _token: String? = null) {
    fun setToken(token: String?) {
        _token = token
    }
    
    
    private val authInterceptor = AuthorizationInterceptor { _token }

    private val httpClient: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        
        OkHttpClient()
            .newBuilder()
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(authInterceptor)
            .build()
    }

//    val moshi = Moshi.Builder()
//        .addLast(KotlinJsonAdapterFactory())
//        .build()
    
    val gson = GsonBuilder()
        .registerTypeAdapterFactory(GeoJsonAdapterFactory.create())
        .registerTypeAdapterFactory(GeometryAdapterFactory.create())
        .create()
    
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}