package gaurang.patel.kotlin.githubtrendingandroid.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import gaurang.patel.kotlin.githubtrendingandroid.R
import gaurang.patel.kotlin.githubtrendingandroid.api.RepoModel
import kotlinx.android.synthetic.main.item_and_trending.view.*

class AndTrendingListAdapter(var data: List<RepoModel.TrendingResponse>?): RecyclerView.Adapter<AndTrendingListAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bindTrendingItem(data!![position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_and_trending, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        if (data != null)
            return data!!.size
        else
            return 0
    }


    class ViewHolder(val v: View): RecyclerView.ViewHolder(v) {
        fun bindTrendingItem(item: RepoModel.TrendingResponse) {
            with(item) {
                itemView.name.text = item.name
                itemView.stars.text = item.stars.toString()
                if(item.description != null){
                    itemView.author.text = item.author
                } else {
                    itemView.author.text = "No Author."
                }

            }
        }


    }

    interface OnFavoriteToggleListener {
        fun onToggle(item: RepoModel.TrendingResponse, btn: ImageView)
    }
}