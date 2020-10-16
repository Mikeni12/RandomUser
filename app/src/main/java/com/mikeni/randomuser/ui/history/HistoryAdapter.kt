package com.mikeni.randomuser.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mikeni.randomuser.R
import com.mikeni.randomuser.data.entities.User
import kotlinx.android.synthetic.main.item_recent.view.*

class HistoryAdapter(private val listener: IRecyclerListener<User>) :
    ListAdapter<User, HistoryAdapter.ViewHolder>(UserDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_recent, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: User) {
            Glide.with(itemView)
                .load(item.picture.thumbnail)
                .apply(RequestOptions.circleCropTransform())
                .into(itemView.imgRecentUser)

            itemView.tvRecentUser.text = itemView.resources.getString(
                R.string.user_name,
                item.name.title,
                item.name.first,
                item.name.last
            )

            itemView.setOnClickListener { listener.onItemClick(item) }
        }
    }

    object UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.cell == newItem.cell


        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem == newItem

    }
}