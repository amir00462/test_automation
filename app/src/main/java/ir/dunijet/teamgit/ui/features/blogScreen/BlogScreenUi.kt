package ir.dunijet.teamgit.ui.features.blogScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import ir.dunijet.teamgit.ui.widgets.BlogContent
import ir.dunijet.teamgit.ui.widgets.BlogInfoDialog
import ir.dunijet.teamgit.ui.widgets.BlogToolbar
import ir.dunijet.teamgit.util.Cache
import ir.dunijet.teamgit.util.KEY_BLOG
import ir.dunijet.teamgit.util.MyScreens
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BlogScreenUi() {
    val navigation = getNavController()
    var showInfoDialog by remember { mutableStateOf(false) }
    val blog = Cache.get(KEY_BLOG)

    val systemUiController = rememberSystemUiController()
    LaunchedEffect(Unit) {
        delay(25)
        systemUiController.setNavigationBarColor(color = Color.White)
        systemUiController.setStatusBarColor(color = Color.White, darkIcons = true)
    }
    Scaffold(
        topBar = {
            BlogToolbar(
                titleArticle = blog.title,
                onBackClicked = { navigation.popBackStack() },
                onInfoClicked = { showInfoDialog = true })
        },
        modifier = Modifier.fillMaxSize()
    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

            BlogContent(blog) {
                navigation.navigate(MyScreens.LargeImageScreen.route)
            }

            if (showInfoDialog) {
                BlogInfoDialog(blog) {
                    showInfoDialog = false
                }
            }

        }
    }
}