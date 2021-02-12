package com.artur.giphyapp.ui.favourite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artur.giphyapp.data.local.GifItem
import com.artur.giphyapp.databinding.GifItemBinding
import com.artur.giphyapp.databinding.GifItemFavouriteBinding
import com.artur.giphyapp.ui.home.adapter.HomeGifAdapter
import com.bumptech.glide.Glide

class FavouriteGifAdapter : ListAdapter<GifItem, FavouriteGifAdapter.GifViewHolder>(object :
    DiffUtil.ItemCallback<GifItem>() {
    override fun areItemsTheSame(oldItem: GifItem, newItem: GifItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GifItem, newItem: GifItem): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val itemBinding = GifItemFavouriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class GifViewHolder(private val itemBinding: GifItemFavouriteBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(gifItem: GifItem) {
            with(itemBinding) {
                Glide.with(gif.context).load(gifItem.url).into(gif)
            }
        }
    }
}