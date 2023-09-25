package ir.dunijet.teamgit.util

sealed class MyScreens(val route: String) {
    object HomeScreen : MyScreens("homeScreen")
    object BlogScreen : MyScreens("blogScreen")
    object SearchScreen : MyScreens("searchScreen")
    object LargeImageScreen : MyScreens("LargeImageScreen")
}
