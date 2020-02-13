package com.example.appstreetshahid.ui.home.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.appstreetshahid.R
import com.example.appstreetshahid.databinding.ItemGithubBinding
import com.example.appstreetshahid.ui.detail.DetailActivity
import com.example.appstreetshahid.ui.home.model.GithubTrending
import com.example.appstreetshahid.ui.home.viewmodel.GitHubViewModel
import com.example.appstreetshahid.utils.imagemanger.ImageLoadingCallBack
import androidx.core.util.Pair as UtilPair


class GitHubAdapter(val activity: Activity,
                    val onClickItem: MutableLiveData<GithubTrending>) : RecyclerView.Adapter<GitHubAdapter.ViewHolder>() {
    private lateinit var githubTrendingList: List<GithubTrending>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemGithubBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_github, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(githubTrendingList[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        return if (::githubTrendingList.isInitialized) githubTrendingList.size else 0
    }

    fun updatePostList(githubTrendingList: List<GithubTrending>) {
        this.githubTrendingList = githubTrendingList
        notifyDataSetChanged()
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.unBind(githubTrendingList[holder.adapterPosition])
    }

    inner class ViewHolder(private val binding: ItemGithubBinding) : RecyclerView.ViewHolder(binding.root) {
        val viewModel = GitHubViewModel(activity)

        fun bind(githubTrending: GithubTrending) {
            binding.userImage.setImageBitmap(null)
            binding.userImage.setImageResource(R.drawable.ic_user_placeholder)
            viewModel.bind(githubTrending)
            binding.viewModel = viewModel
            viewModel.loadImage(githubTrending.avatar, binding.userImage, object : ImageLoadingCallBack {
                override fun onSuccess(bitmap: Bitmap) {}

                override fun onFail() {}

            })
            binding.cardView.setOnClickListener {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(GithubTrending.TAG, githubTrending)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                                UtilPair.create(binding.cvUserImage as View, ViewCompat.getTransitionName(binding.cvUserImage)),
                                UtilPair.create(binding.name as View, ViewCompat.getTransitionName(binding.name)),
                                UtilPair.create(binding.userName as View, ViewCompat.getTransitionName(binding.userName)))
                    ActivityCompat.startActivity(activity, intent, activityOptionsCompat.toBundle())

                } else {
                    ActivityCompat.startActivity(activity, intent, null)
                }
                onClickItem.value = githubTrending
            }
            /*if (!TextUtils.isEmpty(githubTrending.avatar)) {
                val bitmap = imageCache.getImage(githubTrending.avatar ?: "")
                if (bitmap == null) {
                    if (!downLoadList.containsKey(position)) {
                        val downloadImageTask = DownloadImageTask(githubTrending.avatar ?: "",
                                binding.userImage, imageCache, object : ImageLoadingCallBack {
                            override fun onSuccess(bitmap: Bitmap) {
                                viewModel.getImageBitmap().value = bitmap
                            }

                            override fun onFail() {

                            }

                        })
                        downLoadList[position] = downloadImageTask
                        downloadImageTask.execute()
                    } else {
                        downLoadList[position]?.setImage = true
                    }
                } else {
                    if (downLoadList[position]?.isLoading != true) {
                        binding.userImage.setImageBitmap(bitmap)
                    }
                }
            }*/
        }

        fun unBind(githubTrending: GithubTrending) {
            viewModel.unBind(githubTrending.avatar)
        }

    }

    class GitHubAdapterItemDecoration(val marginLeft: Int, val marginTop: Int,
                                      val marginRight: Int, val marginBottom: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.left = marginLeft
            outRect.right = marginRight
            outRect.top = marginTop
            outRect.bottom = marginBottom
            /*when {
                parent.getChildAdapterPosition(view) == 0 -> {
                    outRect.bottom = marginBottom
                }
                parent.getChildAdapterPosition(view) == (parent.adapter?.itemCount
                        ?: 0) - 1 -> {
                    outRect.top = marginTop
                }
                else -> {
                    outRect.top = marginTop
                    outRect.bottom = marginBottom
                }
            }*/
        }

    }

}