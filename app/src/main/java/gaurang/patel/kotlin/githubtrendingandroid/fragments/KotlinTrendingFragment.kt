/*
    Gaurang Patel: This is the KotlinTrendingFragment which is showing Trending Repositories of Kotlin language.
        Inital flag for trending period is daily.
 */

package gaurang.patel.kotlin.githubtrendingandroid.fragments

import android.content.Context
import android.net.Uri
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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.find

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [KotlinTrendingFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class KotlinTrendingFragment : Fragment(), AdapterView.OnItemSelectedListener {

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

    var recylerView: RecyclerView? = null;
    var adapter: AndTrendingListAdapter? = null;
    var data: List<RepoModel.TrendingResponse>?= null;

    val disposable = CompositeDisposable();

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        return TextView(activity).apply {
//            setText(R.string.hello_blank_fragment)
//        }
        val v = inflater!!.inflate(R.layout.fragment_and_trending, container, false)
        recylerView = v?.findViewById<RecyclerView>(R.id.trendingRecylerView)
        val spinner: Spinner? = v?.find(R.id.spinnerFrom)
        spinner?.onItemSelectedListener = this
        adapter = AndTrendingListAdapter(data)
        recylerView?.layoutManager = LinearLayoutManager(context)
        recylerView?.adapter = adapter
        //if(data == null) getTrending()
        return v
    }

    // showStatus function is used to show error message if error occurs
    fun showStatus(msg: String) {
        Snackbar.make(activity?.find(R.id.contentFrame)!!, msg, Snackbar.LENGTH_SHORT).show()
    }

//    Implementation onItemSelected, to change the trending period flag
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

//    Logic to call API and subscribe to the response
    fun getTrending(flag: String) {
        disposable.add(api.getTrendingRepos ("kotlin", flag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Log.d("trending", "got reponse :) count: " + it[0].name)
                            data = it
                            adapter?.data = it
                            adapter?.notifyDataSetChanged()
                        },
                        {
                            showStatus(it.localizedMessage)
                            Log.e("Error", it.localizedMessage);
                        }
                ))
    }


}
