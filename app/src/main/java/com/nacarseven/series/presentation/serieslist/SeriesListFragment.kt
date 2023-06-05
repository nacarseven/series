package com.nacarseven.series.presentation.serieslist

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.nacarseven.series.R
import com.nacarseven.series.databinding.FragmentSeriesListBinding
import com.nacarseven.series.presentation.SeriesOnClickListener
import com.nacarseven.series.presentation.model.SeriesUi
import com.nacarseven.series.presentation.serieslist.adapter.SeriesListAdapter
import com.nacarseven.series.presentation.serieslist.adapter.SeriesLoadStateAdapter
import com.nacarseven.series.presentation.viewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ZERO_VALUE = 0

class SeriesListFragment(private val seriesOnClickListener: SeriesOnClickListener) :
    Fragment(R.layout.fragment_series_list) {

    private val viewModel: SeriesListViewModel by viewModel()
    private val binding: FragmentSeriesListBinding by viewBinding()
    private val seriesListAdapter = SeriesListAdapter(::onItemClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModel.getSeries()
        collectUiState()
    }

    private fun initView() {
        with(binding) {
            rvSeries.adapter = seriesListAdapter.withLoadStateHeaderAndFooter(
                header = SeriesLoadStateAdapter { seriesListAdapter.retry() },
                footer = SeriesLoadStateAdapter { seriesListAdapter.retry() })
            seriesListAdapter.addLoadStateListener { loadState -> renderUi(loadState) }
        }
    }

    private fun onItemClick(seriesUi: SeriesUi) {
        seriesOnClickListener.onItemSeriesClicked(seriesUi)
    }

    private fun renderUi(loadState: CombinedLoadStates) {
        val isListEmpty =
            loadState.refresh is LoadState.NotLoading && seriesListAdapter.itemCount == ZERO_VALUE
        binding.rvSeries.isVisible = !isListEmpty
        binding.rvSeries.isVisible = loadState.source.refresh is LoadState.NotLoading
        binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getSeries().collectLatest {
                seriesListAdapter.submitData(it)
            }
        }
    }
}