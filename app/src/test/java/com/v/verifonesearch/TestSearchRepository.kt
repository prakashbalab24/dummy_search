package com.v.verifonesearch

import com.v.verifonesearch.domain.repo.SearchRepository


class TestSearchRepository : SearchRepository {

    private val randomWords = listOf(
        "Banana", "Rocket", "Galaxy", "Laptop", "Penguin",
        "Mountain", "Ocean", "Pixel", "Android", "Compose"
    )

    val receivedQueries = mutableListOf<String>()
    var shouldThrow: Boolean = false

    override suspend fun search(query: String): List<String> {
        receivedQueries.add(query)

        if (shouldThrow) throw RuntimeException("Test Error")

        val word = randomWords.random()
        return listOf(
            "$query $word 1",
            "$query $word 2",
            "$query $word 3"
        )
    }
}