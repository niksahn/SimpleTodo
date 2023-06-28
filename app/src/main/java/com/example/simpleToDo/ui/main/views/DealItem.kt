package com.example.simpleToDo.ui.main.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.simpleToDo.ui.main.DealsState

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ListItem(
    data: DealsState,
    modifier: Modifier = Modifier
) {

}

@Preview
@Composable
private fun ListItemPreview() {
    ListItem(
        data = DealsState(
            title = "title",
            description = "subTitle",
            data = 500.5f,
        )
    )
}