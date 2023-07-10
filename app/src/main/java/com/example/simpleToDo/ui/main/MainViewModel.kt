package com.example.simpleToDo.ui.main

import com.example.simpleToDo.domain.models.Deal
import com.example.simpleToDo.domain.repositories.DealsRepository
import com.example.simpleToDo.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dealsRepository: DealsRepository
) : BaseViewModel<ListScreenState, MainScreenEvent>(ListScreenState()) {

    init {
        launchViewModelScope {
            launch {
                 dealsRepository.loadListOfCurrentDeals(LocalDate.now()).subscribe {
                     addItemsToList(it)
                 }
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

    private fun addItemsToList(list: List<Deal>) {
        updateState {
            it.copy(
                listOfDeals = (it.listOfDeals + list).toImmutableList(),
                isLoading = false
            )
        }
    }
}