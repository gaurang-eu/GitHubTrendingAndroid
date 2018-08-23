/*
    Gaurang Patel: This is the AndTrendingFragment which is showing Trending Repositories related to Android.
        Inital flag for trending period is daily.
        This actually gets trending repositories of java, kotlin, c, javascript and dart and filters the repositories based on Android keyword.

 */

package gaurang.patel.kotlin.githubtrendingandroid.fragments


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import gaurang.patel.kotlin.githubtrendingandroid.R
import gaurang.patel.kotlin.githubtrendingandroid.adapters.AndTrendingListAdapter
import gaurang.patel.kotlin.githubtrendingandroid.api.RepoModel
import gaurang.patel.kotlin.githubtrendingandroid.api.TrendingApi
import io.reactivex.Observable

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.functions.Function3
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.find
/**
 * A simple [Fragment] subclass.
 *
 */
class AndTrendingFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance(): AndTrendingFragment {
            val fragment = AndTrendingFragment();
            return fragment;
        }
    }

//    Creating TrendingApi
    val api: TrendingApi by lazy {
        TrendingApi.create();
    }

    var recylerView: RecyclerView? = null;
    var adapter: AndTrendingListAdapter? = null;
    var data: List<RepoModel.TrendingResponse>?= null;

    val disposable = CompositeDisposable();

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val v = inflater!!.inflate(R.layout.fragment_and_trending, container, false)
        recylerView = v?.findViewById<RecyclerView>(R.id.trendingRecylerView)
        val spinner: Spinner? = v?.find(R.id.spinnerFrom)
        spinner?.onItemSelectedListener = this
        adapter = AndTrendingListAdapter(data)
        recylerView?.layoutManager = LinearLayoutManager(context)
        recylerView?.adapter = adapter
        return v
    }

//    Used to show the error message
    fun showStatus(msg: String) {
        Snackbar.make(activity?.find(R.id.contentFrame)!!, msg, Snackbar.LENGTH_SHORT).show()
    }


//    To change the trending period
    override fun onItemSelected(parent: AdapterView<*>?, v: View?, pos: Int, id: Long) {
        when(pos) {
            0 -> getTrending("daily")
            1 -> getTrending("weekly")
            2 -> getTrending("monthly")
        }
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun mergeLists(): Function3<List<RepoModel.TrendingResponse>, List<RepoModel.TrendingResponse>, List<RepoModel.TrendingResponse>, List<RepoModel.TrendingResponse>> {
        return Function3 { list, list2, list3 ->

            list as ArrayList<RepoModel.TrendingResponse>
            list2.forEach({
                list.add(it)
            })

            list3.forEach({
                list.add(it)
            })

            list
        }
    }

//    Logic to fetch trending repos of multiple languages and then filtering based on Android
    fun getTrending(flag: String) {

//        val requests = ArrayList<Observable<List<RepoModel.TrendingResponse>>>();
//        requests.add(api.getTrendingRepos ("java", flag).subscribeOn(Schedulers.io()))
//        requests.add(api.getTrendingRepos ("kotlin", flag).subscribeOn(Schedulers.io()))
//        requests.add(api.getTrendingRepos ("dart", flag).subscribeOn(Schedulers.io()))

    disposable.clear()
    disposable.add(
            Observable
                    .zip(api.getTrendingRepos ("java", flag).subscribeOn(Schedulers.io()),
                            api.getTrendingRepos ("kotlin", flag).subscribeOn(Schedulers.io()),
                            api.getTrendingRepos ("dart", flag).subscribeOn(Schedulers.io()),
                            mergeLists())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<List<RepoModel.TrendingResponse>>() {
                        override fun onNext(value: List<RepoModel.TrendingResponse>?) {
                            Log.e("trending", "got reponse :) count: " + value!![0].name)
                            data = filterAndroidRepos(value)
                            adapter?.data = data
                            adapter?.notifyDataSetChanged()
                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                            showStatus(e.localizedMessage)
                            Log.e("Error", e.localizedMessage);
                        }

                        override fun onComplete() {

                        }
                    })
    )

//        disposable.add(api.getTrendingRepos ("java", flag)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        {
//                            Log.d("trending", "got reponse :) count: " + it[0].name)
//                            data = it
//                            adapter?.data = it
//                            adapter?.notifyDataSetChanged()
//                        },
//                        {
//                            showStatus(it.localizedMessage)
//                            Log.e("Error", it.localizedMessage);
//                        }
//                ))
    }


    fun filterAndroidRepos(repos: List<RepoModel.TrendingResponse>): List<RepoModel.TrendingResponse>? {
        return repos?.filter { response -> response.description!!.contains("android",ignoreCase = true) || response.name!!.contains("android",ignoreCase = true) }
    }


}
