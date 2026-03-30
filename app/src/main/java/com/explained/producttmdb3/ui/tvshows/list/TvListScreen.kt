package com.explained.producttmdb3.ui.tvshows.list

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.explained.producttmdb3.ui.common.MediaCard
import com.explained.producttmdb3.ui.common.MediaPagingGrid

@Composable
fun TvListScreen(
    onTvShowClicked: (Int) -> Unit,
    viewModel: TvListViewModel = hiltViewModel()
) {
    val tvShows = viewModel.tvShows.collectAsLazyPagingItems()

    MediaPagingGrid(pagingItems = tvShows) { tv ->
        MediaCard(
            title = tv.name, // Use 'name' for TV Shows
            posterPath = tv.posterPath,
            onClick = { onTvShowClicked(tv.id) }
        )
    }
}