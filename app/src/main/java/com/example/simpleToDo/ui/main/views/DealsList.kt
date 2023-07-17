package com.example.simpleToDo.ui.main.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simpleToDo.ui.models.DealUi
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DealsList(
	title: String,
	isOpened: Boolean,
	listOfDeals: ImmutableList<DealUi?>
) {
	Column {
		ListTitle(title = title, isOpened = isOpened)
		LazyColumn(
			modifier = Modifier,
			verticalArrangement = Arrangement.spacedBy(8.dp),
			contentPadding = PaddingValues(horizontal = 8.dp)
		) {
			itemsIndexed(listOfDeals) { index, itemData ->
				if (itemData != null) {
					ListItem(
						modifier = Modifier
							.fillMaxWidth()
							.clickable(onClick = {})
							.animateItemPlacement(),
						data = itemData
					)
				}
			}
		}
	}
}