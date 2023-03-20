package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.smartcarparkadmin.R
import com.example.smartcarparkadmin.data.AuthViewModel
import com.example.smartcarparkadmin.data.CompoundViewModel
import com.example.smartcarparkadmin.databinding.LoginBinding
import com.example.smartcarparkadmin.databinding.ProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileBinding
    private val nav by lazy { findNavController() }
    private val auths: AuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ProfileBinding.inflate(inflater, container, false)
       val profile = auths.getUser()
        if (profile != null) {
            binding.name.text = profile.name
            binding.email.text = profile.password
            binding.studentId2.text = profile.adminEmail
            binding.studentId.text = profile.phoneNo

        } else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }
        binding.logout.setOnClickListener{logoutAdmin()}
        binding.register.setOnClickListener{logoutAdmin()}
        binding.resetPassword.setOnClickListener{logoutAdmin()}
        return binding.root
    }
    private fun logoutAdmin(){
        auths.logout(requireContext())
        nav.navigate(R.id.loginFragment)
    }



}