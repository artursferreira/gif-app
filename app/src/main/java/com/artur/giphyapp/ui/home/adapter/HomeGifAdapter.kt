package com.artur.giphyapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artur.giphyapp.databinding.GifItemBinding
import com.bumptech.glide.Glide

class HomeGifAdapter() : ListAdapter<GifItem, HomeGifAdapter.GifViewHolder>(object :
    DiffUtil.ItemCallback<GifItem>() {
    override fun areItemsTheSame(oldItem: GifItem, newItem: GifItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GifItem, newItem: GifItem): Boolean {
        return oldItem.url == newItem.url
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val itemBinding = GifItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class GifViewHolder(private val itemBinding: GifItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(gifItem: GifItem) {
            with(itemBinding) {
                Glide.with(gif.context).load(gifItem.url).into(gif)
                favouriteButton.isSelected = gifItem.isFavourite
            }
        }
    }
}