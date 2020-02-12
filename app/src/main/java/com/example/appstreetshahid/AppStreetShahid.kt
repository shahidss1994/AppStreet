package com.example.appstreetshahid

import android.app.Application
import com.example.appstreetshahid.di.component.AppComponent
import com.example.appstreetshahid.di.component.DaggerAppComponent
import com.example.appstreetshahid.di.module.AppModule
import com.example.appstreetshahid.di.module.NetworkModule

/**
 * Created by shahid on 12/8/16.
 */

class AppStreetShahid : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this@AppStreetShahid)).networkModule(NetworkModule).build()
    }

}