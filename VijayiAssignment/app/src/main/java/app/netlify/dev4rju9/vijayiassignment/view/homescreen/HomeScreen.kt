package app.netlify.dev4rju9.vijayiassignment.view.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.netlify.dev4rju9.vijayiassignment.viewmodel.MainViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import shimmerEffect
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    val pagerState = rememberPagerState(initialPage = 0) { 2 }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var lastPage = 0

    LaunchedEffect(state.error) {
        if (state.error.isNotEmpty()) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = state.error,
                    actionLabel = "Dismiss"
                )
            }
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage == 0) {
            viewModel.reorder()
        } else if (pagerState.currentPage == 1 && lastPage != 1) {
            viewModel.shuffle()
        }
        lastPage = pagerState.currentPage
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Text(
                text = "Trending",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
            )

            LazyRow (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.trending) { dish ->
                    val painter = rememberAsyncImagePainter(dish.url)
                    val trendingState = painter.state

                    Box (
                        modifier = Modifier
                            .size(100.dp)
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = dish.name,
                            modifier = Modifier
                                .matchParentSize()
                                .clip(CircleShape)
                                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                                .clickable(
                                    indication = LocalIndication.current,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    val nameEncoded = URLEncoder.encode(dish.name, StandardCharsets.UTF_8.toString())
                                    val descEncoded = URLEncoder.encode(dish.description, StandardCharsets.UTF_8.toString())
                                    val urlEncoded = URLEncoder.encode(dish.url, StandardCharsets.UTF_8.toString())
                                    navController.navigate("detail/$nameEncoded/$descEncoded/$urlEncoded")
                                },
                            contentScale = ContentScale.Crop
                        )
                        if (trendingState is AsyncImagePainter.State.Loading) {
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .clip(CircleShape)
                                    .shimmerEffect()
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val tabs = listOf("All", "Randomized")

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch { pagerState.animateScrollToPage(index) }
                        },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> DishList(
                        dishes = state.dishes,
                        isLoading = state.isLoading,
                        onClick = { dish ->
                            val nameEncoded = URLEncoder.encode(dish.name, StandardCharsets.UTF_8.toString())
                            val descEncoded = URLEncoder.encode(dish.description, StandardCharsets.UTF_8.toString())
                            val urlEncoded = URLEncoder.encode(dish.url, StandardCharsets.UTF_8.toString())

                            navController.navigate("detail/$nameEncoded/$descEncoded/$urlEncoded")
                        }
                    )

                    1 -> DishList(
                        dishes = state.dishes,
                        isLoading = state.isLoading,
                        onClick = { dish ->
                            val nameEncoded = URLEncoder.encode(dish.name, StandardCharsets.UTF_8.toString())
                            val descEncoded = URLEncoder.encode(dish.description, StandardCharsets.UTF_8.toString())
                            val urlEncoded = URLEncoder.encode(dish.url, StandardCharsets.UTF_8.toString())

                            navController.navigate("detail/$nameEncoded/$descEncoded/$urlEncoded")
                        }
                    )
                }
            }
        }
    }
}
