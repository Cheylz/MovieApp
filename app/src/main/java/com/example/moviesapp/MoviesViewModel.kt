package com.example.moviesapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class MoviesUiState {
    object Loading : MoviesUiState()
    data class Success(val movies: List<String>) : MoviesUiState()
    data class Error(val message: String) : MoviesUiState()
}

class MoviesViewModel : ViewModel() {
    var state: MoviesUiState by mutableStateOf(MoviesUiState.Loading)
        private set

    private var attempt = 0

    init {
        load()
    }

    fun load() {
        state = MoviesUiState.Loading
        viewModelScope.launch {
            try {
                state = MoviesUiState.Success(fetchMovies())
            } catch (e: Exception) {
                state = MoviesUiState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }

    private suspend fun fetchMovies(): List<String> {
        delay(2000)
        attempt++
        if (attempt == 1) throw Exception("Нет интернета")   // первая попытка всегда с ошибкой
        return listOf("Матрица", "Начало", "Интерстеллар")
    }
}