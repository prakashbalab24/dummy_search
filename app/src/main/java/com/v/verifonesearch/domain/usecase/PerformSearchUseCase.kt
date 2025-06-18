package com.v.verifonesearch.domain.usecase

import com.v.verifonesearch.domain.repo.SearchRepository
import javax.inject.Inject

class PerformSearchUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query: String): List<String> {
        return repository.search(query)
    }
}
