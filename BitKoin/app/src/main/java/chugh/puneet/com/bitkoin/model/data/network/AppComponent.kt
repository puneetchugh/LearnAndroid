package chugh.puneet.com.bitkoin.model.data.network

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf())
interface AppComponent {
    @Component.Builder{
        @BindsInstance fun application (app : Application): Component.Builder
        fun build() : AppComponent
    }

    fun inject(app : Application)
}