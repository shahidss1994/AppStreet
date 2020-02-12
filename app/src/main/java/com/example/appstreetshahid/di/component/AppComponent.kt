package com.example.appstreetshahid.di.component

import com.example.appstreetshahid.di.module.AppModule
import com.example.appstreetshahid.di.module.NetworkModule
import com.example.appstreetshahid.ui.detail.DetailActivity
import com.example.appstreetshahid.ui.home.viewmodel.GitHubViewModel
import com.example.appstreetshahid.ui.home.viewmodel.HomeViewModel
import dagger.Component
import javax.inject.Singleton


/**
 * Created by shahid on 12/8/16.
 */

@Component(modules = [AppModule::class, NetworkModule::class])
@Singleton
interface AppComponent {

    fun inject(homeViewModel: HomeViewModel)
    fun inject(gitHubViewModel: GitHubViewModel)
    fun inject(detailActivity: DetailActivity)

    @Singleton
    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun appModule(appModule: AppModule): Builder
        fun networkModule(networkModule: NetworkModule): Builder
    }

}