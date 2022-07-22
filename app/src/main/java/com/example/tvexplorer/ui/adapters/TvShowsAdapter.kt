package com.example.tvexplorer.com.example.tvexplorer.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tvexplorer.R
import com.example.tvexplorer.com.example.tvexplorer.tools.GlideApp
import com.example.tvexplorer.com.example.tvexplorer.ui.views.TvShowStatusView
import com.example.tvexplorer.core.enums.TvShowStatus
import com.example.tvexplorer.core.model.TvShow

class TvShowsAdapter(
    val itemActionListener: (TvShow) -> Unit,
) : ListAdapter<TvShow, TvShowsAdapter.ViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<TvShow>() {
        override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_item_tv_show, viewGroup, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(R.id.tvTitle)
        private val status = view.findViewById<TvShowStatusView>(R.id.tvStatus)
        private val poster = view.findViewById<ImageView>(R.id.ivPoster)

        init {
            itemView.setOnClickListener {
                if (absoluteAdapterPosition >= 0) {
                    itemActionListener.invoke(getItem(absoluteAdapterPosition))
                }
            }
        }

        fun bind() {
            val tvShowInfo = getItem(absoluteAdapterPosition)
            title.text = tvShowInfo.title
            status.text = tvShowInfo.status
            status.setTvShowStatus(TvShowStatus.fromIdentifier(tvShowInfo.status))
            GlideApp.with(poster)
                .load(tvShowInfo.imageUrl)
                .placeholder(R.drawable.ic_image_stub)
                .into(poster)
        }
    }
}

