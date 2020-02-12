package com.example.appstreetshahid.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appstreetshahid.ui.home.viewmodel.GitHubViewModel
import com.example.appstreetshahid.ui.home.viewmodel.HomeViewModel

/**
 * Created by shahid on 12/8/16.
 */

class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(activity) as T
        } else if (modelClass.isAssignableFrom(GitHubViewModel::class.java)) {
            return GitHubViewModel(activity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}