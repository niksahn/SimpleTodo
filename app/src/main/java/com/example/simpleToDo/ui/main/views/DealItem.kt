package com.example.simpleToDo.ui.main.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.simpleToDo.domain.models.Deal
import com.example.simpleToDo.domain.models.Tag
import java.time.LocalDate

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ListItem(
	data: Deal,
	modifier: Modifier = Modifier
) {

}

@Preview
@Composable
private fun ListItemPreview() {
	ListItem(
		data = Deal(
			tag = Tag("title"),
			description = "subTitle",
			done = false,
			date = LocalDate.now(),
			id = 0,
			priority = 0
		)
	)
}