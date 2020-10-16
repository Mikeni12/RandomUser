package com.mikeni.randomuser.data.entities

sealed class MainStateEvent {
    object GetUserEvents : MainStateEvent()
    object None : MainStateEvent()
}