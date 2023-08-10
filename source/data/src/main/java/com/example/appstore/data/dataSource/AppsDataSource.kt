package com.example.appstore.data.dataSource

import com.example.appstore.data.domain.*
import com.example.appstore.data.domain.core.ErrorState
import com.example.appstore.data.domain.core.ResponseState
import com.example.appstore.data.extensions.getResponseError
import com.example.appstore.data.extensions.validateRetryCount
import com.example.appstore.data.extensions.validateServerResponseCode
import com.example.appstore.data.network.retrofit.RetrofitInstance
import com.example.appstore.data.network.retrofit.implement.AppsNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppsDataSource {

    var network = RetrofitInstance.getInstance().create(AppsNetwork::class.java)

    @Throws(SecurityException::class)
    suspend fun getApps(): Flow<ResponseState<Any?>> {
        return flow {
            var intents = 1
            try {
                emit(ResponseState.Loading)
                val networkResponse = network.getApps()
                if (networkResponse.code().validateServerResponseCode()) {
                    networkResponse.body()?.let {
                        emit(ResponseState.Success(
                            it.response.appsList.map { it ->
                                AppsDetailModel(
                                    id = it.id,
                                    nombre = it.nombre,
                                    category = it.category,
                                    dev = it.dev,
                                    description = it.description,
                                    rating = it.rating,
                                    price = it.price,
                                    isInstalled = it.isInstalled,
                                    image = it.image,
                                    screenShotsList = it.screenShotsList.map {
                                          ScreenshotDetailModel(
                                              idImage = it.idImage,
                                              url = it.url
                                          )
                                    },
                                    comments = it.comments.map {
                                        CommentsDetailModel(
                                            idComment = it.idComment,
                                            comment = it.comment,
                                            imageUser = it.imageUser
                                        )
                                    }
                                )
                            }
                        )
                        )
                    }
                } else {
                    emit(networkResponse.getResponseError())
                }
            } catch (securityException: SecurityException) {
                throw securityException
            } catch (exception: Exception) {
                while (intents.validateRetryCount()) {
                    intents += 1
                    emit(ResponseState.Retry)
                }
                emit(ResponseState.Error(ErrorState.Network))
            }
        }
    }
}