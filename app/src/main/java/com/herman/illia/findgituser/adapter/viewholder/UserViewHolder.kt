package com.herman.illia.findgituser.adapter.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.herman.illia.findgituser.R
import com.herman.illia.findgituser.adapter.listener.UserAddListener
import com.herman.illia.findgituser.model.UserModel
import com.squareup.picasso.Picasso

/**
 * Created by illia on 8/27/2019.
 */
class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val userName = itemView.findViewById<AppCompatTextView>(R.id.tv_name)
    private val userRepos = itemView.findViewById<AppCompatTextView>(R.id.tv_repos)
    private val userGists = itemView.findViewById<AppCompatTextView>(R.id.tv_gists)
    private val userAddImg = itemView.findViewById<AppCompatImageView>(R.id.iv_user_add)
    private val userAvatar = itemView.findViewById<AppCompatImageView>(R.id.iv_user_avatar)

    fun setLogin(name: String) {
        userName.text = name
    }

    fun setRepos(reposCount: Int) {
        userRepos.text = userRepos.context.resources.getString(R.string.repos_label, reposCount)
    }

    fun setGists(gistsCount: Int) {
        userGists.text = userGists.context.resources.getString(R.string.gists_label, gistsCount)
    }

    fun setAvatar(url: String) {
        Picasso.get().load(url)
            .placeholder(R.drawable.avatar_user).into(userAvatar)
    }

    fun setAddFeatureVisibility(visibility: Int) {
        userAddImg.visibility = visibility
    }

    fun bindAddUserFeature(user: UserModel, listener: UserAddListener) {
        userAddImg.setOnClickListener {
            listener.userAdded(user)
        }
    }
}