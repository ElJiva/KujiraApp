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
  val selectedGenre:String ="All Genres",
  val marvelChecked: Boolean=true,
  val dcChecked: Boolean=true,
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
                    error = null
                )
            } catch (e: Exception) {
                println("DEBUG: Error: ${e.message}")
                e.printStackTrace()
                _state.value = HomeState(
                    comics = emptyList(),
                    allComics = emptyList(),
                    loading = false,
                    error = e.message,
                )
            }
        }
    }
    fun onSearchQueryChange(query: String){
        _state.update{current ->
            current.copy(searchQuery = query)
        }
        applyFilters()
    }
    fun onGenreChange(genre: String){
        _state.update{
            it.copy(selectedGenre = genre)
        }
        applyFilters()
    }
    fun onMarvelCheckChange(checked: Boolean){
        _state.update { it.copy(marvelChecked = checked) }
        applyFilters()

    }
    fun onDcCheckChange(checked: Boolean){
        _state.update { it.copy(dcChecked = checked) }
        applyFilters()

    }
    fun clearFilters(){
        _state.update {
            it.copy(
                searchQuery = "",
                selectedGenre = "All Genres",
                marvelChecked = true,
                dcChecked = true
            )
        }
        applyFilters()
    }
    private fun applyFilters(){
        _state.update { current ->
            var filtered = current.allComics

            if (current.searchQuery.isNotBlank()) {
                filtered = filtered.filter { comic ->
                    comic.title.contains(current.searchQuery, ignoreCase = true) ||
                            comic.author.contains(current.searchQuery, ignoreCase = true)
                }
            }

            val selectedPublishers = mutableListOf<String>()
            if (current.marvelChecked)selectedPublishers.add("Marvel")
            if (current.dcChecked)selectedPublishers.add("DC")

            if (selectedPublishers.isNotEmpty()){
                filtered = filtered.filter { comic ->
                    selectedPublishers.any { publisher ->
                        comic.editorial.contains(publisher, ignoreCase = true)
                    }
                }
            }
            if (current.selectedGenre != "All Genres") {
                filtered = filtered.filter { comic ->
                    comic.category.equals(current.selectedGenre, ignoreCase = true)
                }
            }
            current.copy(comics = filtered)
        }


    }
}

