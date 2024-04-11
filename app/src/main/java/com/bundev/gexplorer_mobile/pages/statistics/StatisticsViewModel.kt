package com.bundev.gexplorer_mobile.pages.statistics

import androidx.lifecycle.ViewModel
import com.bundev.gexplorer_mobile.repo.GexplorerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repo: GexplorerRepository
) : ViewModel() {
}