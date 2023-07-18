package com.example.simpleToDo.ui.main

import com.example.simpleToDo.domain.repositories.DealsRepository
import com.example.simpleToDo.domain.usecases.GetDealUseCase
import com.example.simpleToDo.ui.models.DealUi
import com.example.simpleToDo.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val dealsRepository: DealsRepository,
	private val getDealUseCase: GetDealUseCase
) : BaseViewModel<ListScreenState, MainScreenEvent>(ListScreenState()) {
	
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
					0,
					"2",
					date = LocalDate.now(),
					priority = 3,
					false
				)
			)*/
		launchViewModelScope {
			getDealUseCase.run(LocalDate.now())
				.subscribe() {
					addItemsToList(it.toImmutableList())
				}
			delay(2000)
		}
		
	}
	
	fun onClickItem(index: Int) {
		currentState.listOfDeals[index].tag?.let { MainScreenEvent.ShowToast(it.name) }
			?.let { trySendEvent(it) }
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