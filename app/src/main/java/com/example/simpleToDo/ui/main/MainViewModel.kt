package com.example.simpleToDo.ui.main

import com.example.simpleToDo.domain.repositories.DealsRepository
import com.example.simpleToDo.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dealsRepository: DealsRepository
) : BaseViewModel<ListScreenState, MainScreenEvent>(ListScreenState()) {

    init {
        launchViewModelScope {
            launch {
                val list = dealsRepository.loadListOfCurrentDeals().map {
                    it.mapToItemState()
                }
                addItemsToList(list)
            }
        }
    }

    fun onClickItem(index: Int) {
        trySendEvent(MainScreenEvent.ShowToast(currentState.listOfDeals[index].title))
    }

    fun onBack() {
        trySendEvent(MainScreenEvent.GoBack)
    }

    private fun addItemsToList(list: List<DealsState>) {
        updateState {
            it.copy(
                listOfDeals = (it.listOfDeals + list).toImmutableList(),
                isLoading = false
            )
        }
    }
}