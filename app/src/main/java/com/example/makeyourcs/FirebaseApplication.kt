package com.example.makeyourcs

import android.app.Application
import com.example.makeyourcs.data.Repository.AccountRepository
import com.example.makeyourcs.data.firebase.FirebaseAuthSource
import com.example.makeyourcs.ui.auth.AuthViewModelFactory
import com.example.makeyourcs.ui.home.HomeViewModelFactory
import com.example.makeyourcs.ui.user.UserViewModelFactory

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

        bind() from singleton { FirebaseAuthSource() }
        bind() from singleton { AccountRepository(instance()) }
        bind<AuthViewModelFactory>() with provider { AuthViewModelFactory(instance()) }
        bind<HomeViewModelFactory>() with provider { HomeViewModelFactory(instance())}
        bind<UserViewModelFactory>() with provider {UserViewModelFactory(instance())}
    }
}
