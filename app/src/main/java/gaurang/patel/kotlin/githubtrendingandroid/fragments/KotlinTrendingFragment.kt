/*
    Gaurang Patel: This is the KotlinTrendingFragment which is showing Trending Repositories of Kotlin language.
        Inital flag for trending period is daily.
 */

package gaurang.patel.kotlin.githubtrendingandroid.fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import gaurang.patel.kotlin.githubtrendingandroid.DetailsActivity
import gaurang.patel.kotlin.githubtrendingandroid.R
import gaurang.patel.kotlin.githubtrendingandroid.adapters.AndTrendingListAdapter
import gaurang.patel.kotlin.githubtrendingandroid.api.RepoModel
import gaurang.patel.kotlin.githubtrendingandroid.api.TrendingApi
import gaurang.patel.kotlin.githubtrendingandroid.util.NetworkUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.find

class KotlinTrendingFragment : Fragment(), AdapterView.OnItemSelectedListener, AndTrendingListAdapter.OnButtonClickListener {

    companion object {
        fun newInstance(): KotlinTrendingFragment {
            val fragment = KotlinTrendingFragment();
            return fragment;
        }
    }

    //    Creating TrendingApi
    val api: TrendingApi by lazy {
        TrendingApi.create();
    }

    var recylerView: RecyclerView? = null
    var adapter: AndTrendingListAdapter? = null
    var data: List<RepoModel.TrendingResponse>? = null

    var llTop: LinearLayout? = null
    var progress: ProgressBar? = null
    var tvMsg: TextView? = null
    var spinner: Spinner? = null

    val disposable = CompositeDisposable();

    var period = 0
    var flag: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_and_trending, container, false)
        recylerView = v?.findViewById<RecyclerView>(R.id.trendingRecylerView)
        llTop = v?.findViewById<LinearLayout>(R.id.ll_top)
        progress = v?.findViewById<ProgressBar>(R.id.progressBar)
        tvMsg = v?.findViewById<TextView>(R.id.tvMsg)

        spinner = v?.find(R.id.spinnerFrom)
        spinner?.onItemSelectedListener = this
        adapter = AndTrendingListAdapter(data, this)
        recylerView?.layoutManager = LinearLayoutManager(context)
        recylerView?.adapter = adapter
        return v
    }

    override fun onResume() {
        super.onResume()
        flag = false
        handleItemSelected(period)
    }

    override fun onPause() {
        super.onPause()
        if (disposable != null) {
            disposable.clear();
        }
    }

    // showStatus function is used to show error message if error occurs
    fun showStatus(msg: String) {
        Snackbar.make(activity?.find(R.id.contentFrame)!!, msg, Snackbar.LENGTH_SHORT).show()
    }

    //    Implementation onItemSelected, to change the trending period flag
    override fun onItemSelected(parent: AdapterView<*>?, v: View?, pos: Int, id: Long) {
        if (flag == false) {
            flag = true
        } else {
            handleItemSelected(pos)
        }
    }

    //    when nothing is selected
    override fun onNothingSelected(parent: AdapterView<*>?) {
//        not needed now
    }

    fun handleItemSelected(pos: Int) {
        if (NetworkUtil().isNetworkAvailable(context!!)) {
            hideList();
            period = pos
            when (pos) {
                0 -> getTrending("daily")
                1 -> getTrending("weekly")
                2 -> getTrending("monthly")
            }
        } else {
            showMsg("Check Internet Connectivity")
        }
    }

    //    Implementing the onButtonClick function of OnButtonClickListener
    override fun onButtonClic(item: RepoModel.TrendingResponse) {
//        activity?.toast("AndTrendingListAdapter item clicked")
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("name", item.name)
        intent.putExtra("lang", item.language)
        intent.putExtra("author", item.author)
        intent.putExtra("starToday", item.starsThisWeek)
        intent.putExtra("stars", item.stars)
        intent.putExtra("forks", item.forks)
        intent.putExtra("url", item.url)
        intent.putExtra("desc", item.description)
        activity?.startActivity(intent)
    }

    //    Logic to call API and subscribe to the response
    fun getTrending(flag: String) {
        disposable.add(api.getTrendingRepos("kotlin", flag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            showList()
                            data = it
                            adapter?.data = it
                            adapter?.notifyDataSetChanged()
                        },
                        {
                            showStatus(it.localizedMessage)
                            showMsg("Failed to connect to the Server");
//                            Log.e("Error", it.localizedMessage);
                        }
                ))
    }

    //    Show the recycler and spinner view and hide progress
    fun showList() {
        llTop?.visibility = View.VISIBLE
        recylerView?.visibility = View.VISIBLE
        spinner?.visibility = View.VISIBLE
        progress?.visibility = View.GONE
        tvMsg?.visibility = View.GONE
    }

    //    Show the progress and loading and hide recycler and spinner view
    fun hideList() {
        llTop?.visibility = View.GONE
        recylerView?.visibility = View.GONE
        spinner?.visibility = View.GONE
        progress?.visibility = View.VISIBLE
        tvMsg?.visibility = View.VISIBLE
    }


    //    Show only messasge when any error occured.
    fun showMsg(msg: String) {
        llTop?.visibility = View.GONE
        recylerView?.visibility = View.GONE
        spinner?.visibility = View.GONE
        progress?.visibility = View.GONE
        tvMsg?.visibility = View.VISIBLE
        tvMsg?.text = "Please try again since could not load data due to below issue.\n" + msg
    }

}
