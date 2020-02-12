package com.example.appstreetshahid.di

import com.example.appstreetshahid.ui.home.model.GithubTrending
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by shahid on 12/8/16.
 */
interface IDataService {

    @GET("/developers?language=java&since=weekly")
    fun getData(): Observable<List<GithubTrending>>

}