package ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions.details

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val interactionDetailsModule = module {
    viewModel { (id: String) ->
        InteractionDetailsViewModel(interactionsRepository = get(), interactionId = id)
    }
}
