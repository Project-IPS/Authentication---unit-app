package com.example.jwttest.ui.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.jwttest.databinding.FragmentLoginBinding
import com.example.jwttest.data.network.AuthAPI
import com.example.jwttest.data.network.Resource
import com.example.jwttest.data.repository.AuthRepository
import com.example.jwttest.home.HomeActivity
import com.example.jwttest.ui.base.BaseFragment
import com.example.jwttest.ui.enable
import com.example.jwttest.ui.handleApiError
import com.example.jwttest.ui.startNewActivity
import com.example.jwttest.ui.visible
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    companion object {
        fun newInstance() = LoginFragment()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressBar.visible(false)
        binding.loginButton.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success -> {
                    //using lifecycleScope the NewActivity will be called
                    // only after save function finishes.
                    // lifecycleScope provides sequential function call inside it

                    lifecycleScope.launch {
                        viewModel.saveLoginAuthToken(it.value.toString())
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it){login()}
                else -> {}
            }
        })

        binding.signUp.setOnClickListener{
            val action = LoginFragmentDirections.actionLoginFragmentToSignupFragment()
            findNavController().navigate(action)
        }

        binding.emailEditText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        binding.passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD

        binding.loginButton.setOnClickListener{
                login()
        }

        binding.passwordEditText.addTextChangedListener{
            val email = binding.emailEditText.text.toString().trim()
            binding.loginButton.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }
    }

    private fun login(){
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        viewModel.login(email, password)
    }
    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository((remoteDataSource.buildApi(AuthAPI::class.java)), userPreferences )


}