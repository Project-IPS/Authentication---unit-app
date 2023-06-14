package com.example.jwttest.ui.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.example.jwttest.data.network.AuthAPI
import com.example.jwttest.data.network.Resource
import com.example.jwttest.data.repository.AuthRepository
import com.example.jwttest.databinding.FragmentLoginBinding
import com.example.jwttest.databinding.FragmentSignupBinding
import com.example.jwttest.home.HomeActivity
import com.example.jwttest.ui.base.BaseFragment
import com.example.jwttest.ui.enable
import com.example.jwttest.ui.handleApiError
import com.example.jwttest.ui.startNewActivity
import com.example.jwttest.ui.visible
import kotlinx.coroutines.launch

class SignupFragment : BaseFragment<AuthViewModel, FragmentSignupBinding, AuthRepository>() {

    companion object {
        fun newInstance() = SignupFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressBar.visible(false)
        binding.signUpButton.enable(false)

        viewModel.signUpResponse.observe(viewLifecycleOwner) {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveRegisterAuthToken(it.value.toString())

                        val action = SignupFragmentDirections.actionSignupFragmentToLoginFragment()
                        findNavController().navigate(action)
                    }

                }
                is Resource.Failure -> handleApiError(it){signUp()}
                else -> {}
            }
        }

        binding.signIn.setOnClickListener{
            val action = SignupFragmentDirections.actionSignupFragmentToLoginFragment()
            findNavController().navigate(action)
        }

        binding.emailEditText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        binding.passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD

        binding.signUpButton.setOnClickListener{
            signUp()
        }

        binding.passwordEditText.addTextChangedListener{
            val user = binding.usernameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            binding.signUpButton.enable(user.isNotEmpty() && email.isNotEmpty() && it.toString().isNotEmpty())
        }

    }

    private fun signUp(){
        val user = binding.usernameEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        binding.progressBar.visible(true)
        viewModel.signUp(user, email, password)
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSignupBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository((remoteDataSource.buildApi(AuthAPI::class.java)), userPreferences )

}