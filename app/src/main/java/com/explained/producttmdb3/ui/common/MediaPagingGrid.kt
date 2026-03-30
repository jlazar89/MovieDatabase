package com.explained.producttmdb3.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> MediaPagingGrid(
    pagingItems: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {

        // 1. Main Grid Content
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalItemSpacing = 8.dp
        ) {
            // Success State: Show Items
            items(pagingItems.itemCount) { index ->
                pagingItems[index]?.let { item ->
                    itemContent(item)
                }
            }

            // Append State: Loading more data at the bottom
            if (pagingItems.loadState.append is LoadState.Loading) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    CircularProgressIndicator()
                }
            }

            // Append State: Error while loading more data
            if (pagingItems.loadState.append is LoadState.Error) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    val error = pagingItems.loadState.append as LoadState.Error
                    ErrorItem(
                        message = error.error.localizedMessage ?: "Failed to load more",
                        onRetry = { pagingItems.retry() }
                    )
                }
            }
        }

        // 2. Refresh State: Initial full-screen loading
        if (pagingItems.loadState.refresh is LoadState.Loading && pagingItems.itemCount == 0) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        }

        // 3. Refresh State: Initial full-screen error
        if (pagingItems.loadState.refresh is LoadState.Error && pagingItems.itemCount == 0) {
            val error = pagingItems.loadState.refresh as LoadState.Error
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ErrorItem(
                    message = error.error.localizedMessage ?: "An unexpected error occurred",
                    onRetry = { pagingItems.retry() }
                )
            }
        }
    }
}