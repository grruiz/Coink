package com.fpr.appcoink.ui.profile

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fpr.appcoink.R
import com.fpr.appcoink.core.Result
import com.fpr.appcoink.data.remote.auth.AuthDataSource
import com.fpr.appcoink.databinding.FragmentSetupProfileBinding
import com.fpr.appcoink.domain.auth.AuthRepoImp
import com.fpr.appcoink.presentation.auth.AuthViewModel
import com.fpr.appcoink.presentation.auth.AuthViewModelFactory

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    lateinit var binding: FragmentSetupProfileBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepoImp(AuthDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Peta en esta linea Idk
//        binding = FragmentSetupProfileBinding.bind(view)
        viewModel.getUser().observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Loading -> {
                    Log.d("user", "Cargando usuario: ${result}")
                }
                is Result.Success -> {
                    Log.d("user", "Cargando usuario: ${result.data?.photoUrl}") //TODO Poner los datos en editext que no se modifique
                }
                is Result.Failure -> {
                    Log.d("user", "Cargando usuario: ${result}")
                }
            }
        })
    }
}