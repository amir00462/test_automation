package ir.dunijet.teamgit.di

import ir.dunijet.teamgit.data.net.createApiService
import ir.dunijet.teamgit.data.repository.BlogRepository
import ir.dunijet.teamgit.data.repository.RetrofitBlogRepository
import ir.dunijet.teamgit.ui.features.blogScreen.BlogViewModel
import ir.dunijet.teamgit.ui.features.homeScreen.HomeViewModel
import ir.dunijet.teamgit.ui.features.searchScreen.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {
    single { createApiService() }
    single<BlogRepository> { RetrofitBlogRepository(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { BlogViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}