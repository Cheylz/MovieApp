package com.example.moviesapp

import retrofit2.HttpException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.IOException


sealed class MoviesUiState {
    object Loading : MoviesUiState()
    data class Success(val posts: List<Post>) : MoviesUiState()
    data class Error(val message: String) : MoviesUiState()
}

class MoviesViewModel : ViewModel() {
    var state: MoviesUiState by mutableStateOf(MoviesUiState.Loading)
        private set

    init {
        load()
    }

    fun load() {
        state = MoviesUiState.Loading
        viewModelScope.launch {
            try {
                val posts = Network.postsApi.getPosts()
                state = MoviesUiState.Success(posts)
            }catch (e : IOException){
                state = MoviesUiState.Error("Нет подключения к интернету")
            }catch (e: HttpException){
                state = MoviesUiState.Error("Ошибка сервера: ${e.code()}")
            }catch (e: Exception){
                state = MoviesUiState.Error("Что-то пошло не так")
            }

        }
    }
}