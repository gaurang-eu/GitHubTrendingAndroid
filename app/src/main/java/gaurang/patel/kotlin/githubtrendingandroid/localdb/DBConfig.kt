/*
    Gaurang Patel: Local DB configuration with table structure.

 */

package gaurang.patel.kotlin.githubtrendingandroid.localdb

object DBConfig {
        object TblRepository {
            val NAME = "repository"
            val COL_ID = "id"
            val COL_AUTHOR = "author"
            val COL_NAME = "name"
            val COL_URL = "url"
            val COL_DESCRIPTION = "description"
            val COL_LANGUAGE = "language"
            val COL_STARS = "stars"
            val COL_FORKS = "forks"
            val COL_STARSTHISWEEK = "starsThisWeek"
        }

    object DB {
        val NAME = "trening"
        val VERSION = 1
    }
}