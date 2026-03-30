package com.explained.producttmdb3.ui.tvshows.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.explained.producttmdb3.domain.model.TvShowDomain
import com.explained.producttmdb3.domain.repository.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TvListViewModel @Inject constructor(
    private val tvRepository: TvRepository
) : ViewModel() {

    val tvShows: Flow<PagingData<TvShowDomain>> = tvRepository
        .getTopRatedTvShows()
        .cachedIn(viewModelScope)
}
