package com.nacarseven.series.presentation.seriesdetail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.nacarseven.series.R
import com.nacarseven.series.databinding.FragmentSeriesDetailBinding
import com.nacarseven.series.presentation.viewBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

const val SERIES_UI_ID_KEY = "seriesUiId"

class SeriesDetailFragment : Fragment(R.layout.fragment_series_detail) {

    private val binding: FragmentSeriesDetailBinding by viewBinding()
    private val seriesUiId by lazy { arguments?.getLong(SERIES_UI_ID_KEY) }
    private val viewModel: SeriesDetailViewModel by viewModel { parametersOf(seriesUiId) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectUiState()
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.seriesDetailViewState.observe(viewLifecycleOwner) {
                with(binding) {
                    tvSeriesName.text = it.name
                    progressBar.isVisible = it.isLoading
                }
            }
        }
    }


    fun newInstance(id: Long): SeriesDetailFragment {
        return SeriesDetailFragment().apply {
            arguments = Bundle().apply {
                putLong("SERIES_UI_ID_KEY", id)
            }
        }
    }

}