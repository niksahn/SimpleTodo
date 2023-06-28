package com.example.simpleToDo.ui.main.views

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ListTitle(
	title: String,
	isOpened: Boolean
) {
	Row {
		Text(text = title, modifier = Modifier.weight(1F))
		Icon(
			imageVector = if (isOpened) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
			contentDescription = null
		)
	}
}