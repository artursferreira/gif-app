package com.artur.giphyapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.artur.giphyapp.data.remote.Status
import com.artur.giphyapp.databinding.HomeFragmentBinding
import com.artur.giphyapp.ui.home.adapter.HomeGifAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    private val adapter = HomeGifAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.getTrending().observe(viewLifecycleOwner, Observer {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        with(binding) {
            recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerview.adapter = adapter
        }
    }
}