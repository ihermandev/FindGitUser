package com.herman.illia.findgituser.model
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.herman.illia.findgituser.utils.Constant.TAB_USER_NAME
import java.io.Serializable

@Entity(tableName = TAB_USER_NAME)
data class UserModel(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @Expose
    val id: Int,
    @SerializedName("avatar_url")
    @Expose
    val avatarUrl: String,
    @SerializedName("location")
    @Expose
    val location: String?,
    @SerializedName("login")
    @Expose
    val login: String,
    @SerializedName("name")
    @Expose
    val name: String?,
    @SerializedName("public_gists")
    @Expose
    val publicGists: Int,
    @SerializedName("public_repos")
    @Expose
    val publicRepos: Int
) : Serializable