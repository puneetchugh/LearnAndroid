package chugh.puneet.com.bitkoin

import android.app.Activity
import android.app.Application
import chugh.puneet.com.bitkoin.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class AndroidApp: Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector:
            DispatchingAndroidInjector<Activity>

    /*@set:Inject
    internal var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null*/

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> =
            activityDispatchingAndroidInjector
}