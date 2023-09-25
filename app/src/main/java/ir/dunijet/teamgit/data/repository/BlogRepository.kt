package ir.dunijet.teamgit.data.repository

import ir.dunijet.teamgit.data.model.Blog
import ir.dunijet.teamgit.data.model.Category

interface BlogRepository {
    suspend fun getBlogs(): List<Blog>
    suspend fun getCategoryList() :List<Category>
}