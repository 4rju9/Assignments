package app.netlify.dev4rju9.trigrexamassignment.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import app.netlify.dev4rju9.trigrexamassignment.view.components.BottomNavigationBar
import app.netlify.dev4rju9.trigrexamassignment.view.components.CategoryRow
import app.netlify.dev4rju9.trigrexamassignment.view.components.FilterRow
import app.netlify.dev4rju9.trigrexamassignment.view.components.RestaurantList
import app.netlify.dev4rju9.trigrexamassignment.view.components.SearchBar
import app.netlify.dev4rju9.trigrexamassignment.view.components.TopBar
import app.netlify.dev4rju9.trigrexamassignment.viewmodels.HomeViewModel

@Composable
fun MainScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val categories = viewModel.categories
    val selectedCategory = viewModel.selectedCategory
    val selectedFilter = viewModel.selectedFilter
    val searchQuery = viewModel.searchQuery
    val restaurants = viewModel.filteredRestaurants

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar() }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SearchBar(
                query = searchQuery,
                onQueryChange = viewModel::onSearchQueryChanged
            )
            CategoryRow(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategoryClick = viewModel::fetchRestaurantsByCategory
            )
            FilterRow(
                selected = selectedFilter,
                onFilterSelected = viewModel::onFilterSelected
            )
            RestaurantList(restaurants)
        }
    }
}