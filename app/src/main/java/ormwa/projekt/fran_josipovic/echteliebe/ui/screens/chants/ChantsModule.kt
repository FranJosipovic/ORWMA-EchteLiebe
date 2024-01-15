package ormwa.projekt.fran_josipovic.echteliebe.ui.screens.chants

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val chantsModule = module {
    viewModel {
        ChantsViewModel(
            repository = get()
        )
    }
}
