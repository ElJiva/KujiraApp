package com.comics.kujiraapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.comics.kujiraapp.models.Comics
import com.comics.kujiraapp.models.Comment
import com.comics.kujiraapp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ComicDetailState(
    val comic: Comics? = null,
    val loading: Boolean = true,
    val error: String? = null
)

class ComicDetailViewModel(private val comicId: String) : ViewModel() {

    private val _state = MutableStateFlow(ComicDetailState())
    val state: StateFlow<ComicDetailState> = _state.asStateFlow()

    init {
        fetchComicDetail()
    }

    private fun fetchComicDetail() {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.comicApi.getComicsDetail(comicId)
                _state.value = ComicDetailState(comic = result, loading = false)
            } catch (e: Exception) {
                _state.value = ComicDetailState(loading = false, error = e.message)
            }
        }
    }

    fun addComment(text: String) {
        val currentState = _state.value
        val currentComic = currentState.comic ?: return

        val  newComment = Comment(
            username = "Actual User",
            text = text,
            createdAt = java.time.Instant.now().toString()
        )

        val updatedComic = currentComic.copy(
            comments = currentComic.comments + newComment
        )

        _state.value = currentState.copy(comic = updatedComic)
    }

    class Factory(private val comicId: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ComicDetailViewModel(comicId) as T
        }
    }
}