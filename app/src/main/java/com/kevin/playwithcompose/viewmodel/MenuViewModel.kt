package com.kevin.playwithcompose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.composestudy.http.Api
import com.kevin.composestudy.http.HttpUtils
import com.kevin.playwithcompose.bean.NavListArticle
import com.kevin.playwithcompose.bean.NavListData
import kotlinx.coroutines.launch

class MenuViewModel : ViewModel() {
    private val _navTitleListData = mutableStateOf<List<NavListData>>(emptyList())
    var navTitleListData = _navTitleListData
    private val _navContentListData = mutableStateOf<List<NavListArticle>>(emptyList())

    init {
        requestNav()
    }

    private fun requestNav() {
        viewModelScope.launch {
            val api = HttpUtils.create(Api::class.java)
            val requestNav = api.requestNav()
            if(requestNav.errorCode==0){
                val data = requestNav.data
                _navTitleListData.value = data
//                data.forEach { it->{
//                    val children = it.children
//                } }
            }
        }
    }
}