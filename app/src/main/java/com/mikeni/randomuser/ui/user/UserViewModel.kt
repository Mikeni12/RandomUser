package com.mikeni.randomuser.ui.user

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mikeni.randomuser.data.entities.MainStateEvent
import com.mikeni.randomuser.data.entities.User
import com.mikeni.randomuser.data.repository.UserRepository
import com.mikeni.randomuser.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class UserViewModel @ViewModelInject constructor(
    private val repository: UserRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataState = MutableLiveData<DataState<User>>()
    val dataState: LiveData<DataState<User>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetUserEvents -> {
                    repository.getRandomUser()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is MainStateEvent.None -> { }
            }
        }
    }
}