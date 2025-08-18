package app.netlify.dev4rju9.trigrexamassignment.util

object Utility {

    fun extractCategoryFromUrl(url: String): String {
        return try {
            val segments = url.split("/")
            val category = segments.getOrNull(segments.size - 2) ?: "Pizza"
            category.replaceFirstChar { it.uppercase() }
        } catch (e: Exception) {
            "Pizza"
        }
    }

}