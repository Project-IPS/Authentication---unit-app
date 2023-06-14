package com.example.jwttest.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.jwttest.R
import com.example.jwttest.data.network.AuthAPI
import com.example.jwttest.data.repository.AuthRepository
import com.example.jwttest.data.responses.LoginResponse
import com.example.jwttest.data.responses.User
import com.example.jwttest.databinding.FragmentHomeBinding
import com.example.jwttest.databinding.FragmentLoginBinding
import com.example.jwttest.databinding.FragmentSignupBinding
import com.example.jwttest.ui.auth.AuthViewModel
import com.example.jwttest.ui.base.BaseFragment
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject


class HomeFragment :  BaseFragment<AuthViewModel, FragmentHomeBinding, AuthRepository>() {



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            var response : String? = ""

            viewModel.getLoginToken().collect{ value ->
                response = value
                Log.d("ResponseDebug", response.toString())
                val root: JsonObject = Gson().fromJson(response, JsonObject::class.java)
            }
        }

        binding.logoutButton.setOnClickListener{
            logout()
        }
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository((remoteDataSource.buildApi(AuthAPI::class.java)), userPreferences )
}

