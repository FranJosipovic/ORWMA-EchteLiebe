package ormwa.projekt.fran_josipovic.echteliebe.data.di

import org.koin.dsl.module
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.chants.ChantsRepository
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.chants.ChantsRepositoryImpl
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.interactions.InteractionsRepository
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.interactions.InteractionsRepositoryImpl
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.posts.PostsRepository
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.posts.PostsRepositoryImpl
import ormwa.projekt.fran_josipovic.echteliebe.data.services.chants.ChantsService
import ormwa.projekt.fran_josipovic.echteliebe.data.services.chants.ChantsServiceImpl
import ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions.InteractionsService
import ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions.InteractionsServiceImpl
import ormwa.projekt.fran_josipovic.echteliebe.data.services.mappers.InteractionsServiceMapper
import ormwa.projekt.fran_josipovic.echteliebe.data.services.mappers.PostsServiceMapper
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.PostsService
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.PostsServiceImpl

val dataModule = module {

    single {
        InteractionsServiceMapper()
    }

    single {
        PostsServiceMapper()
    }

    single<ChantsService> {
        ChantsServiceImpl(
            httpClient = get()
        )
    }

    single<ChantsRepository> {
        ChantsRepositoryImpl(
            chantsService = get()
        )
    }

    single<PostsService> {
        PostsServiceImpl(
            postsServiceMapper = get()
        )
    }

    single<PostsRepository> {
        PostsRepositoryImpl(
            postsService = get()
        )
    }

    single<InteractionsService> {
        InteractionsServiceImpl(
            interactionsServiceMapper = get()
        )
    }

    single<InteractionsRepository> {
        InteractionsRepositoryImpl(
            interactionsService = get()
        )
    }
}
