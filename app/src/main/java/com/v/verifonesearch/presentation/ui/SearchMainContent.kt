package com.v.verifonesearch.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.v.verifonesearch.R
import com.v.verifonesearch.data.repo.ContactsDataHelper
import com.v.verifonesearch.presentation.SearchViewModel
import com.v.verifonesearch.presentation.ui.theme.Purple80


@Composable
fun SearchScreenMainContent(
    modifier: Modifier = Modifier,
    results: List<String>,
    state: SearchViewModel.SearchState,
    onSearchInputChanged: (String) -> Unit
) {
    var input by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            leadingIcon = {
                if (state is SearchViewModel.SearchState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(android.R.drawable.ic_menu_search),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            },
            value = input,
            onValueChange = {
                input = it
                onSearchInputChanged(it)
            },
            placeholder = {
                Text(
                    stringResource(R.string.type_to_search),
                    color = Color.LightGray,
                    fontStyle = FontStyle.Italic
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                )
                .background(
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Gray,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (state is SearchViewModel.SearchState.NoResults) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(android.R.drawable.ic_dialog_alert),
                    contentDescription = null,
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(stringResource(R.string.no_results_for, state.query), color = Color.Red)
            }
        } else if (state is SearchViewModel.SearchState.Idle && results.isEmpty()) {
            Text(
                "Note: This is just a sample contact search, " +
                        "it will search for name which is present in the list. " +
                        "ex: ${ContactsDataHelper.getRandom5Contacts().joinToString()}",
                color = Color.White,
                fontStyle = FontStyle.Italic,
                fontSize = 12.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(results.size) { resultIndex ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White
                        )
                        .background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(android.R.drawable.ic_menu_call),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(results[resultIndex], color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SearchScreenMainContent(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple80)
            .padding(
                top = 100.dp,
                start = 24.dp,
                end = 24.dp,
            ),
        results = listOf("Result 1", "Result 2", "Result 3"),
        onSearchInputChanged = {},
        state = SearchViewModel.SearchState.Idle
    )
}