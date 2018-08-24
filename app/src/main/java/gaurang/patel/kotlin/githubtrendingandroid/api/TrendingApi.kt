/*
    Gaurang Patel: Retrofit configuration and GET call to repositories with Query parameters, language and since.

 */

package gaurang.patel.kotlin.githubtrendingandroid.api

import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingApi {
    @GET("repositories")
    fun getTrendingRepos(
            @Query("language") lang: String = "java",
            @Query("since") tredingSince: String = "weekly"
    ): Observable<List<RepoModel.TrendingResponse>>

    companion object {
        fun create(): TrendingApi {
            val baseUrl = "https://github-trending-api.now.sh/"
            val retrofit = Retrofit.Builder()
            retrofit.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            retrofit.addConverterFactory(GsonConverterFactory.create())
            retrofit.baseUrl(baseUrl)
            retrofit.client(OkHttpClient())
            return retrofit.build().create(TrendingApi::class.java)
        }
    }

}
