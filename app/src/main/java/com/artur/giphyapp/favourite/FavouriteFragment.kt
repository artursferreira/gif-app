package com.artur.giphyapp.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.artur.giphyapp.data.local.GifItem
import com.artur.giphyapp.databinding.FavouriteFragmentBinding
import com.artur.giphyapp.extensions.setDisplayHomeAsUpEnabled
import com.artur.giphyapp.home.adapter.GifAdapter
import com.artur.giphyapp.home.adapter.MenuFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouriteFragment : Fragment(), GifAdapter.OnItemClickListener {

    private var _binding: FavouriteFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavouriteViewModel by viewModel()

    private val adapter = GifAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FavouriteFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeFavorites()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        with(binding) {
            recyclerview.layoutManager = GridLayoutManager(context, 2)
            recyclerview.adapter = adapter
        }
    }

    private fun observeFavorites() {
        viewModel.favouriteGifs.observe(viewLifecycleOwner, {
            it?.let { list ->
                with(binding) {
                    progressCircular.visibility = View.GONE

                    if(list.isNullOrEmpty()) {
                       emptyState.visibility = View.VISIBLE
                        recyclerview.visibility = View.GONE
                    } else {
                        emptyState.visibility = View.GONE
                        recyclerview.visibility = View.VISIBLE
                        adapter.favourites = list.map { it.id }
                        adapter.submitList(list)
                    }
                }
            }
        })
    }

    override fun onItemClicked(gifItem: GifItem, share: Boolean) {
        if (!share)
            viewModel.saveFavourite(gifItem)
        else {
            val menuFragment = MenuFragment()
            val args = Bundle().apply { putParcelable(MenuFragment.KEY_GIF, gifItem) }
            menuFragment.arguments = args
            menuFragment.show(childFragmentManager, "tag")
        }
    }

}