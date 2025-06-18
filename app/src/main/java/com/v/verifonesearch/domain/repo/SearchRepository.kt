package com.v.verifonesearch.domain.repo

interface SearchRepository {
    suspend fun search(query: String): List<String>
}