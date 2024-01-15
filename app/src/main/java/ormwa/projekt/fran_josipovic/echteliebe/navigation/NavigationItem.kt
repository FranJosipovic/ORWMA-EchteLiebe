package ormwa.projekt.fran_josipovic.echteliebe.navigation

import androidx.annotation.DrawableRes
import ormwa.projekt.fran_josipovic.echteliebe.R

sealed class NavigationDestination(open val route: String)
sealed class NavigationItem(
    override val route: String,
    @DrawableRes val vectorAsset: Int,
    val label: String
) : NavigationDestination(route) {
    data object Home : NavigationItem("home", R.drawable.home_icon, "Home")
    data object Interaction :
        NavigationItem("interaction", R.drawable.interaction_icon, "Interaction")

    data object Info : NavigationItem("info", R.drawable.info_icon, "Info")
    data object Chants : NavigationItem("chants", R.drawable.chants_icon, "Chants")

}

const val POST_DETAILS_ROUTE_WITH_PARAMS = "home/{postId}"
const val INTERACTION_DETAILS_ROUTE_WITH_PARAMS = "interaction/{interactionId}"

data object InteractionDetailsDestination : NavigationDestination(
    INTERACTION_DETAILS_ROUTE_WITH_PARAMS
) {
    fun createNavigationRoute(interactionId: String): String = "interaction/$interactionId"
}

data object PostDetailsDestination : NavigationDestination(POST_DETAILS_ROUTE_WITH_PARAMS) {
    fun createNavigationRoute(postId: String): String = "home/$postId"
}

val navigationItems = listOf(
    NavigationItem.Home,
    NavigationItem.Interaction,
    NavigationItem.Info,
    NavigationItem.Chants
)
