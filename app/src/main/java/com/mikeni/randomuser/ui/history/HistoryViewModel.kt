package com.mikeni.randomuser.ui.history

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mikeni.randomuser.data.entities.MainStateEvent
import com.mikeni.randomuser.data.entities.User
import com.mikeni.randomuser.data.repository.UserRepository
import com.mikeni.randomuser.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HistoryViewModel @ViewModelInject constructor(
    private val repository: UserRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataState = MutableLiveData<DataState<List<User>>>()
    val dataState: LiveData<DataState<List<User>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetUserEvents -> {
                    repository.getRecentRandomUsers()
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