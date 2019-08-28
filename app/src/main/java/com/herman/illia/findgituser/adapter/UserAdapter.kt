package com.herman.illia.findgituser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.herman.illia.findgituser.R
import com.herman.illia.findgituser.adapter.listener.UserAddListener
import com.herman.illia.findgituser.adapter.viewholder.UserViewHolder
import com.herman.illia.findgituser.model.UserModel

/**
 * Created by illia on 8/27/2019.
 */
class UserAdapter internal constructor(
    private var dataList: List<UserModel>,
    private val listener: UserAddListener? = null,
    private var isSearchMode: Boolean = true
) : RecyclerView.Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.user_item, parent, false)
        )
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.setLogin(currentItem.login)
        holder.setAvatar(currentItem.avatarUrl)
        holder.setRepos(currentItem.publicRepos)
        holder.setGists(currentItem.publicGists)
        when {
            isSearchMode -> listener?.let { holder.bindAddUserFeature(currentItem, it) }
            else -> {
                holder.setAddFeatureVisibility(View.GONE)
            }
        }

    }

    fun addUsers(userList: List<UserModel>) {
        this.dataList = userList
        notifyDataSetChanged()
    }

    fun removeUser(position: Int) {
        notifyItemRemoved(position)
    }

    fun getData(): List<UserModel> {
        return dataList
    }

}