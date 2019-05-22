package chugh.puneet.com.bitkoin.ui

import chugh.puneet.com.bitkoin.model.data.network.NetworkService
import chugh.puneet.com.bitkoin.viewmodel.MainActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule{

    @Provides
    fun providesViewModel(networkService: NetworkService) = MainActivityViewModel(networkService)

}