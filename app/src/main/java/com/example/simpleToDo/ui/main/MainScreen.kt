package com.example.simpleToDo.ui.main

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.simpleToDo.R
import com.example.simpleToDo.ui.main.views.DaysColumn
import com.example.simpleToDo.ui.main.views.DealsList
import com.example.simpleToDo.ui.main.views.TopSimpleToDoBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate

@RootNavGraph(start = true)
@Destination
@Composable
fun ListScreen(
	navigator: DestinationsNavigator,
	viewModel: MainViewModel = hiltViewModel()
) {
	val state by viewModel.screenState.collectAsStateWithLifecycle()
	val context = LocalContext.current
	LaunchedEffect(Unit) {
		viewModel.event.collect {
			when (it) {
				is MainScreenEvent.ShowToast -> Toast.makeText(
					context, it.text, Toast.LENGTH_LONG
				).show()
				
				is MainScreenEvent.GoBack -> navigator.navigateUp()
			}
		}
	}
	ListScreenContent(
		state = state, onClickItem = viewModel::onClickItem, onBack = viewModel::onBack, onDayClick = viewModel::onDayClick
	)
}

@Composable
fun ListScreenContent(
	state: ListScreenState,
	onDayClick: (LocalDate) -> Unit,
	onClickItem: (Int) -> Unit,
	onBack: () -> Unit,
) {
	Scaffold(
		topBar = {
			TopSimpleToDoBar(state.selectedDay, {}, {})
		}
	) {
		if (state.isLoading) {
			CircularProgressIndicator()
		} else {
			Column(
				modifier = Modifier
					.padding(it)
					.fillMaxSize()
			) {
				DaysColumn(
					modifier = Modifier.fillMaxWidth(),
					selectedDay = state.selectedDay,
					listOfDays = state.listOfDays,
					onDayClick = onDayClick
				)
				DealsList(
					title = stringResource(id = R.string.not_done),
					isOpened = false,
					listOfDeals = state.listOfDeals.filter { !it.done }.toImmutableList()
				)
				DealsList(
					title = stringResource(id = R.string.done),
					isOpened = false,
					listOfDeals = state.listOfDeals.filter { it.done }.toImmutableList()
				)
			}
		}
	}
}