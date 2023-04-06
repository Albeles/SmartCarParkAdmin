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
import com.example.smartcarparkadmin.data.AuthViewModel
import com.example.smartcarparkadmin.databinding.ProfileaddingBinding
import com.example.smartcarparkadmin.util.errorDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class ProfileAdd : Fragment() {

    private lateinit var binding: ProfileaddingBinding
    private val nav by lazy { findNavController() }
    private val auths: AuthViewModel by activityViewModels()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ProfileaddingBinding.inflate(inflater, container, false)
//        val profile = auths.getUser()
//        if (profile != null) {
//            binding.names.setText(profile.name)
//            binding.passwords.setText(profile.password)
//            binding.adminEmails.setText(profile.adminEmail)
//            binding.contactnums.setText(profile.phoneNo)
//
//        } else {
//            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
//        }
        binding.compoundsubmit.setOnClickListener{Adds()}
        binding.ComBack.setOnClickListener{backs()}
        return binding.root
    }
    private fun Adds(){

        lifecycleScope.launch {

            if (binding.adminEmails.text.toString() == "") {
                errorDialog("Please insert email")
            } else if(binding.names.text.toString()=="")
            {
                errorDialog("Please insert name")
            }else if(binding.contactnums.text.toString()=="")
            {
                errorDialog("Please insert contact number")
            }else if(binding.passwords.text.toString()=="")
            {
                errorDialog("Please insert password")
            }
            else if(binding.reconfirmpasswordsss.text.toString()=="")
            {
                errorDialog("Please insert re-enter password")
            }else if(binding.reconfirmpasswordsss.text.toString()!=binding.passwords.text.toString())
            {
                errorDialog("Both password is not the same please check")
            }
            else{
                        val success =
                            auths.checkemail(requireContext(), binding.adminEmails.text.toString())

                        if (success) {
                            errorDialog("This email is available")

                        } else {

                            auths.addProfile(
                                binding.adminEmails.text.toString(),
                                binding.names.text.toString(),
                                binding.passwords.text.toString(),
                                binding.contactnums.text.toString()
                            )


                            nav.navigate(R.id.profileFragment)
                        }
            }
        }
    }
    private fun backs(){
        nav.navigateUp()
    }
}