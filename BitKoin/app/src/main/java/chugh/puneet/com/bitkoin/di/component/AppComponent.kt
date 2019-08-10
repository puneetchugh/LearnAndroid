package chugh.puneet.com.bitkoin.di.component

import android.app.Application
import chugh.puneet.com.bitkoin.AndroidApp
import chugh.puneet.com.bitkoin.di.builder.ActivityBuilder
import chugh.puneet.com.bitkoin.model.data.network.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityBuilder::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application : Application): Builder
        fun build(): AppComponent
    }

    fun inject(application : AndroidApp)
}