package ir.dunijet.teamgit.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.navigation.KoinNavHost
import ir.dunijet.teamgit.data.model.Blog
import ir.dunijet.teamgit.di.myModules
import ir.dunijet.teamgit.ui.features.LargeImageScreen
import ir.dunijet.teamgit.ui.features.blogScreen.BlogScreenUi
import ir.dunijet.teamgit.ui.features.homeScreen.HomeScreenUi
import ir.dunijet.teamgit.ui.features.searchScreen.SearchScreenUi
import ir.dunijet.teamgit.ui.theme.Article_App_ComposeTheme
import ir.dunijet.teamgit.ui.theme.cBackground
import ir.dunijet.teamgit.util.MyScreens
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContent {

            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

                Koin(appDeclaration = {
                    androidContext(this@MainActivity)
                    modules(myModules)
                }) {

                    Article_App_ComposeTheme {

                        val uiController = rememberSystemUiController()
                        SideEffect { uiController.setStatusBarColor(cBackground) }

                        Surface(color = cBackground, modifier = Modifier.fillMaxSize()) {
                            ArticleApp()
                        }

                    }

                }

            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticleApp() {

    val navController = rememberNavController()
    KoinNavHost(navController = navController, startDestination = MyScreens.HomeScreen.route) {

        composable(MyScreens.HomeScreen.route) {
            HomeScreenUi()
        }

        composable(MyScreens.BlogScreen.route) {
            BlogScreenUi()
        }

        composable(MyScreens.SearchScreen.route) {
            SearchScreenUi()
        }

        composable(MyScreens.LargeImageScreen.route) {
            LargeImageScreen()
        }

    }

}