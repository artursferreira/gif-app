package com.artur.giphyapp.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artur.giphyapp.R
import com.artur.giphyapp.data.local.GifItem
import com.artur.giphyapp.databinding.GifItemBinding
import com.artur.giphyapp.extensions.dp
import com.artur.giphyapp.extensions.performHapticFeedback
import com.artur.giphyapp.extensions.setLayoutSize
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade


class GifAdapter(private val itemClickListener: OnItemClickListener) :
    ListAdapter<GifItem, GifAdapter.GifViewHolder>(object :
        DiffUtil.ItemCallback<GifItem>() {
        override fun areItemsTheSame(oldItem: GifItem, newItem: GifItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GifItem, newItem: GifItem): Boolean {
            return oldItem == newItem
        }
    }) {

    var favourites: List<String> = emptyList()
        set(value) {
            if (field == value)
                return
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val itemBinding = GifItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val item = getItem(position)
        item.isFavourite = favourites.contains(item.id)
        holder.bind(item, itemClickListener)
    }

    class GifViewHolder(private val itemBinding: GifItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(gifItem: GifItem, clickListener: OnItemClickListener) {
            with(itemBinding) {
                gif.setLayoutSize(gifItem.width.dp, gifItem.height.dp)
                Glide.with(gif.context)
                    .asGif()
                    .transition(withCrossFade())
                    .load(gifItem.url)
                    .placeholder(R.drawable.progress_anim)
                    .into(gif)

                favouriteButton.isSelected = gifItem.isFavourite
                favouriteButton.performHapticFeedback()
                favouriteButton.setOnClickListener {
                    clickListener.onItemClicked(
                        gifItem.copy(
                            isFavourite = !gifItem.isFavourite
                        )
                    )
                }

                shareButton.setOnClickListener {
                    clickListener.onItemClicked(gifItem, true)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(gifItem: GifItem, share: Boolean = false)
    }
}