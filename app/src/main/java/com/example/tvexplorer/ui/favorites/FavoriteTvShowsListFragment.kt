package com.example.tvexplorer.com.example.tvexplorer.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvexplorer.com.example.tvexplorer.tools.show
import com.example.tvexplorer.com.example.tvexplorer.ui.adapters.TvShowsAdapter
import com.example.tvexplorer.databinding.FragmentFavoriteTvShowsBinding
import com.example.tvexplorer.navigation.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteTvShowsListFragment : BaseFragment<FragmentFavoriteTvShowsBinding, FavoriteTvShowsListViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TvShowsAdapter { tvShow ->
            val action = FavoriteTvShowsListFragmentDirections.actionFavoriteTvShowsListFragmentToTvShowDetailsFragment(tvShow.id, tvShow.title, true)
            findNavController().navigate(action)
        }

        binding.apply {
            rvTvShows.layoutManager = LinearLayoutManager(requireContext())
            rvTvShows.adapter = adapter
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    binding.progressBar.show(uiState.isFetchingTvShows)
                    if (!uiState.errorMessage.isNullOrBlank()) {
                        Snackbar.make(view, uiState.errorMessage, Snackbar.LENGTH_SHORT).show()
                        viewModel.onErrorMessageDismissed()
                    }
                    adapter.submitList(uiState.tvShowsList)
                    binding.tvEmptyList.show(uiState.tvShowsList.isEmpty())
                }
            }
        }
    }
}