package com.fpr.appcoink.ui.profile


import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fpr.appcoink.R
import com.fpr.appcoink.core.Result
import com.fpr.appcoink.core.hide
import com.fpr.appcoink.core.show
import com.fpr.appcoink.data.remote.auth.AuthDataSource
import com.fpr.appcoink.databinding.FragmentProfileBinding
import com.fpr.appcoink.databinding.FragmentSetupProfileBinding
import com.fpr.appcoink.domain.auth.AuthRepoImp
import com.fpr.appcoink.presentation.auth.AuthViewModel
import com.fpr.appcoink.presentation.auth.AuthViewModelFactory

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepoImp(AuthDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        viewModel.getUser().observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Loading -> {
                    binding.progressBar.show()
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    Glide.with(requireContext()).load(result.data?.photoUrl).centerCrop().into(binding.profilePicture)
                    binding.txtUser.text = result.data?.displayName
                    binding.txtEmail.text = result.data?.email
                }
                is Result.Failure -> {
                    Log.d("user", "Cargando usuario: ${result}")
                }
            }
        })
    }
}