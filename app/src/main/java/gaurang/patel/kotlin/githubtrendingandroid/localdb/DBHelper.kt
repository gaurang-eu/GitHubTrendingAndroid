/*
    Gaurang Patel: DBHelper to create and manage DB and operations.

 */

package gaurang.patel.kotlin.githubtrendingandroid.localdb

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import gaurang.patel.kotlin.githubtrendingandroid.api.RepoModel
import org.jetbrains.anko.db.*;

class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper ( ctx, DBConfig.DB.NAME, null, DBConfig.DB.VERSION) {
    companion object {
        private var instance: DBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DBHelper {
            if (instance == null)
                instance = DBHelper(ctx)
            return instance!!
        }
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
                DBConfig.TblRepository.NAME,
                true,
                DBConfig.TblRepository.COL_ID to INTEGER + PRIMARY_KEY,
                DBConfig.TblRepository.COL_AUTHOR to TEXT,
                DBConfig.TblRepository.COL_NAME to TEXT ,
                DBConfig.TblRepository.COL_URL to TEXT,
                DBConfig.TblRepository.COL_DESCRIPTION to TEXT,
                DBConfig.TblRepository.COL_LANGUAGE to TEXT,
                DBConfig.TblRepository.COL_STARS to INTEGER ,
                DBConfig.TblRepository.COL_FORKS to INTEGER,
                DBConfig.TblRepository.COL_STARSTHISWEEK to INTEGER
                )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(DBConfig.TblRepository.NAME, true)
        onCreate(db)
    }

    fun getSelectedRepo (ID: String): List<RepoModel.TrendingResponse>? {
        var items: List<RepoModel.TrendingResponse>? = null
        val query = "SELECT * FROM %s T WHERE T.%s = %s".format(DBConfig.TblRepository.NAME,DBConfig.TblRepository.COL_ID,ID)
        val cursor = this.readableDatabase.rawQuery(query, null)
        with(cursor) {
            while (moveToNext()) {

                val parser = RepoParser()
                items = parseList(parser)
            }
            Log.d("DB", "Column count" + columnCount)
            for(c in columnNames)
                Log.d("DB", "Columns" + c)
        }
        return items
    }
}

val Context.database: DBHelper get() = DBHelper.getInstance(getApplicationContext())
