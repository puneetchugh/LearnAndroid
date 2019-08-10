package chugh.puneet.com.bitkoin.di.builder

import chugh.puneet.com.bitkoin.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector()
    abstract fun bindMainActivity(): MainActivity
}