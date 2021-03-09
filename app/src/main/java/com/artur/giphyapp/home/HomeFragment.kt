package com.artur.giphyapp.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.artur.giphyapp.R
import com.artur.giphyapp.data.local.GifItem
import com.artur.giphyapp.data.remote.Result
import com.artur.giphyapp.databinding.HomeFragmentBinding
import com.artur.giphyapp.home.adapter.GifAdapter
import com.artur.giphyapp.home.adapter.MenuFragment
import com.artur.giphyapp.home.adapter.MenuFragment.Companion.KEY_GIF
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
        setupSearchView()
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
            recyclerview.layoutManager = GridLayoutManager(context, 2)
            recyclerview.adapter = adapter
            recyclerview.setHasFixedSize(true)
        }
    }

    private fun setupSearchView() {
        with(binding) {
            searchView.setOnQueryTextListener(this@HomeFragment)
            searchView.isFocusable = false
            searchView.isIconified = false
            searchView.clearFocus()
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        onTextSearched(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        onTextSearched(newText)
        return true
    }

    private fun onTextSearched(text: String?) {
        if (text != null)
            viewModel.search(text)
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