package app.netlify.dev4rju9.vijayiassignment.viewmodel

import app.netlify.dev4rju9.vijayiassignment.model.Dish

data class UIState(
    val isLoading: Boolean = false,
    val trending: List<Dish> = emptyList(),
    val dishes: List<Dish> = emptyList(),
    val error: String = ""
)
