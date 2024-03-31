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

object AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (CredentialHolder.gexplorerApiToken == null) {
            return chain.proceed(chain.request())
        }

        
        val requestWithHeader = chain.request()
            .newBuilder()
            .header(
                "Authorization", "Bearer ${CredentialHolder.gexplorerApiToken}"
            ).build()
        return chain.proceed(requestWithHeader)
    }
}

object RetrofitClient {
    private const val BASE_URL = "http://fendeavour:5107/"
    
    private val httpClient: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        
        OkHttpClient()
            .newBuilder()
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(AuthorizationInterceptor)
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

object ApiClient {
    val gexplorerApi: GexplorerApi by lazy {
        RetrofitClient.retrofit.create(GexplorerApi::class.java)
    }
}