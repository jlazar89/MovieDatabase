package com.explained.producttmdb3.ui.movies.detail

import com.explained.producttmdb3.domain.model.MediaDetailDomain

sealed class DetailState {
    object Loading : DetailState()
    data class Success(val media: MediaDetailDomain) : DetailState()
    data class Error(val message: String) : DetailState()
}
