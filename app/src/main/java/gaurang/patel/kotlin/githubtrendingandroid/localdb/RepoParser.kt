/*
    Gaurang Patel: Repository parser using anko db MapRowparser.

 */

package gaurang.patel.kotlin.githubtrendingandroid.localdb

import gaurang.patel.kotlin.githubtrendingandroid.api.RepoModel
import org.jetbrains.anko.db.MapRowParser


class RepoParser : MapRowParser<RepoModel.TrendingResponse> {
    override fun parseRow(columns: Map<String, Any?>): RepoModel.TrendingResponse {

        val item = RepoModel.TrendingResponse(
                columns[DBConfig.TblRepository.COL_AUTHOR].toString(),
                columns[DBConfig.TblRepository.COL_NAME].toString(),
                columns[DBConfig.TblRepository.COL_URL].toString(),
                columns[DBConfig.TblRepository.COL_DESCRIPTION].toString(),
                columns[DBConfig.TblRepository.COL_LANGUAGE].toString(),
                columns[DBConfig.TblRepository.COL_STARS].toString().toInt(),
                columns[DBConfig.TblRepository.COL_FORKS].toString().toInt(),
                columns[DBConfig.TblRepository.COL_STARSTHISWEEK].toString().toInt()
        )
        return item
    }
}