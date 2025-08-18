package app.netlify.dev4rju9.trigrexamassignment.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.netlify.dev4rju9.trigrexamassignment.models.Category
import app.netlify.dev4rju9.trigrexamassignment.models.Restaurant
import app.netlify.dev4rju9.trigrexamassignment.models.remote.retrofit.FoodishApiService
import app.netlify.dev4rju9.trigrexamassignment.util.Utility.extractCategoryFromUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: FoodishApiService
) : ViewModel() {

    var allRestaurants by mutableStateOf<List<Restaurant>>(emptyList())
        private set

    var filteredRestaurants by mutableStateOf<List<Restaurant>>(emptyList())
        private set

    var selectedCategory by mutableStateOf("All")
        private set

    var searchQuery by mutableStateOf("")
        private set

    var selectedFilter by mutableStateOf("Recommended") // or "Popular"
        private set

    val categories = listOf(
        Category("All", "https://media.istockphoto.com/id/1457433817/photo/group-of-healthy-food-for-flexitarian-diet.jpg?s=612x612&w=0&k=20&c=v48RE0ZNWpMZOlSp13KdF1yFDmidorO2pZTu2Idmd3M="),
        Category("Burger", "https://foodish-api.com/images/burger/burger1.jpg"),
        Category("Pizza", "https://foodish-api.com/images/pizza/pizza1.jpg"),
        Category("Dessert", "https://foodish-api.com/images/dessert/dessert1.jpg"),
        Category("Biryani", "https://foodish-api.com/images/biryani/biryani1.jpg"),
        Category("Samosa", "https://foodish-api.com/images/samosa/samosa1.jpg"),
        Category("Rice", "https://foodish-api.com/images/rice/rice1.jpg"),
        Category("Butter Chicken", "https://foodish-api.com/images/butter-chicken/butter-chicken1.jpg"),
        Category("Dosa", "https://foodish-api.com/images/dosa/dosa1.jpg"),
        Category("Idly", "https://foodish-api.com/images/idly/idly1.jpg"),
        Category("Pasta", "https://foodish-api.com/images/pasta/pasta1.jpg"),
    )

    init {
        fetchRestaurants()
    }

    private fun fetchRestaurants() {
        selectedCategory = "All"
        viewModelScope.launch {
            val list = mutableListOf<Restaurant>()
            repeat(20) {
                try {
                    val imageUrl = api.getRandomFoodImage().image
                    val category = extractCategoryFromUrl(imageUrl)
                    list.add(
                        Restaurant(
                            name = "Domingo $category",
                            category = category,
                            rating = 4.5,
                            priceLevel = "$$",
                            deliveryTime = "35min",
                            imageUrl = imageUrl
                        )
                    )
                } catch (_: Exception) {
                    list.add(
                        Restaurant(
                            name = "Domingo Pizza",
                            category = "Pizza",
                            rating = 4.5,
                            priceLevel = "$$",
                            deliveryTime = "35min",
                            imageUrl = "https://foodish-api.com/images/pizza/pizza1.jpg"
                        )
                    )
                }
            }
            allRestaurants = list
            applyFilters()
        }
    }

    fun fetchRestaurantsByCategory(category: String) {
        selectedCategory = category

        if (category == "All") {
            fetchRestaurants()
            return
        }

        viewModelScope.launch {
            val list = mutableListOf<Restaurant>()
            repeat(20) {
                try {
                    val imageUrl = api.getCategorisedFoodImage(category.lowercase()).image
                    list.add(
                        Restaurant(
                            name = "Domingo $category",
                            category = category,
                            rating = 4.5,
                            priceLevel = "$$",
                            deliveryTime = "35min",
                            imageUrl = imageUrl
                        )
                    )
                } catch (_: Exception) {
                    list.add(
                        Restaurant(
                            name = "Domingo $category",
                            category = category,
                            rating = 4.5,
                            priceLevel = "$$",
                            deliveryTime = "35min",
                            imageUrl = "https://via.placeholder.com/150"
                        )
                    )
                }
            }
            allRestaurants = list
            applyFilters()
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery = query
        applyFilters()
    }

    private fun applyFilters() {
        filteredRestaurants = allRestaurants.filter {
            (selectedCategory == "All" || it.category.equals(selectedCategory, ignoreCase = true)) &&
                    it.name.contains(searchQuery, ignoreCase = true)
        }
    }

    fun onFilterSelected(filter: String) {
        selectedFilter = filter
        regenerateCategories()
    }

    private fun regenerateCategories() {
        filteredRestaurants = if (selectedFilter == "Recommended") {
            allRestaurants.shuffled()
        } else {
            allRestaurants.reversed()
        }
    }

}