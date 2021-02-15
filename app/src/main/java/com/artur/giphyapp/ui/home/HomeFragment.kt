package com.artur.giphyapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.artur.giphyapp.data.local.GifItem
import com.artur.giphyapp.data.remote.Result.Status
import com.artur.giphyapp.databinding.HomeFragmentBinding
import com.artur.giphyapp.ui.adapter.GifAdapter
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        binding.searchView.setOnQueryTextListener(this)

        getTrending()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        with(binding) {
            recyclerview.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            recyclerview.adapter = adapter
        }
    }

    private fun getTrending() {
        viewModel.trendingGifs.observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        with(binding) {
                            progressCircular.visibility = View.VISIBLE
                            recyclerview.visibility = View.GONE
                        }
                    }
                    Status.SUCCESS -> {
                        with(binding) {
                            progressCircular.visibility = View.GONE
                            recyclerview.visibility = View.VISIBLE
                            adapter.submitList(resource.data)
                        }
                    }
                    Status.ERROR -> {
                    }
                }
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
        if (!text.isNullOrEmpty())
            viewModel.search(text).observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            with(binding) {
                                progressCircular.visibility = View.VISIBLE
                                recyclerview.visibility = View.GONE
                            }
                        }
                        Status.SUCCESS -> {
                            with(binding) {
                                progressCircular.visibility = View.GONE
                                recyclerview.visibility = View.VISIBLE
                                adapter.submitList(resource.data)
                            }
                        }
                        Status.ERROR -> {
                        }
                    }
                }
            })
        else
            getTrending()
    }

    override fun onItemClicked(gifItem: GifItem) {
        viewModel.saveFavourite(gifItem)
    }
}