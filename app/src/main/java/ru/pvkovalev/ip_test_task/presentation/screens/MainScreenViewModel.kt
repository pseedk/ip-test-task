package ru.pvkovalev.ip_test_task.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.pvkovalev.ip_test_task.domain.model.Item
import ru.pvkovalev.ip_test_task.domain.usecase.DeleteItemUseCase
import ru.pvkovalev.ip_test_task.domain.usecase.EditItemUseCase
import ru.pvkovalev.ip_test_task.domain.usecase.GetItemsUseCase
import ru.pvkovalev.ip_test_task.domain.usecase.SearchItemsUseCase
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase,
    private val searchItemsUseCase: SearchItemsUseCase,
    private val editItemUseCase: EditItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase
) : ViewModel() {

    private val _items = MutableStateFlow<List<Item>>(listOf())
    val items = _items.asStateFlow()

    init {
        getItems()
    }

    private fun getItems() {
        viewModelScope.launch {
            getItemsUseCase.invoke().collectLatest {
                _items.value = it
            }
        }
    }

    fun searchItems(query: String) {
        viewModelScope.launch {
            searchItemsUseCase.invoke(query = query).collectLatest {
                _items.value = it
            }
        }
    }

    fun editItem(item: Item) {
        viewModelScope.launch {
            editItemUseCase.invoke(item = item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            deleteItemUseCase.invoke(item = item)
        }
    }

}