package com.example.simpleToDo.data.repositories

import com.example.simpleToDo.data.models.Deal
import com.example.simpleToDo.domain.repositories.DealsRepository
import javax.inject.Inject

class DealsRepositoryImpl @Inject constructor() : DealsRepository {
     override suspend fun loadListOfCurrentDeals(): List<Deal>{
         return listOf()
     }

}
