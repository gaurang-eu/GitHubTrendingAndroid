/*
    Gaurang Patel: This is the main activity which shows bottom tab bar.
        On selecting the tab it shows appropriate Fragment.
        These activity is initially showing today's trending repositories of kotlin language.
 */

package gaurang.patel.kotlin.githubtrendingandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import gaurang.patel.kotlin.githubtrendingandroid.api.TrendingApi
import gaurang.patel.kotlin.githubtrendingandroid.fragments.AndTrendingFragment
import gaurang.patel.kotlin.githubtrendingandroid.fragments.KotlinTrendingFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    val apiTrending: TrendingApi by lazy {
        TrendingApi.create();
    }

//    Creating KotlinTrendingFragment instance
    val kotlinTrendingFragment: KotlinTrendingFragment by lazy {
        KotlinTrendingFragment.newInstance();
    }

//    Creating AndTrendingFragment instance
    val andTrendingFragment: AndTrendingFragment by lazy {
        AndTrendingFragment.newInstance();
    }

//    Register onNavigationItemSelected listener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.menu.getItem(0))
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.nav_and_trend -> {
                fragment = andTrendingFragment
                setTitle("Android Trending")
            }
            R.id.nav_kot_trend -> {
                fragment = kotlinTrendingFragment
                setTitle("Kotlin Trending")
            }
            else -> {
                toast("Oops")
                return@OnNavigationItemSelectedListener false
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.contentFrame, fragment).commit()
        true
    }
}
