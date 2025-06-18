package com.v.verifonesearch.data.repo

import com.v.verifonesearch.domain.repo.SearchRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton


@Singleton

class DummySearchRepo @Inject constructor() : SearchRepository {

    override suspend fun search(query: String): List<String> {
        // Simulate network delay
        delay(300)

        if (query.isBlank()) {
            return emptyList()
        }

        val queryLower = query.lowercase().trim()

        // Find contacts that match the query
        val matchingContacts = ContactsDataHelper.getAllContacts().filter { contact ->
            val contactLower = contact.lowercase()
            contactLower.contains(queryLower) ||
                    contact.split(" ").any { name ->
                        name.lowercase().startsWith(queryLower)
                    }
        }

        val sortedContacts = matchingContacts.sortedWith { contact1, contact2 ->
            val contact1Lower = contact1.lowercase()
            val contact2Lower = contact2.lowercase()

            when {
                contact1Lower == queryLower -> -1
                contact2Lower == queryLower -> 1

                contact1.split(" ")[0].lowercase().startsWith(queryLower) &&
                        !contact2.split(" ")[0].lowercase().startsWith(queryLower) -> -1

                !contact1.split(" ")[0].lowercase().startsWith(queryLower) &&
                        contact2.split(" ")[0].lowercase().startsWith(queryLower) -> 1

                contact1.split(" ").getOrNull(1)?.lowercase()?.startsWith(queryLower) == true &&
                        contact2.split(" ").getOrNull(1)?.lowercase()
                            ?.startsWith(queryLower) != true -> -1

                contact1.split(" ").getOrNull(1)?.lowercase()?.startsWith(queryLower) != true &&
                        contact2.split(" ").getOrNull(1)?.lowercase()
                            ?.startsWith(queryLower) == true -> 1

                else -> contact1.compareTo(contact2)
            }
        }

        return sortedContacts.take(8)
    }
}