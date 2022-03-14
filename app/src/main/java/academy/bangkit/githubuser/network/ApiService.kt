package academy.bangkit.githubuser.network

import academy.bangkit.githubuser.model.SearchResponse
import academy.bangkit.githubuser.model.UserDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/search/users")
    fun getSearchUsers(
        @Query("q") username: String,
        @Header("Authorization") token: String
    ): Call<SearchResponse>

    @GET("/users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetail>
}