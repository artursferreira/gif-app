package com.artur.gifapp.home.adapter

import android.net.Uri
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artur.gifapp.R
import com.artur.gifapp.data.local.GifItem
import com.artur.gifapp.databinding.GifItemBinding
import com.facebook.drawee.backends.pipeline.Fresco


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

        val holder = GifViewHolder(itemBinding)

        itemBinding.favouriteButton.setOnClickListener {
            it?.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            val item = getItem(holder.adapterPosition)
            itemClickListener.onItemClicked(
                item.copy(
                    isFavourite = !item.isFavourite
                )
            )
        }

        itemBinding.shareButton.setOnClickListener {
            it?.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            val item = getItem(holder.adapterPosition)
            itemClickListener.onItemClicked(item, true)
        }

        return holder
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val item = getItem(position)
        item.isFavourite = favourites.contains(item.id)
        holder.bind(item)
    }

    class GifViewHolder(private val itemBinding: GifItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(gifItem: GifItem) {
            with(itemBinding) {
                val controller = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse(gifItem.webpUrl))
                    .setOldController(gif.controller)
                    .setAutoPlayAnimations(true)
                    .build()

                gif.hierarchy.setPlaceholderImage(R.drawable.progress_anim)
                gif.controller = controller

                favouriteButton.isSelected = gifItem.isFavourite

            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(gifItem: GifItem, share: Boolean = false)
    }
}