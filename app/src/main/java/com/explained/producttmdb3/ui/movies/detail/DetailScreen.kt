package com.explained.producttmdb3.ui.movies.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.explained.producttmdb3.domain.model.GenreDomain
import com.explained.producttmdb3.domain.model.MediaDetailDomain

@Composable
fun DetailScreen(
    viewModel: MediaDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (state) {
            is DetailState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is DetailState.Success -> {
                MediaDetailContent(media = state.media)
            }

            is DetailState.Error -> {
                Text(
                    text = state.message,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun MediaDetailContent(media: MediaDetailDomain) {
    val scrollState = rememberScrollState()
    val topBarAlpha by remember {
        derivedStateOf { (scrollState.value / 600f).coerceIn(0f, 1f) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0F))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            // ── HERO ──────────────────────────────────────────
            HeroSection(media = media)

            // ── BODY ──────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-32).dp)
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(Color(0xFF0A0A0F))
                    .padding(horizontal = 24.dp)
                    .padding(top = 28.dp, bottom = 48.dp)
            ) {
                TitleSection(media = media)
                Spacer(modifier = Modifier.height(20.dp))
                StatsRow(media = media)
                Spacer(modifier = Modifier.height(20.dp))
                GenreRow(genres = media.genres)
                Spacer(modifier = Modifier.height(28.dp))
                OverviewSection(overview = media.overview)
            }
        }

        // ── SCROLL-AWARE TOP BAR ──────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Black.copy(alpha = 0.55f), Color.Transparent)
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color(0xFF0A0A0F).copy(alpha = topBarAlpha))
            )
        }
    }
}

@Composable
private fun HeroSection(media: MediaDetailDomain) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
    ) {
        val backdropUrl = media.backdropPath?.let { "https://image.tmdb.org/t/p/w1280$it" }

        if (backdropUrl != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(backdropUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            brush = Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.0f to Color.Black.copy(alpha = 0.15f),
                                    0.6f to Color.Black.copy(alpha = 0.3f),
                                    1.0f to Color(0xFF0A0A0F)
                                )
                            )
                        )
                    }
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFF1A1A2E), Color(0xFF16213E), Color(0xFF0F3460))
                        )
                    )
            )
        }

        Card(
            shape = RoundedCornerShape(14.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 24.dp)
                .offset(y = 48.dp)
                .size(width = 110.dp, height = 165.dp)
        ) {
            val posterUrl = media.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }
            if (posterUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(posterUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = media.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1E1E2E)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = media.title.take(2).uppercase(),
                        color = Color.White.copy(alpha = 0.4f),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun TitleSection(media: MediaDetailDomain) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        Spacer(modifier = Modifier.width(148.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 10.dp)
        ) {
            Text(
                text = media.releaseDate.take(4),
                color = Color(0xFFE0A84E),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = media.title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 26.sp
            )
        }
    }
}

@Composable
private fun StatsRow(media: MediaDetailDomain) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF141420))
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color(0xFFE0A84E),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "%.1f".format(media.voteAverage),
                    color = Color(0xFFE0A84E),
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "SCORE",
                color = Color.White.copy(alpha = 0.4f),
                fontSize = 10.sp,
                letterSpacing = 1.5.sp
            )
        }

        Box(
            modifier = Modifier
                .width(1.dp)
                .height(40.dp)
                .background(Color.White.copy(alpha = 0.1f))
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = media.voteCount.toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "VOTES",
                color = Color.White.copy(alpha = 0.4f),
                fontSize = 10.sp,
                letterSpacing = 1.5.sp
            )
        }
    }
}

@Composable
private fun GenreRow(genres: List<GenreDomain>) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        genres.forEach { genre ->
            Text(
                text = genre.name,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(100.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
private fun OverviewSection(overview: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "OVERVIEW",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = overview,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 15.sp,
            lineHeight = 24.sp
        )
    }
}
