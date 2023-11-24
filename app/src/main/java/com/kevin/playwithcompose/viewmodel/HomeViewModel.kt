package com.kevin.playwithcompose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.composestudy.bean.BannerData
import com.kevin.composestudy.http.Api
import com.kevin.composestudy.http.HttpUtils
import com.kevin.playwithcompose.bean.ArticleListData
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _bannerData = mutableStateOf<List<BannerData>>(emptyList())
    var bannerData = _bannerData
    private val _articleData: MutableState<ArticleListData?> = mutableStateOf(null)
    var articleData = _articleData
    var currentNum = 0

    init {
        requestBanner()
        requestArticleList()
    }

    private fun requestBanner() {
        viewModelScope.launch {
            val api = HttpUtils.create(Api::class.java)
            val bannerBean = api.requestBanner()
            if (bannerBean.errorCode == 0) {
                val data = bannerBean.data
                val first = data.first()
                val last = data.last()
                val temp: MutableList<BannerData> = mutableListOf()
                temp.add(last)
                temp.addAll(data)
                temp.add(first)
                _bannerData.value = temp
            }
        }
    }

    fun requestArticleList(pageNumber: Int = 0) {
        viewModelScope.launch {
            currentNum = pageNumber
            val api = HttpUtils.create(Api::class.java)
            val articleList = api.requestArticleList(pageNumber)
            if (articleList.errorCode == 0) {
                val data = articleList.data
                val temp = _articleData.value
                if (temp != null) {
                    _articleData.value = temp + data
                } else {
                    _articleData.value = data
                }
            }
        }
    }
}
