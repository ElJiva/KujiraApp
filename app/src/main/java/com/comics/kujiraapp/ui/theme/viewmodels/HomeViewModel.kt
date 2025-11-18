package com.comics.kujiraapp.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comics.kujiraapp.models.Comics
import com.comics.kujiraapp.services.ComicApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class HomeState(
    val comics: List<Comics> = emptyList(),
    val loading: Boolean = true,
    val error: String? = null
)

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        fetchComics()
    }

    private fun fetchComics() {
        viewModelScope.launch {
            try {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://134.199.232.81:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val service = retrofit.create(ComicApi::class.java)
                val result = service.getComics()
                _state.value = HomeState(comics = result.comics ?: emptyList(), loading = false)
            } catch (e: Exception) {
                _state.value = HomeState(loading = false, error = e.message)
            }
        }
    }
}
