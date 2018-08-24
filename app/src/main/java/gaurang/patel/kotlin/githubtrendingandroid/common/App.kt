package gaurang.patel.kotlin.githubtrendingandroid.common

import android.app.Application
import gaurang.patel.kotlin.githubtrendingandroid.localdb.DBHelper

class App : Application() {
    companion object {
        lateinit var db: DBHelper
            private set
    }

    override fun onCreate() {
        super.onCreate()
        db = DBHelper.getInstance(this)
    }
}