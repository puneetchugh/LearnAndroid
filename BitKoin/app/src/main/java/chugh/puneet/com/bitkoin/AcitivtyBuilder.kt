package chugh.puneet.com.bitkoin

import chugh.puneet.com.bitkoin.ui.MainActivity
import chugh.puneet.com.bitkoin.ui.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun bindMainActivity(): MainActivity
}