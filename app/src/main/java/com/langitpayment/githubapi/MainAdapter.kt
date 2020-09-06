package com.langitpayment.githubapi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.langitpayment.githubapi.databinding.RepositoriesListItemBinding
import com.langitpayment.githubapi.entity.TrendingRepositories

class MainAdapter(private val trendingRepositories: ArrayList<TrendingRepositories>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RepositoriesListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = trendingRepositories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(trendingRepositories[position])
    }

    class ViewHolder(val binding: RepositoriesListItemBinding) :
        RecyclerView.ViewHolder(binding.rootLayout) {

        fun bind(trendingRepositories: TrendingRepositories) {
            with(binding) {
                usernameText.text = trendingRepositories.author
                repositoryNameText.text = trendingRepositories.name
                com.bumptech.glide.Glide.with(avatarImage)
                    .load(trendingRepositories.avatar)
                    .placeholder(R.drawable.ic_baseline_account_circle_24)
                    .into(avatarImage)
            }
        }
    }
}