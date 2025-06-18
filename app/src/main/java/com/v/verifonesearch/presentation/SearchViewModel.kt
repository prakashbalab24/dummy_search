package com.v.verifonesearch.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v.verifonesearch.di.DebounceDuration
import com.v.verifonesearch.di.IoDispatcher
import com.v.verifonesearch.domain.usecase.PerformSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val performSearch: PerformSearchUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineContext,
    @DebounceDuration private val debounce: Long
) : ViewModel() {

    val searchResults = MutableStateFlow<List<String>>(emptyList())
    val searchStates: MutableState<SearchState> = mutableStateOf(SearchState.Idle)

    private var searchJob: Job? = null
    private val searchInputFlow = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1
    )

    init {
        observeSearchQuery()
    }

    fun onSearchInputChanged(newQuery: String) {
        searchInputFlow.tryEmit(newQuery)
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            var lastQuery = ""
            searchInputFlow
                .collectLatest { query ->
                    if (query == lastQuery) return@collectLatest
                    lastQuery = query
                    if (query.isBlank()) {
                        searchStates.value = SearchState.Idle
                        searchResults.value = emptyList()
                        return@collectLatest
                    }
                    searchStates.value = SearchState.Loading
                    delay(debounce) // debounce

                    searchJob?.cancel()
                    searchJob = launch {
                        val results = withContext(ioDispatcher) {
                            try {
                                performSearch(query)
                            } catch (e: Exception) {
                                emptyList()
                            }
                        }
                        if (results.isEmpty()) {
                            searchStates.value = SearchState.NoResults(query)
                        } else {
                            searchStates.value = SearchState.Idle
                            searchResults.value = results
                        }
                    }
                }
        }
    }

    sealed class SearchState {
        data object Idle : SearchState()
        data object Loading : SearchState()
        data class Error(val message: String) : SearchState()
        data class NoResults(val query: String) : SearchState()
    }
}