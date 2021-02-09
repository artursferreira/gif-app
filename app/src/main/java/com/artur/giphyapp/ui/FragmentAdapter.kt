package com.artur.giphyapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.artur.giphyapp.ui.favorite.FavoriteFragment
import com.artur.giphyapp.ui.home.HomeFragment

class FragmentAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    //fixed number because there is fixed number of tabs
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                FavoriteFragment()
            }
            else -> {
                Fragment()
            } //TODO fix this
        }
    }
}