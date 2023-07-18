package com.example.simpleToDo.domain.usecases

import com.example.simpleToDo.domain.models.Tag
import com.example.simpleToDo.domain.models.toDealUi
import com.example.simpleToDo.domain.repositories.DealsRepository
import com.example.simpleToDo.ui.models.DealUi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import javax.inject.Inject


abstract class GetDealUseCase {
	abstract suspend fun run(date: LocalDate): Flow<List<DealUi>>
}

class GetDealUseCaseImpl @Inject constructor(
	private val dealRepository: DealsRepository,
) : GetDealUseCase() {
	override suspend fun run(date: LocalDate) = combine(
		dealRepository.loadDeals(date),
		dealRepository.loadTags()
	) { dealEntities, tagEntities ->
		dealEntities.map { dealEntity ->
			dealEntity.toDealUi(
				tag = tagEntities.find { it.id == dealEntity.tagId }
					?.let { Tag(name = it.name, id = it.id) }
			)
		}.sortedBy { it.priority }.toImmutableList()
	}
}
