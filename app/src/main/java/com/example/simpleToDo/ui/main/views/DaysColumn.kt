package com.example.simpleToDo.ui.main.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpleToDo.ui.utils.toDayNumber
import com.example.simpleToDo.ui.utils.toShortDayString
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate

@Composable
fun DaysColumn(
	selectedDay: LocalDate,
	listOfDays: ImmutableList<LocalDate>,
	modifier: Modifier = Modifier,
	onDayClick: (LocalDate) -> Unit
) {
	Row(
		modifier = modifier,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		listOfDays.forEach {
			DayItem(day = it, onDayClick = onDayClick, selected = it == selectedDay)
		}
	}
}

@Composable
fun DayItem(
	day: LocalDate,
	onDayClick: (LocalDate) -> Unit,
	selected: Boolean,
	modifier: Modifier = Modifier,
) {
	Column(
		modifier = modifier.clickable(onClick = remember(day) { { onDayClick(day) } }),
		verticalArrangement = Arrangement.spacedBy(8.dp)
	
	) {
		Text(
			modifier = Modifier.background(color = if (selected) MaterialTheme.colors.secondary else MaterialTheme.colors.background),
			color = if (selected) MaterialTheme.colors.background else MaterialTheme.colors.primaryVariant,
			text = stringResource(id = day.toShortDayString())
		)
		Text(
			modifier = Modifier.background(color = if (selected) MaterialTheme.colors.primarySurface else MaterialTheme.colors.background),
			text = day.toDayNumber()
		)
	}
}

@Preview
@Composable
fun DaysColumnPreview() {

}