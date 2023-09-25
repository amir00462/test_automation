package ir.dunijet.teamgit.ui.features.searchScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.dunijet.teamgit.data.model.Blog
import ir.dunijet.teamgit.data.model.Category
import ir.dunijet.teamgit.data.model.Filtering
import ir.dunijet.teamgit.data.repository.BlogRepository
import ir.dunijet.teamgit.util.NO_FILTER
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val blogRepository: BlogRepository) : ViewModel() {
    private val _blogs = MutableStateFlow<List<Blog>>(emptyList())
    private val _categoryList = MutableStateFlow<List<Category>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    private val _authors = MutableStateFlow(mutableListOf<String>())
    private val _filtering = MutableStateFlow(NO_FILTER)

    val blogs: StateFlow<List<Blog>> = _blogs
    val categoryList: StateFlow<List<Category>> = _categoryList
    val searchQuery: StateFlow<String> = _searchQuery
    val filtering: StateFlow<Filtering> = _filtering
    val authors: StateFlow<List<String>> = _authors

    init {
        fetchBlogs()
        fetchCategoryList()
    }

    fun fetchBlogs() {

        viewModelScope.launch {

            try {
                val fullBlogs = blogRepository.getBlogs()
                var categoryFilterBlogs = emptyList<Blog>()
                var authorFilterBlog = emptyList<Blog>()
                var searchedQueryBlog = emptyList<Blog>()

                categoryFilterBlogs = if (_filtering.value.categories.isNotEmpty())
                    fullBlogs.filter { _filtering.value.categories.contains(it.category) }
                else
                    fullBlogs

                authorFilterBlog = if (_filtering.value.authors.isNotEmpty())
                    categoryFilterBlogs.filter { _filtering.value.authors.contains(it.author) }
                else
                    categoryFilterBlogs

                // authorFilterBlog is filtered and is ready ->
                searchedQueryBlog = if (_searchQuery.value.isNotEmpty())
                    authorFilterBlog.filter {
                        it.content.contains(_searchQuery.value) ||
                                it.title.contains(_searchQuery.value)
                    }
                else
                    authorFilterBlog

                _blogs.emit(searchedQueryBlog)
                fetchAuthors()
            } catch (ex: Exception) {

                Log.v("TeamGitLog", ex.message ?: "exception in fetchBlogs() - searchViewModel")

            }
        }
    }
    fun fetchCategoryList() {
        viewModelScope.launch {
            try {
                val categoryList = blogRepository.getCategoryList()
                _categoryList.emit(categoryList)
            } catch (ex: Exception) {
                Log.v(
                    "TeamGitLog",
                    ex.message ?: "exception in fetchCategoryList() - searchViewModel"
                )
            }
        }
    }
    fun fetchAuthors() {
        if (_blogs.value.isNotEmpty()) {
            _blogs.value.forEach {
                if (!_authors.value.contains(it.author)) {
                    _authors.value.add(it.author)
                }
            }
        }
    }
    fun setSearchQuery(str: String) {
        _searchQuery.value = str
        fetchBlogs()
    }
    fun changeFiltering(filtering: Filtering) {
        _filtering.value = filtering
        fetchBlogs()
    }

}