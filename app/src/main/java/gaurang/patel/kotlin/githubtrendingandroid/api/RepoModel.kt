/*
    Gaurang Patel: Model that will used by Retrofit and local DB according to the current response of the API.

 */

package gaurang.patel.kotlin.githubtrendingandroid.api

import com.google.gson.annotations.SerializedName

object RepoModel{
    data class TrendingResponse (
            val author: String,
            val name: String,
            val url: String,
            val description: String?,
            val language: String,
            val stars: Int,
            val forks: Int,
            @SerializedName("currentPeriodStars") val starsThisWeek: Int

    )
}