package ormwa.projekt.fran_josipovic.echteliebe.auth

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val signInViewModelModule = module {
    viewModel{
        SignInViewModel(

        )
    }
}