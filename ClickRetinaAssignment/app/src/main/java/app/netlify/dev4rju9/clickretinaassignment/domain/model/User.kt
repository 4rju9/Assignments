package app.netlify.dev4rju9.clickretinaassignment.domain.model

data class User (
    val avatar: String,
    val location: Location,
    val name: String,
    val social: Social,
    val statistics: Statistics,
    val username: String
)