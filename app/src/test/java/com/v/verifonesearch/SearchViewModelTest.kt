package com.v.verifonesearch

import com.v.verifonesearch.domain.usecase.PerformSearchUseCase
import com.v.verifonesearch.presentation.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var testRepo: TestSearchRepository
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        testRepo = TestSearchRepository()
        viewModel = SearchViewModel(PerformSearchUseCase(testRepo), testDispatcher, 0L)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onSearchInputChanged emits results with random suffix`() = runTest {
        val query = "Android"
        advanceUntilIdle()
        viewModel.onSearchInputChanged(query)

        advanceUntilIdle()

        val currentResults = viewModel.searchResults.value
        assertEquals(3, currentResults.size)
    }

    @Test
    fun `onSearchInputChanged emits empty results if empty query`() = runTest {
        val query = ""
        advanceUntilIdle()
        viewModel.onSearchInputChanged(query)

        advanceUntilIdle()

        val currentResults = viewModel.searchResults.value
        assertEquals(0, currentResults.size)
    }

    @Test
    fun `onSearchInputChanged throws error and handled gracefully`() = runTest {
        testRepo.shouldThrow = true
        viewModel.onSearchInputChanged("ErrorCase")

        advanceTimeBy(300)
        advanceUntilIdle()

        // no error state, making idle
        assertEquals(SearchViewModel.SearchState.Idle, viewModel.searchStates.value)
    }
}
