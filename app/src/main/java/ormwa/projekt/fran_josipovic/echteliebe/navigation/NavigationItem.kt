package ormwa.projekt.fran_josipovic.echteliebe.navigation

import androidx.annotation.DrawableRes
import ormwa.projekt.fran_josipovic.echteliebe.R

sealed class NavigationItem(val route:String, @DrawableRes val vectorAsset: Int, val label: String) {
    data object Home : NavigationItem("home", R.drawable.home_icon,"Home")
    data object Interaction: NavigationItem("interaction",R.drawable.interaction_icon,"Interaction")
    data object Info: NavigationItem("info",R.drawable.info_icon,"Info")
    data object Chants: NavigationItem("chants",R.drawable.chants_icon,"Chants")
}

public val navigationItems = listOf<NavigationItem>(NavigationItem.Home,NavigationItem.Interaction,NavigationItem.Info,NavigationItem.Chants)