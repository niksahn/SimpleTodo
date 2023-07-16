package com.example.simpleToDo.ui.main

import com.example.simpleToDo.domain.models.Deal
import com.example.simpleToDo.domain.repositories.DealsRepository
import com.example.simpleToDo.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Job
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val dealsRepository: DealsRepository
) : BaseViewModel<ListScreenState, MainScreenEvent>(ListScreenState()) {
	var dealsJob: Job? = null
	
	init {
		/*
		for (i in 0 until 5) {
			dealsRepository.addDeal(
				"wek",
				"ll",
				date = LocalDate.now(),
			)
		}
		*/
		/*dealsRepository.changeDeal(
				Deal(
					3,
					Tag(0, "second"),
					"2",
					date = LocalDate.now(),
					priority = 3,
					false
				)
			)*/
		launchViewModelScope {
			dealsRepository.loadListOfCurrentDeals(LocalDate.now())
				.subscribe() {
					//	dealsJob?.cancel()
					//	dealsJob = launchViewModelScope {
					addItemsToList(it)
					//	}
				}
		}
		
	}
	
	fun onClickItem(index: Int) {
		currentState.listOfDeals[index].tag?.let { MainScreenEvent.ShowToast(it.name) }
			?.let { trySendEvent(it) }
	}
	
	fun onBack() {
		trySendEvent(MainScreenEvent.GoBack)
	}
	
	private fun addItemsToList(list: ImmutableList<Deal>) {
		updateState {
			it.copy(
				listOfDeals = list,
				isLoading = false
			)
		}
	}
}