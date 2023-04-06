package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.smartcarparkadmin.R
import com.example.smartcarparkadmin.data.ADMIN
import com.example.smartcarparkadmin.data.Admin
import com.example.smartcarparkadmin.data.AuthViewModel
import com.example.smartcarparkadmin.databinding.HeaderBinding
import com.example.smartcarparkadmin.databinding.ProfileBinding
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileBinding
    private var tada1 = ""
    private val nav by lazy { findNavController() }
    private val auths: AuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ProfileBinding.inflate(inflater, container, false)


       val profile = auths.getUser()
        if (profile != null) {
            binding.name.text = profile.name
            binding.email.text = profile.password
            binding.studentId.text = profile.adminEmail
            binding.studentId2.text = profile.phoneNo
            tada1 = profile.role



            if(tada1.toString() == ""||tada1.toString()=="Guard"){
                binding.register.visibility = View.GONE
            }
            else if(tada1.toString() == "Admin"){
                binding.register.visibility = View.VISIBLE
            }

        } else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }


        val emails = binding.email.text.toString()





        binding.logout.setOnClickListener{logoutAdmin()}
        binding.register.setOnClickListener{registerpage()}
        binding.resetPassword.setOnClickListener{editpage()}
        return binding.root
    }
    private fun logoutAdmin(){
        auths.logout(requireContext())
        nav.navigate(R.id.loginFragment)
    }

    private fun registerpage(){

        nav.navigate(R.id.profileAdd)
    }
    private fun editpage(){

        nav.navigate(R.id.profileEdit)
    }


}