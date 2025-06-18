package com.v.verifonesearch.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.v.verifonesearch.presentation.ui.SearchScreenMainContent
import com.v.verifonesearch.presentation.ui.theme.GradientBottom
import com.v.verifonesearch.presentation.ui.theme.GradientTop
import com.v.verifonesearch.presentation.ui.theme.Purple80
import com.v.verifonesearch.presentation.ui.theme.VerifoneSearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }

    @Composable
    private fun MainScreen() {
        val viewModel: SearchViewModel = hiltViewModel()
        val results by viewModel.searchResults.collectAsState()
        val searchState by viewModel.searchStates
        SearchScreenMainContent(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            GradientTop,
                            GradientBottom,
                        )
                    ),
                )
                .padding(
                    top = 100.dp,
                    start = 24.dp,
                    end = 24.dp,
                ),
            results = results,
            onSearchInputChanged = { query ->
                viewModel.onSearchInputChanged(query)
            },
            state = searchState
        )
    }
}