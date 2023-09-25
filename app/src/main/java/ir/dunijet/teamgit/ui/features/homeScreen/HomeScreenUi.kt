package ir.dunijet.teamgit.ui.features.homeScreen

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import dev.burnoo.cokoin.navigation.getNavViewModel
import ir.dunijet.teamgit.ui.theme.cBackground
import ir.dunijet.teamgit.ui.widgets.HomeContent
import ir.dunijet.teamgit.ui.widgets.HomeDrawer
import ir.dunijet.teamgit.ui.widgets.HomeToolbar
import kotlinx.coroutines.launch
import androidx.paging.compose.collectAsLazyPagingItems
import dev.burnoo.cokoin.navigation.getNavController
import ir.dunijet.teamgit.data.model.Blog
import ir.dunijet.teamgit.data.model.BlogResponse
import ir.dunijet.teamgit.data.net.ApiService
import ir.dunijet.teamgit.util.MyScreens
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenUi() {

    val viewModel = getNavViewModel<HomeViewModel>()
    val activity = (LocalContext.current as? Activity)
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navigation = getNavController()

    val data by viewModel.blogs.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            HomeToolbar(
                onDrawerClicked = {
                    scope.launch { scaffoldState.drawerState.open() }
                },
                onSearchClicked = {
                    navigation.navigate(MyScreens.SearchScreen.route)
                })
        },
        modifier = Modifier.fillMaxSize(),
        drawerGesturesEnabled = true,
        drawerContent = {
            HomeDrawer() {

                scope.launch {

                    if (scaffoldState.drawerState.currentValue == DrawerValue.Open) {
                        scaffoldState.drawerState.close()
                    } else {
                        activity?.finish()
                    }

                }
            }
        },
        drawerElevation = 2.dp,
        drawerBackgroundColor = cBackground
    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center)
                )
            } else {
                HomeContent(data) {
                    viewModel.fetchBlogs()
                }
            }

        }
    }
}