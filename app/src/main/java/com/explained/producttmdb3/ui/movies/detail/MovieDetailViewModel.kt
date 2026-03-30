package com.explained.producttmdb3.ui.movies.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.explained.producttmdb3.domain.repository.MovieRepository
import com.explained.producttmdb3.domain.repository.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<DetailState>(DetailState.Loading)
    val state = _state.asStateFlow()

    init {
        val mediaId = savedStateHandle.get<Int>("mediaId")
        val mediaType = savedStateHandle.get<String>("mediaType")
        
        if (mediaId != null && mediaType != null) {
            getMediaDetail(mediaId, mediaType)
        } else {
            _state.value = DetailState.Error("Media information not found")
        }
    }

    private fun getMediaDetail(mediaId: Int, mediaType: String) {
        viewModelScope.launch {
            try {
                val detail = if (mediaType == "movie") {
                    movieRepository.getMovieDetail(mediaId)
                } else {
                    tvRepository.getTvShowDetail(mediaId)
                }
                _state.value = DetailState.Success(detail)
            } catch (e: Exception) {
                _state.value = DetailState.Error(e.localizedMessage ?: "An error occurred")
            }
        }
    }
}
