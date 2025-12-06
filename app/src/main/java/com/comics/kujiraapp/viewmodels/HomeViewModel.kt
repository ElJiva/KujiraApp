package com.comics.kujiraapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comics.kujiraapp.models.Comics
import com.comics.kujiraapp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update

data class HomeState(
  val comics: List<Comics> = emptyList(),
  val allComics: List<Comics> = emptyList(),
  val loading: Boolean = true,
  val searchQuery: String = "",
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
                val result = RetrofitClient.comicApi.getComics()
                println("DEBUG: Comics recibidos: ${result.size}")
                result.firstOrNull()?.let { comic ->
                    println("DEBUG: Primer comentario: ${comic.comments.firstOrNull()}")
                }
                _state.value = HomeState(
                    comics = result,
                    allComics = result,
                    loading = false,
                    error = null,
                    searchQuery = ""
                )
            } catch (e: Exception) {
                println("DEBUG: Error: ${e.message}")
                e.printStackTrace()
                _state.value = HomeState(
                    comics = emptyList(),
                    allComics = emptyList(),
                    loading = false,
                    error = e.message,
                    searchQuery = ""
                )
            }
        }
    }
    fun onSearchQueryChange(query: String){
        _state.update{current ->
            val filtered = if(query.isBlank()){
                current.allComics
            }
            else {
                current.allComics.filter{comic ->
                    comic.title.contains(query,ignoreCase = true)||
                            comic.author.contains(query,ignoreCase = true)
                }
            }
            current.copy(
                searchQuery = query,
                comics = filtered
            )
        }
    }
}
