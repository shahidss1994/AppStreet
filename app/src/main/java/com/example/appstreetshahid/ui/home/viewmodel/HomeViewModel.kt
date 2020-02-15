package com.example.appstreetshahid.ui.home.viewmodel

import android.app.Activity
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.appstreetshahid.R
import com.example.appstreetshahid.di.BaseViewModel
import com.example.appstreetshahid.di.IDataService
import com.example.appstreetshahid.ui.home.adapter.GitHubAdapter
import com.example.appstreetshahid.ui.home.model.GithubTrending
import com.example.appstreetshahid.utils.imagemanger.ImageCache
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by shahid on 12/8/16.
 */

class HomeViewModel(activity: Activity) : BaseViewModel(activity) {

    @Inject
    lateinit var iDataService: IDataService

    val onClick: MutableLiveData<GithubTrending> = MutableLiveData()
    val postListAdapter: GitHubAdapter = GitHubAdapter(activity, onClick)
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { getPosts() }

    private lateinit var subscription: Disposable

    init {
        getPosts()
    }

    fun getPosts() {
        subscription = iDataService.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrievePostListStart() }
                .doOnTerminate { onRetrievePostListFinish() }
                .subscribe(
                        { result -> onRetrievePostListSuccess(result) },
                        { error -> onRetrievePostListError(error) }
                )
    }

    fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
    }

    fun onRetrievePostListSuccess(list: List<GithubTrending>) {
        postListAdapter.updatePostList(list)
    }

    fun onRetrievePostListError(error: Throwable) {
        errorMessage.value = R.string.loading_error
    }

}