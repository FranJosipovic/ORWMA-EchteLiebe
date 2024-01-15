package ormwa.projekt.fran_josipovic.echteliebe.ui.screens.posts.details

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val postDetailsModule = module {
    viewModel { (postId: String) ->
        PostDetailsViewModel(
            postsRepository = get(),
            postId = postId
        )
    }
}
