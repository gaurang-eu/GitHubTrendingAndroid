package gaurang.patel.kotlin.githubtrendingandroid.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gaurang.patel.kotlin.githubtrendingandroid.R
import gaurang.patel.kotlin.githubtrendingandroid.api.RepoModel
import kotlinx.android.synthetic.main.item_trending.view.*

class AndTrendingListAdapter(var data: List<RepoModel.TrendingResponse>?, val onButtonClickListener: OnButtonClickListener) : RecyclerView.Adapter<AndTrendingListAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bindTrendingItem(data!![position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_trending, parent, false)
        return ViewHolder(view, onButtonClickListener)
    }


    override fun getItemCount(): Int {
        if (data != null)
            return data!!.size
        else
            return 0
    }


    class ViewHolder(val v: View, val onButtonClickListener: OnButtonClickListener) : RecyclerView.ViewHolder(v) {
        fun bindTrendingItem(item: RepoModel.TrendingResponse) {
            with(item) {
                itemView.name.text = item.name
                itemView.stars.text = item.starsThisWeek.toString()
                if (item.author != null) {
                    itemView.author.text = "Author : " + item.author + " Lang: " + " " + item.language
                } else {
                    itemView.author.text = "No Auther."
                }

                itemView.setOnClickListener { onButtonClickListener.onButtonClic(item) }

            }
        }
    }


    interface OnButtonClickListener {
        abstract fun onButtonClic(item: RepoModel.TrendingResponse)
    }
}