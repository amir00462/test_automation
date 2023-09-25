package ir.dunijet.teamgit.ui.features.searchScreen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import ir.dunijet.teamgit.ui.widgets.SearchContent
import ir.dunijet.teamgit.ui.widgets.SearchDialog
import ir.dunijet.teamgit.ui.widgets.SearchToolbar
import ir.dunijet.teamgit.util.NO_FILTER

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreenUi() {
    val viewModel = getNavViewModel<SearchViewModel>()
    val activity = (LocalContext.current as? Activity)
    val scope = rememberCoroutineScope()
    val navigation = getNavController()
    var showFilterDialog by remember { mutableStateOf(false) }

    val data by viewModel.blogs.collectAsState()
    val categoryList by viewModel.categoryList.collectAsState()
    val authorList by viewModel.authors.collectAsState()
    val searchedQuery by viewModel.searchQuery.collectAsState()
    val filtering by viewModel.filtering.collectAsState()

    var isFilterEnabled by remember { mutableStateOf(filtering != NO_FILTER) }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.setSearchQuery("")
        }
    }
    Scaffold(
        topBar = {
            SearchToolbar(
                edtValue = searchedQuery,
                isFilterEnabled = isFilterEnabled,
                onEdtChanged = { viewModel.setSearchQuery(it) },
                onFilterClicked = { showFilterDialog = !showFilterDialog },
                onBackPressed = { navigation.popBackStack() }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

            SearchContent(data)

            if (showFilterDialog) {
                SearchDialog(
                    filtering = filtering,
                    categoryList = categoryList,
                    authorList = authorList,
                    onDismissClicked = { showFilterDialog = false },
                    onSubmitClicked = {
                        isFilterEnabled = !(it.authors.isEmpty() && it.categories.isEmpty())
                        viewModel.changeFiltering(it)
                    }
                )
            }

        }

    }


}

