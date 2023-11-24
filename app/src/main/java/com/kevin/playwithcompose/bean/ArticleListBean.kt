package com.kevin.playwithcompose.bean

import com.google.gson.annotations.SerializedName

data class ArticleListBean(
    @SerializedName("data")
    val `data`: ArticleListData,
    val errorCode: Int,
    val errorMsg: String
)

data class ArticleListData(
    val curPage: Int,
    @SerializedName("datas")
    val datas: List<ArticleData>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
) {
    operator fun plus(data: ArticleListData): ArticleListData? {
        val temp: MutableList<ArticleData> = mutableListOf()
        temp.addAll(this.datas)
        temp.addAll(data.datas)
        return ArticleListData(
            curPage = data.curPage,
            datas = temp,
            offset = data.offset,
            over = data.over,
            pageCount = data.pageCount,
            size = data.size,
            total = data.total
        )
    }
}

data class ArticleData(
    val adminAdd: Boolean,
    val apkLink: String,
    val audit: Int,
    val author: String,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val fresh: Boolean,
    val host: String,
    val id: Int,
    val isAdminAdd: Boolean,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<Tag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)

data class Tag(
    val name: String,
    val url: String
)