package com.example.simpleToDo.ui.main

import com.example.simpleToDo.domain.repositories.DealsRepository
import com.example.simpleToDo.domain.usecases.GetDealUseCase
import com.example.simpleToDo.ui.models.DealUi
import com.example.simpleToDo.ui.utils.getWeek
import com.example.simpleToDo.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
	private val dealsRepository: DealsRepository,
	private val getDealUseCase: GetDealUseCase
) : BaseViewModel<ListScreenState, MainScreenEvent>(ListScreenState()) {
	
	var flowJob: Job? = null
	
	private fun subscribeToDealFlow() {
		launchViewModelScope {
			flowJob = getDealUseCase.run(currentState.selectedDay)
				.subscribe() {
					addItemsToList(it.toImmutableList())
				}
		}
	}
	
	init {
		/*
		for (i in 0 until 5) {
			dealsRepository.addDeal(
				"wek",
				"ll",
				date = LocalDate.now(),
			)
		}
		dealsRepository.changeDeal(id = 7, date = LocalDate.now().minusDays(1))
		*/
		updateState { it.copy(listOfDays = it.selectedDay.getWeek().toImmutableList()) }
		subscribeToDealFlow()
	}
	
	fun onClickItem(index: Int) {
		currentState.listOfDeals[index].tag?.let { MainScreenEvent.ShowToast(it.name) }
			?.let { trySendEvent(it) }
	}
	
	fun onDayClick(date: LocalDate) {
		updateState { it.copy(selectedDay = date) }
		flowJob?.cancel()
		subscribeToDealFlow()
	}
	
	fun onBack() {
		trySendEvent(MainScreenEvent.GoBack)
	}
	
	private fun addItemsToList(list: ImmutableList<DealUi>) {
		updateState {
			it.copy(
				listOfDeals = list,
				isLoading = false
			)
		}
	}
}