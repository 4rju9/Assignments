package app.netlify.dev4rju9.clickretinaassignment.presentation.UserProfileScreen

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.netlify.dev4rju9.clickretinaassignment.R
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
fun UserProfileScreen (
    modifier: Modifier = Modifier,
    viewModel: UserProfileViewModel = hiltViewModel()
) {

    val user = viewModel.state.value?.user
    val isLoading = viewModel.isLoading.value
    val error = viewModel.error.value
    val context = LocalContext.current
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 2 }
    )
    val scope = rememberCoroutineScope()
    val tabs = listOf("0 shots", "10 Collections")

    Column (
        modifier = modifier
            .fillMaxSize()
    ) {

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize(0.5f))
            }
        } else if (error.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Text(text = error)
            }
        } else {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(bottom = 70.dp)
            ) {

                Image(
                    painterResource(id = R.drawable.background),
                    contentDescription = "Background",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .scale(scaleY = 1.2f, scaleX = 1f)
                )

                IconButton(
                    onClick = {
                        Toast.makeText(context, "Settings Clicked", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        Icons.Outlined.Settings,
                        contentDescription = "Settings",
                        tint = Color.White
                    )
                }

                Text(
                    text = user?.username?: "unknown",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )

                AsyncImage(
                    model = "https://media.istockphoto.com/id/1682296067/photo/happy-studio-portrait-or-professional-man-real-estate-agent-or-asian-businessman-smile-for.jpg?s=612x612&w=0&k=20&c=9zbG2-9fl741fbTWw5fNgcEEe4ll-JegrGlQQ6m54rg=",
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .size(100.dp)
                        .offset(y = 60.dp)
                        .clip(CircleShape)
                        .border(4.dp, Color.White, CircleShape),
                    contentScale = ContentScale.Crop
                )

            }

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 14.dp)
            ) {

                Text(
                    text = user?.name?: "unknown",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                val location = "${user?.location?.city}, ${user?.location?.country}"

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = location,
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFDCDFE8))
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)
                            ) {
                                append("${user?.statistics?.followers ?: 0}")
                            }
                            withStyle(
                                SpanStyle(color = Color.Gray)
                            ) {
                                append(" Followers")
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)
                            ) {
                                append("${user?.statistics?.following ?: 0}")
                            }
                            withStyle(
                                SpanStyle(color = Color.Gray)
                            ) {
                                append(" Following")
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium,
                    )

                }

                Spacer(modifier = Modifier.height(20.dp))

                Row (
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    if (!user?.social?.website.isNullOrEmpty()) {
                        IconButton(
                            onClick = {
                                Intent(Intent.ACTION_VIEW, Uri.parse(user?.social?.website))
                                    .also { context.startActivity(it) }
                            }
                        ) {
                            Icon(
                                Icons.Rounded.Language,
                                contentDescription = "Website",
                                tint = Color.Gray
                            )
                        }

                        Canvas (modifier = Modifier.size(5.dp)) {
                            drawCircle(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF5151C6),
                                        Color(0xFF888BF4)
                                    ),
                                    start = Offset(0f, 0f),
                                    end = Offset(this.size.width, 0f)
                                ),
                                radius = 5.dp.toPx()
                            )
                        }

                    }

                    user?.social?.profiles?.forEachIndexed { index, profile ->
                        IconButton(
                            onClick = {
                                Intent(Intent.ACTION_VIEW, Uri.parse(profile.url))
                                    .also { context.startActivity(it) }
                            }
                        ) {
                            Icon(
                                if (profile.platform == "Instagram") painterResource(id = R.drawable.ic_instagram)
                                else painterResource(R.drawable.ic_facebook),
                                contentDescription = profile.platform,
                                tint = Color.Gray
                            )
                        }

                        if (index < user.social.profiles.size - 1) {
                            Canvas (modifier = Modifier.size(5.dp)) {
                                drawCircle(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF5151C6),
                                            Color(0xFF888BF4)
                                        ),
                                        start = Offset(0f, 0f),
                                        end = Offset(this.size.width, 0f)
                                    ),
                                    radius = 5.dp.toPx()
                                )
                            }
                        }

                    }

                }

                Spacer(modifier = Modifier.height(20.dp))

                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = Color(0xFFF7F7FF),
                    contentColor = Color(0xFF5F5FFF),
                    indicator = { _ -> },
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, title ->
                        val selected = pagerState.currentPage == index
                        Tab(
                            modifier = Modifier
                                .background(
                                    color = if (selected) Color(0xFFEAEAFF) else Color.Transparent,
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            selected = selected,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 14.sp,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selected) Color(0xFF5F5FFF) else Color.Gray
                                )
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.empty),
                            contentDescription = null,
                            modifier = Modifier.size(200.dp)
                        )
                    }
                }

            }

        }

    }

}