package com.example.tvexplorer.com.example.tvexplorer.ui.tvShowsList

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvexplorer.R
import com.example.tvexplorer.com.example.tvexplorer.tools.show
import com.example.tvexplorer.com.example.tvexplorer.ui.adapters.TvShowsAdapter
import com.example.tvexplorer.databinding.FragmentTvShowsListBinding
import com.example.tvexplorer.navigation.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TvShowsListFragment : BaseFragment<FragmentTvShowsListBinding, TvShowsListViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TvShowsAdapter { tvShow ->
            val action =
                TvShowsListFragmentDirections.actionTvShowsListFragmentToTvShowDetailsFragment(tvShow.id, tvShow.title)
            findNavController().navigate(action)
        }

        binding.apply {
            rvTvShows.layoutManager = LinearLayoutManager(requireContext())
            rvTvShows.adapter = adapter
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    hideKeyboard()
                    viewModel.submitQuery(query.orEmpty())
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
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
                    when (uiState.showEmptyListMessage()) {
                        EmptyListMessageTypes.None -> {
                            binding.tvEmptyList.show(false)
                        }
                        EmptyListMessageTypes.NothingFound -> {
                            binding.tvEmptyList.show(true)
                            binding.tvEmptyList.text = getString(R.string.tv_shows_list_nothing_found)
                        }
                        EmptyListMessageTypes.EmptyQuery -> {
                            binding.tvEmptyList.show(true)
                            binding.tvEmptyList.text = getString(R.string.tv_shows_list_find_something_hint)
                        }
                    }
                    binding.searchView.setQuery(uiState.currentQuery, false)
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager? = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view?.applicationWindowToken, 0)
    }
}