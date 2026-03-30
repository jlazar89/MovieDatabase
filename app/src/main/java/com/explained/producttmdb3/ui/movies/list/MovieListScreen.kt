package com.explained.producttmdb3.ui.movies.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.explained.producttmdb3.ui.common.MediaCard
import com.explained.producttmdb3.ui.common.MediaPagingGrid

@Composable
fun MovieListScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieListViewModel = hiltViewModel(),
    onMovieClicked: (Int) -> Unit
) {
    val movies = viewModel.movies.collectAsLazyPagingItems()

    MediaPagingGrid(pagingItems = movies) { movie ->
        MediaCard(
            title = movie.title,
            posterPath = movie.posterPath,
            onClick = { onMovieClicked(movie.id) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MediaCardPreview() {
    MediaCard(
        title = "Movie Title",
        posterPath = "/path/to/poster.jpg",
        onClick = {},
    )
}
