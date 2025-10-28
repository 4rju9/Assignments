package app.netlify.dev4rju9.vijayiassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.netlify.dev4rju9.vijayiassignment.model.repository.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UIState())
    val state = _state.asStateFlow()

    private val disposable = CompositeDisposable()

    init {
        fetchAllData()
    }

    private fun fetchAllData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {

                val trending = repository.getTrending()

                val menuSingle = Single.zip(
                    repository.getButterChickens(),
                    repository.getBurgers(),
                    repository.getPizzas(),
                    repository.getPastas(),
                    repository.getDosas()
                ) { butterChickens, burgers, pizzas, pastas, dosas ->
                    buildList {
                        addAll(butterChickens.items)
                        addAll(burgers.items)
                        addAll(pizzas.items)
                        addAll(pastas.items)
                        addAll(dosas.items)
                    }
                }

                disposable.add(
                    menuSingle
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ dishes ->
                            _state.value = UIState(
                                trending = trending.items,
                                dishes = dishes,
                                isLoading = false
                            )
                        }, { error ->
                            _state.value = UIState(
                                error = error.message ?: "Something went wrong. Please try again.",
                                isLoading = false
                            )
                        })
                )
            } catch (e: Exception) {
                _state.value = UIState(
                    error = e.message ?: "Unable to fetch data. Check your connection.",
                    isLoading = false
                )
            }
        }
    }

    fun shuffle() {
        val shuffledDishes = _state.value.dishes.shuffled()
        _state.value = _state.value.copy(dishes = shuffledDishes)
    }

    fun reorder() {
        val orderedDishes = _state.value.dishes.sortedBy { it.name }
        _state.value = _state.value.copy(dishes = orderedDishes)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}