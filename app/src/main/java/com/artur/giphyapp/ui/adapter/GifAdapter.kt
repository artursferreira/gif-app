package com.artur.giphyapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artur.giphyapp.R
import com.artur.giphyapp.data.local.GifItem
import com.artur.giphyapp.databinding.GifItemBinding
import com.artur.giphyapp.extensions.dp
import com.artur.giphyapp.extensions.setLayoutHeight
import com.bumptech.glide.Glide

class GifAdapter(private val itemClickListener: OnItemClickListener) : ListAdapter<GifItem, GifAdapter.GifViewHolder>(object :
    DiffUtil.ItemCallback<GifItem>() {
    override fun areItemsTheSame(oldItem: GifItem, newItem: GifItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GifItem, newItem: GifItem): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val itemBinding = GifItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, itemClickListener)
    }

    class GifViewHolder(private val itemBinding: GifItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(gifItem: GifItem, clickListener: OnItemClickListener) {
            with(itemBinding) {
                gif.setLayoutHeight(gifItem.width.dp, gifItem.height.dp)
                Glide.with(gif.context).load(gifItem.url).placeholder(R.drawable.progress_anim).into(gif)
                favouriteButton.isSelected = gifItem.isFavourite

                favouriteButton.setOnClickListener { clickListener.onItemClicked(gifItem.copy(isFavourite = !gifItem.isFavourite)) }
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClicked(gifItem: GifItem)
    }
}