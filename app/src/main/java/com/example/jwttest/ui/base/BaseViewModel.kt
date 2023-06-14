package com.example.jwttest.ui.base

import androidx.lifecycle.ViewModel
import com.example.jwttest.data.network.UserApi

abstract class BaseViewModel(
    private val  repository: BaseRepository
): ViewModel() {

        suspend fun logout(api: UserApi) = repository.logout(api)
}