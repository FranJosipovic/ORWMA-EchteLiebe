package ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val interactionsModule = module {
    viewModel {
        InteractionsViewModel(
            interactionsRepository = get()
        )
    }
}