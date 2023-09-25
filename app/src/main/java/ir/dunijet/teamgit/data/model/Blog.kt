package ir.dunijet.teamgit.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class Blog(
    val _id: String,
    val title: String,
    val image: String,
    val content: String,
    val author: String,
    val category: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)

data class BlogResponse(
    val blogs: List<Blog>
)