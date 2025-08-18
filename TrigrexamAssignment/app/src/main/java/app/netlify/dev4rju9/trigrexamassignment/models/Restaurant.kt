package app.netlify.dev4rju9.trigrexamassignment.models

data class Restaurant(
    val name: String,
    val category: String,
    val rating: Double,
    val priceLevel: String,
    val deliveryTime: String,
    val imageUrl: String?
)