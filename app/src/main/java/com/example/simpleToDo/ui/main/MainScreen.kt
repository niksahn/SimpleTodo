package com.example.simpleToDo.ui.main

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.examplet.R
import com.example.simpleToDo.ui.main.views.DealsList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

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
		state = state, onClickItem = viewModel::onClickItem, onBack = viewModel::onBack
	)
}

@Composable
fun ListScreenContent(
	state: ListScreenState,
	onClickItem: (Int) -> Unit,
	onBack: () -> Unit,
) {
	Scaffold(
		topBar = {
			TopAppBar() {
				Text(
					modifier = Modifier.padding(start = 16.dp),
					text = stringResource(id = R.string.app_name),
					style = MaterialTheme.typography.h5,
				)
			}
		}
	) {
		if (state.isLoading) {
			CircularProgressIndicator()
		} else {
			Column(modifier = Modifier.padding(it).fillMaxSize()) {
				DealsList(
					title = stringResource(id = R.string.not_done),
					isOpened = false,
					listOfDeals = state.listOfDeals
				)
				DealsList(
					title = stringResource(id = R.string.done),
					isOpened = false,
					listOfDeals = state.listOfDeals
				)
			}
		}
	}
}