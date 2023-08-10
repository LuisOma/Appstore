package com.example.appstore.data.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class AppsModel(
    var appsList: List<AppsDetailModel>,
)

@Parcelize
data class AppsDetailModel(
    var id: String,
    var nombre: String,
    var category: String,
    var dev: String,
    var description : String,
    var rating: Double,
    var price: Double,
    var isInstalled: Boolean,
    var image: String,
    var screenShotsList: List<ScreenshotDetailModel>,
    var comments: List<CommentsDetailModel>
): Parcelable

@Parcelize
data class ScreenshotDetailModel(
    var idImage: String,
    var url: String
): Parcelable

@Parcelize
data class CommentsDetailModel(
    var idComment: String,
    var comment: String,
    var imageUser: String
): Parcelable


