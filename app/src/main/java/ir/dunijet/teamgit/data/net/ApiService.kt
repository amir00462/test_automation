package ir.dunijet.teamgit.data.net

import ir.dunijet.teamgit.data.model.BlogResponse
import ir.dunijet.teamgit.data.model.CategoryResponse
import ir.dunijet.teamgit.util.SITE_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("blog")
    suspend fun getBlogs(): BlogResponse

    @GET("category")
    suspend fun getCategoryList(): CategoryResponse

}

fun createApiService(): ApiService {

    val retrofit = Retrofit.Builder()
        .baseUrl(SITE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(ApiService::class.java)
}