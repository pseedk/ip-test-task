package ru.pvkovalev.ip_test_task.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.pvkovalev.ip_test_task.utils.Constants.EMPTY_STRING
import com.github.pvkovalev.ip_test_task.utils.Constants.ONE_THOUSAND
import org.json.JSONArray
import org.json.JSONException
import ru.pvkovalev.ip_test_task.R
import ru.pvkovalev.ip_test_task.domain.model.Item
import ru.pvkovalev.ip_test_task.presentation.ui.theme.AlertBackgroundColor
import ru.pvkovalev.ip_test_task.presentation.ui.theme.AlertIconColor
import ru.pvkovalev.ip_test_task.presentation.ui.theme.AppBarColor
import ru.pvkovalev.ip_test_task.presentation.ui.theme.BackgroundColor
import ru.pvkovalev.ip_test_task.presentation.ui.theme.CheckedSearchTextFieldColor
import ru.pvkovalev.ip_test_task.presentation.ui.theme.ChipBorderColor
import ru.pvkovalev.ip_test_task.presentation.ui.theme.DeleteIconColor
import ru.pvkovalev.ip_test_task.presentation.ui.theme.EditButtonsColor
import ru.pvkovalev.ip_test_task.presentation.ui.theme.EditIconColor
import ru.pvkovalev.ip_test_task.presentation.ui.theme.TextColor
import ru.pvkovalev.ip_test_task.presentation.ui.theme.UncheckedSearchTextFieldColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val mainScreenViewModel: MainScreenViewModel = hiltViewModel()
    val items = mainScreenViewModel.items.collectAsState(initial = listOf()).value
    val textState = remember { mutableStateOf(TextFieldValue(EMPTY_STRING)) }

    LaunchedEffect(textState.value.text) {
        mainScreenViewModel.searchItems(textState.value.text)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppBarColor),
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.list_of_products),
                        color = TextColor,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor),
            contentPadding = paddingValues

        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                SearchItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    state = textState
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(
                items = items,
                key = { it.id }
            ) { item ->
                Item(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    item = item,
                    onEditAcceptClick = { amount ->
                        val editItem = Item(
                            id = item.id,
                            name = item.name,
                            time = item.time,
                            tags = item.tags,
                            amount = amount
                        )
                        mainScreenViewModel.editItem(item = editItem)
                    },
                    onDeleteAcceptClick = {
                        mainScreenViewModel.deleteItem(item = item)
                    }
                )
            }
        }
    }
}

@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    state: MutableState<TextFieldValue>
) {
    OutlinedTextField(
        modifier = modifier,
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = stringResource(R.string.search_icon),
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        label = {
            Text(
                text = stringResource(R.string.search_products),
                color = TextColor
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue(EMPTY_STRING)) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue(EMPTY_STRING)
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = stringResource(R.string.clear_search_text),
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedTextColor = TextColor,
            cursorColor = TextColor,
            focusedLeadingIconColor = TextColor,
            unfocusedLeadingIconColor = TextColor,
            focusedTrailingIconColor = TextColor,
            unfocusedTrailingIconColor = TextColor,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedLabelColor = TextColor,
            unfocusedLabelColor = TextColor,
            focusedIndicatorColor = CheckedSearchTextFieldColor,
            unfocusedIndicatorColor = UncheckedSearchTextFieldColor,
            disabledIndicatorColor = TextColor
        )
    )
}


@Composable
private fun Item(
    modifier: Modifier = Modifier,
    item: Item,
    onEditAcceptClick: (Int) -> Unit,
    onDeleteAcceptClick: () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            EditRow(
                item = item,
                onEditAcceptClick = { amount ->
                    onEditAcceptClick(amount)
                },
                onDeleteAcceptClick = {
                    onDeleteAcceptClick()
                }
            )
            TagsRow(
                tags = item.tags
            )
            Row {
                ItemInfo(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.in_stock),
                    description = item.amount.toString()
                )
                Spacer(modifier = Modifier.width(8.dp))
                val time = item.time
                if (time != 0) {
                    ItemInfo(
                        modifier = Modifier.weight(1f),
                        title = stringResource(R.string.date_added),
                        description = mapTimestampToDate(time.toLong() * ONE_THOUSAND)
                    )
                }
            }
        }
    }
}

@Composable
private fun EditRow(
    modifier: Modifier = Modifier,
    item: Item,
    onEditAcceptClick: (Int) -> Unit,
    onDeleteAcceptClick: () -> Unit
) {
    val editDialogVisibleState = rememberSaveable { mutableStateOf(false) }
    val deleteDialogVisibleState = rememberSaveable { mutableStateOf(false) }
    val amount = rememberSaveable { mutableIntStateOf(item.amount) }

    when {
        editDialogVisibleState.value -> {
            EditAlert(
                modifier = Modifier.padding(8.dp),
                visibleState = editDialogVisibleState,
                amount = amount,
                onAcceptClick = {
                    onEditAcceptClick(amount.intValue)
                }
            )
        }

        deleteDialogVisibleState.value -> {
            DeleteAlert(
                modifier = Modifier.padding(8.dp),
                visibleState = deleteDialogVisibleState,
                onAcceptClick = {
                    onDeleteAcceptClick()
                }
            )
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val itemName = item.name
        if (itemName.isNotBlank()) {
            Text(
                modifier = Modifier.weight(1f),
                text = itemName,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = TextColor
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = {
                editDialogVisibleState.value = !editDialogVisibleState.value
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = stringResource(R.string.edit_item),
                tint = EditIconColor
            )
        }
        IconButton(
            onClick = {
                deleteDialogVisibleState.value = !deleteDialogVisibleState.value
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = stringResource(R.string.delete_item),
                tint = DeleteIconColor
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TagsRow(
    modifier: Modifier = Modifier,
    tags: String
) {
    FlowRow(modifier = modifier) {
        if (tags.isNotBlank()) {
            val chipTags = tags.parseString() ?: listOf()
            if (chipTags.isNotEmpty()) {
                chipTags.forEach { tag ->
                    SuggestionChip(
                        onClick = {},
                        border = BorderStroke(
                            width = 1.dp,
                            color = ChipBorderColor
                        ),
                        shape = RoundedCornerShape(8.dp),
                        colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color.White),
                        label = {
                            Text(
                                text = tag,
                                fontWeight = FontWeight.Bold,
                                color = TextColor,
                                fontSize = 14.sp
                            )
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
private fun ItemInfo(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = TextColor,
            fontSize = 14.sp
        )
        Text(
            text = description,
            color = TextColor,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun EditAlert(
    modifier: Modifier = Modifier,
    amount: MutableState<Int>,
    visibleState: MutableState<Boolean>,
    onAcceptClick: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { visibleState.value = false },
        confirmButton = {
            TextButton(
                onClick = {
                    visibleState.value = !visibleState.value
                    onAcceptClick()
                }
            ) {
                Text(
                    text = stringResource(R.string.accept)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    visibleState.value = false
                }
            ) {
                Text(
                    text = stringResource(R.string.cancel)
                )
            }
        },
        shape = RoundedCornerShape(percent = 10),
        containerColor = AlertBackgroundColor,
        icon = {
            Icon(
                painter = painterResource(R.drawable.ic_settings),
                contentDescription = stringResource(R.string.settings_icon),
                tint = AlertIconColor
            )
        },
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.product_count),
                color = TextColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = {
                        if (amount.value > 0) {
                            amount.value--
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_remove),
                        contentDescription = stringResource(R.string.remove_count),
                        tint = EditButtonsColor,
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = amount.value.toString(),
                    color = TextColor,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(
                    onClick = {
                        amount.value++
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_circle),
                        contentDescription = stringResource(R.string.add_count),
                        tint = EditButtonsColor
                    )
                }
            }
        }
    )
}

@Composable
private fun DeleteAlert(
    modifier: Modifier = Modifier,
    visibleState: MutableState<Boolean>,
    onAcceptClick: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { visibleState.value = false },
        confirmButton = {
            TextButton(
                onClick = {
                    visibleState.value = !visibleState.value
                    onAcceptClick()
                }
            ) {
                Text(
                    text = stringResource(R.string.yes)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    visibleState.value = false
                }
            ) {
                Text(
                    text = stringResource(R.string.no)
                )
            }
        },
        containerColor = AlertBackgroundColor,
        shape = RoundedCornerShape(percent = 10),
        icon = {
            Icon(
                painter = painterResource(R.drawable.ic_warning),
                contentDescription = stringResource(R.string.settings_icon),
                tint = AlertIconColor
            )
        },
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.delete_product),
                color = TextColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.accept_delete),
                color = TextColor,
                fontSize = 16.sp
            )
        }
    )
}


private fun String.parseString(): List<String>? {
    return try {
        val jsonArray = JSONArray(this)
        val stringList = mutableListOf<String>()
        for (i in 0 until jsonArray.length()) {
            stringList.add(jsonArray.getString(i))
        }
        stringList
    } catch (e: JSONException) {
        e.printStackTrace()
        null
    }
}

private fun mapTimestampToDate(timestamp: Long): String {
    val date = Date(timestamp)
    return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
}
