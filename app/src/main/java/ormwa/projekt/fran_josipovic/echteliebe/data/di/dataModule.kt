package ormwa.projekt.fran_josipovic.echteliebe.data.di

import org.koin.dsl.module
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.ChantsRepository
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.ChantsRepositoryImpl
import ormwa.projekt.fran_josipovic.echteliebe.data.services.chants.ChantsService
import ormwa.projekt.fran_josipovic.echteliebe.data.services.chants.ChantsServiceImpl

val dataModule = module {
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
}