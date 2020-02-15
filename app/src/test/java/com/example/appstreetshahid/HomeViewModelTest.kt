package com.example.appstreetshahid

import android.app.Activity
import com.example.appstreetshahid.di.component.AppComponent
import com.example.appstreetshahid.ui.home.viewmodel.HomeViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @Mock
    private lateinit var application: AppStreetShahid

    @Mock
    private lateinit var activity: Activity

    @Mock
    private lateinit var appComponent: AppComponent

    private lateinit var homeViewModel: HomeViewModel

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        (activity.application as AppStreetShahid).appComponent = application.appComponent
        homeViewModel = Mockito.spy(HomeViewModel(activity))
    }

    @Test
    fun getPostFailure() {
        homeViewModel.onRetrievePostListStart()

        homeViewModel.onRetrievePostListError(Throwable("Unable to connect"))

        homeViewModel.onRetrievePostListFinish()

    }

}