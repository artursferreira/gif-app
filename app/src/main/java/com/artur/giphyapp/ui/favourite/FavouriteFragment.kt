package com.artur.giphyapp.ui.favourite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.artur.giphyapp.R
import com.artur.giphyapp.databinding.FavouriteFragmentBinding
import com.artur.giphyapp.databinding.HomeFragmentBinding
import com.artur.giphyapp.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouriteFragment : Fragment() {

    private var _binding: FavouriteFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavouriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FavouriteFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}