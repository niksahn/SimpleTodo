package com.example.simpleToDo.ui.main.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.simpleToDo.R
import com.example.simpleToDo.ui.theme.SimpleToDoTheme
import java.time.LocalDate

@Composable
fun TopSimpleToDoBar(
	month: LocalDate?,
	onClickCalendar: () -> Unit,
	onClickSettings: () -> Unit,
	backgroundColor: Color = MaterialTheme.colors.background
) {
	TopAppBar(backgroundColor = backgroundColor)
	{
		Icon(
			imageVector = Icons.Default.AccountCircle,
			contentDescription = null
		)
		Text(
			modifier = Modifier.weight(0.8F).fillMaxWidth(),
			textAlign = TextAlign.Center,
			text = stringResource(id = R.string.app_name),
			style = MaterialTheme.typography.h5,
		)
		Icon(
			imageVector = Icons.Default.DateRange,
			contentDescription = null,
			modifier = Modifier.clickable(
				onClick = onClickCalendar
			)
		)
		Icon(
			imageVector = Icons.Default.Settings,
			contentDescription = null,
			modifier = Modifier.clickable(
				onClick = onClickSettings
			)
		)
	}
}


@Preview
@Composable
fun TopBarPreview() {
	SimpleToDoTheme {
		TopSimpleToDoBar(null,{},{})
	}
}