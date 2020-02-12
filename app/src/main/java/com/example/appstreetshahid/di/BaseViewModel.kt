package com.example.appstreetshahid.di

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.appstreetshahid.AppStreetShahid
import com.example.appstreetshahid.di.component.AppComponent
import com.example.appstreetshahid.ui.home.viewmodel.GitHubViewModel
import com.example.appstreetshahid.ui.home.viewmodel.HomeViewModel

/**
 * Created by shahid on 12/8/16.
 */

abstract class BaseViewModel(activity: Activity) : ViewModel() {

    private val injector: AppComponent = (activity.application as AppStreetShahid).appComponent

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is HomeViewModel -> injector.inject(this)
            is GitHubViewModel -> injector.inject(this)
        }
    }
}