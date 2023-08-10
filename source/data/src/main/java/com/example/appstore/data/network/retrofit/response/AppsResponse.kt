package com.example.appstore.data.network.retrofit.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseData (
    val code: Long,
    val message: String,
    val response: AppsResponse
): Parcelable

@Parcelize
data class AppsResponse(
    var appsList: List<AppsDetailResponse>
): Parcelable

@Parcelize
data class AppsDetailResponse(
    var id: String,
    var nombre: String,
    var category: String,
    var dev: String,
    var description : String,
    var rating: Double,
    var price: Double,
    var isInstalled: Boolean,
    var image: String,
    var screenShotsList: List<ScreenshotDetail>,
    var comments: List<CommentsDetail>
    ): Parcelable

@Parcelize
data class ScreenshotDetail(
    var idImage: String,
    var url: String
): Parcelable

@Parcelize
data class CommentsDetail(
    var idComment: String,
    var comment: String,
    var imageUser: String
): Parcelable
