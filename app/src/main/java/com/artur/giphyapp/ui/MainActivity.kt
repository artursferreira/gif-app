package com.artur.giphyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.artur.giphyapp.R
import com.artur.giphyapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.pager.adapter = FragmentAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text =
                if (position == 0) getString(R.string.home_tab_title) else getString(R.string.favourites_tab_title)
        }.attach()

    }
}