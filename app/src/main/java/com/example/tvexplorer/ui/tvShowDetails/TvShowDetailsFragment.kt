package com.example.tvexplorer.com.example.tvexplorer.ui.tvShowDetails

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tvexplorer.R
import com.example.tvexplorer.com.example.tvexplorer.tools.GlideApp
import com.example.tvexplorer.com.example.tvexplorer.tools.show
import com.example.tvexplorer.core.enums.TvShowStatus
import com.example.tvexplorer.databinding.FragmentTvShowDetailsBinding
import com.example.tvexplorer.navigation.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TvShowDetailsFragment : BaseFragment<FragmentTvShowDetailsBinding, TvShowDetailsViewModel>() {
    private var snackBar: Snackbar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivFavorite.setOnClickListener {
            viewModel.onFavoriteClicked()
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    binding.toolbar.title = uiState.screenTitle
                    when (uiState.state) {
                        is TvShowDetailsUiState.Error -> {
                            binding.progressBar.show(false)
                            snackBar?.dismiss()
                            snackBar = Snackbar.make(view, uiState.state.message, Snackbar.LENGTH_INDEFINITE)
                            snackBar?.show()
                        }
                        is TvShowDetailsUiState.Loading -> {
                            binding.progressBar.show(true)
                            snackBar?.dismiss()
                        }
                        is TvShowDetailsUiState.Success -> {
                            binding.progressBar.show(false)
                            snackBar?.dismiss()
                            val tvShow = uiState.state.tvShow
                            binding.apply {
                                tvTitle.text = tvShow.title
                                tvSummary.text = Html.fromHtml(tvShow.summary, Html.FROM_HTML_MODE_COMPACT)
                                tvStatus.text = tvShow.status
                                tvStatus.setTvShowStatus(TvShowStatus.fromIdentifier(tvShow.status))
                                tvRating.text = tvShow.rating.toString()
                                GlideApp.with(ivPoster)
                                    .load(tvShow.imageUrl)
                                    .placeholder(R.drawable.ic_image_stub)
                                    .into(ivPoster)
                            }
                            binding.ivFavorite.setImageResource(if (tvShow.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
                            val aliases = if (uiState.state.aliases.isEmpty()) getString(R.string.tv_show_details_no_aliases) else uiState.state.aliases.joinToString { it }
                            binding.tvAliases.text = getString(R.string.tv_show_details_aliases_mask, aliases)
                        }
                        else -> {}
                    }

                }
            }
        }
    }
}