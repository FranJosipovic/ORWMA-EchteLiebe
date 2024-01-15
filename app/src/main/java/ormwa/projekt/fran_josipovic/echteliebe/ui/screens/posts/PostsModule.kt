package ormwa.projekt.fran_josipovic.echteliebe.ui.screens.posts

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val postsModule = module {
    viewModel {
        PostsViewModel(
            postsRepository = get()
        )
    }
}
