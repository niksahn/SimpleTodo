package com.example.simpleToDo.ui.main

import com.example.simpleToDo.utils.base.Event

sealed class MainScreenEvent: Event() {
    class ShowToast(val text: String): MainScreenEvent()
    object GoBack: MainScreenEvent()
}
