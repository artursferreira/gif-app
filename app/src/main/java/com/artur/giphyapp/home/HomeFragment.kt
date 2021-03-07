package com.artur.giphyapp.home

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.artur.giphyapp.R
import com.artur.giphyapp.data.local.GifItem
import com.artur.giphyapp.data.remote.Result
import com.artur.giphyapp.databinding.HomeFragmentBinding
import com.artur.giphyapp.home.adapter.GifAdapter
import com.artur.giphyapp.home.adapter.MenuFragment
import com.artur.giphyapp.home.adapter.MenuFragment.Companion.KEY_GIF
import com.artur.giphyapp.main.MainActivity
import com.google.android.material.navigation.NavigationView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), SearchView.OnQueryTextListener,
    GifAdapter.OnItemClickListener {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    private val adapter = GifAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        binding.searchView.setOnQueryTextListener(this)
        observeGifs()
        observeFavorites()
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater)

        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favourites -> {
                findNavController().navigate(R.id.favouriteFragment)
                return true
            }
            R.id.settings -> {
                findNavController().navigate(R.id.settingsFragment)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        with(binding) {
            recyclerview.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            recyclerview.adapter = adapter
        }
    }

    private fun observeGifs() {
        viewModel.gifs.observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource) {
                    is Result.Loading -> {
                        with(binding) {
                            progressCircular.visibility = View.VISIBLE
                            recyclerview.visibility = View.GONE
                            emptyState.visibility = View.GONE
                        }
                    }
                    is Result.Success -> {
                        with(binding) {
                            motionLayout.setTransition(R.id.loading_transition)
                            motionLayout.transitionToEnd()
                            adapter.submitList(resource.data)
                        }
                    }
                    is Result.Error -> {
                        with(binding.motionLayout) {
                            setTransition(R.id.error_transition)
                            transitionToEnd()
                        }
                    }
                }
            }
        })
    }

    private fun observeFavorites() {
        viewModel.favouriteGifs.observe(viewLifecycleOwner, {
            it?.let { list ->
                adapter.favourites = list.map { it.id }
            }
        })

    }

    private fun getTrending() {
        viewModel.getTrendingGifs()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        onTextSearched(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        onTextSearched(newText)
        return true
    }

    private fun onTextSearched(text: String?) {
        if (!text.isNullOrEmpty())
            viewModel.search(text)
        else
            getTrending()
    }

    override fun onItemClicked(gifItem: GifItem, share: Boolean) {
        if (!share)
            viewModel.saveFavourite(gifItem)
        else {
            val menuFragment = MenuFragment()
            val args = Bundle().apply { putParcelable(KEY_GIF, gifItem) }
            menuFragment.arguments = args
            menuFragment.show(childFragmentManager, "tag")
        }
    }
}