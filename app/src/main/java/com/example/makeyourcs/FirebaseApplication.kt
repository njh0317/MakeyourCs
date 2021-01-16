package com.example.makeyourcs

import android.app.Application
import com.example.makeyourcs.data.Repository.AccountRepository
import com.example.makeyourcs.data.firebase.FirebaseSource
import com.example.makeyourcs.ui.auth.AuthViewModelFactory

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class FirebaseApplication : Application(), KodeinAware{

    override val kodein = Kodein.lazy {
        import(androidXModule(this@FirebaseApplication))

        bind() from singleton { FirebaseSource() }
        bind() from singleton { AccountRepository(instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        //bind() from provider { HomeViewModelFactory(instance()) }

    }
}