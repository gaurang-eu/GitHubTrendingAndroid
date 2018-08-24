package gaurang.patel.kotlin.githubtrendingandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class DetailsActivity : AppCompatActivity() {

    var tvName: TextView? = null;
    var tvLang: TextView? = null;
    var tvAuthro: TextView? = null;
    var tvStarToday: TextView? = null;
    var tvStar: TextView? = null;
    var tvFork: TextView? = null;
    var tvUrl: TextView? = null;
    var tvDesc: TextView? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        tvName = findViewById<TextView>(R.id.tvName)
        tvLang = findViewById<TextView>(R.id.tvLang)
        tvAuthro = findViewById<TextView>(R.id.tvAuthor)
        tvStarToday = findViewById<TextView>(R.id.tvStarTodayNum)
        tvStar = findViewById<TextView>(R.id.tvStarNum)
        tvFork = findViewById<TextView>(R.id.tvForkNum)
        tvUrl = findViewById<TextView>(R.id.tvUrl)
        tvDesc = findViewById<TextView>(R.id.tvDesc)

        val instance = intent;
        if (instance != null) {
            tvName!!.text = instance.getStringExtra("name")
            tvLang!!.text = instance.getStringExtra("lang")
            tvAuthro!!.text = instance.getStringExtra("author")
            tvStarToday!!.text = instance.getIntExtra("starToday", 0).toString()
            tvStar!!.text = instance.getIntExtra("stars", 0).toString()
            tvFork!!.text = instance.getIntExtra("forks", 0).toString()
            tvUrl!!.text = instance.getStringExtra("url")
            tvDesc!!.text = instance.getStringExtra("desc")
        }


    }
}
