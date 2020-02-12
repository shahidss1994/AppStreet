package com.example.appstreetshahid.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appstreetshahid.R
import com.example.appstreetshahid.databinding.MainActivityBinding
import com.example.appstreetshahid.di.ViewModelFactory
import com.example.appstreetshahid.ui.home.adapter.GitHubAdapter
import com.example.appstreetshahid.ui.home.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * Created by shahid on 12/8/16.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: MainActivityBinding
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.postList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dp16 = resources.getDimensionPixelSize(R.dimen.dp_16)
        val dp5 = resources.getDimensionPixelSize(R.dimen.dp_5)
        binding.postList.addItemDecoration(GitHubAdapter.GitHubAdapterItemDecoration(dp16, dp5, dp16, dp5))

        viewModel = ViewModelProvider(this@MainActivity, ViewModelFactory(this@MainActivity)).get(HomeViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        binding.viewModel = viewModel

    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }
}
