package com.example.simpleToDo.ui.main.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.simpleToDo.domain.models.Tag
import com.example.simpleToDo.ui.models.DealUi
import java.time.LocalDate

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ListItem(
	data: DealUi,
	modifier: Modifier = Modifier
) {
	Spacer(modifier = Modifier.height(20.dp))
	Text(text = data.description)
}

@Preview
@Composable
private fun ListItemPreview() {
	ListItem(
		data = DealUi(
			tag = Tag(0, "title"),
			description = "subTitle",
			done = false,
			date = LocalDate.now(),
			id = 0,
			priority = 0
		)
	)
}