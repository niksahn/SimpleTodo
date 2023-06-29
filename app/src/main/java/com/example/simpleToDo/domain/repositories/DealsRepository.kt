package com.example.simpleToDo.domain.repositories

import com.example.simpleToDo.domain.models.Deal

interface DealsRepository {
    suspend fun loadListOfCurrentDeals(): List<Deal>
}