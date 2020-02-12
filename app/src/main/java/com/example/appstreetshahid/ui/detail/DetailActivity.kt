package com.example.appstreetshahid.ui.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.appstreetshahid.R
import com.example.appstreetshahid.databinding.ActivityDetailBinding
import com.example.appstreetshahid.di.ViewModelFactory
import com.example.appstreetshahid.ui.home.model.GithubTrending
import com.example.appstreetshahid.ui.home.viewmodel.GitHubViewModel
import com.example.appstreetshahid.utils.imagemanger.ImageLoadingCallBack

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: GitHubViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

        val githubTrending = intent.getParcelableExtra<GithubTrending>(GithubTrending.TAG)

        viewModel = ViewModelProvider(this@DetailActivity, ViewModelFactory(this@DetailActivity)).get(GitHubViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.bind(githubTrending)
        viewModel.loadImage(githubTrending.avatar, binding.childContentDetail.userImage, object : ImageLoadingCallBack {
            override fun onSuccess(bitmap: Bitmap) {}
            override fun onFail() {}
        })
        binding.childContentDetail.userUrl.paintFlags = binding.childContentDetail.userUrl.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.childContentDetail.repoUrl.paintFlags = binding.childContentDetail.userUrl.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        viewModel.getDescription().observe(this, Observer {
            binding.childContentDetail.description.visibility = if (TextUtils.isEmpty(it)) {
                View.GONE
            } else {
                View.VISIBLE
            }
        })
        binding.childContentDetail.userUrl.setOnClickListener {
            openUrl(viewModel.getUrl().value)
        }
        binding.childContentDetail.repoUrl.setOnClickListener {
            openUrl(viewModel.getRepoUrl().value)
        }
    }

    private fun openUrl(url: String?) {
        if (!TextUtils.isEmpty(url)) {
            try {
                val uri = Uri.parse(url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }
    }

}
