package com.kevin.playwithcompose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.composestudy.bean.BannerData
import com.kevin.composestudy.http.Api
import com.kevin.composestudy.http.HttpUtils
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _bannerData = mutableStateOf<List<BannerData>>(emptyList())
    var bannerData = _bannerData

    init {
        requestBanner()
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
}