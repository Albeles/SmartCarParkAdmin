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
import com.example.smartcarparkadmin.databinding.ProfileeditingBinding
import com.example.smartcarparkadmin.util.errorDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class ProfileEdit : Fragment() {

    private lateinit var binding: ProfileeditingBinding
    private val nav by lazy { findNavController() }
    private val auths: AuthViewModel by activityViewModels()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var password = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ProfileeditingBinding.inflate(inflater, container, false)
        val profile = auths.getUser()
        if (profile != null) {
            binding.names.setText(profile.name)
            binding.passwords.setText(profile.password)
            binding.adminEmails.setText(profile.adminEmail)
            binding.contactnums.setText(profile.phoneNo)

            password = profile.password

        } else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }
        binding.compoundsubmit.setOnClickListener{Edits()}
        binding.ComBack.setOnClickListener{backs()}
        return binding.root
    }
    private fun Edits(){
        lifecycleScope.launch {


            if (binding.adminEmails.text.toString() == "") {
                errorDialog("Please insert email")
            } else if(binding.names.text.toString()=="")
            {
                errorDialog("Please insert name")
            }else if(binding.contactnums.text.toString()=="")
            {
                errorDialog("Please insert contact number")
            }else if(binding.passwords.text.toString()==password)
            {
                auths.updateUser(
                    binding.adminEmails.text.toString(),
                    binding.names.text.toString(),
                    binding.passwords.text.toString(),
                    binding.contactnums.text.toString()

                )
                nav.navigate(R.id.profileFragment)
            }else if(binding.passwords.text.toString()!=password)
            {
                if(binding.reconfirmpasswordsss.text.toString()=="")
                {
                    errorDialog("Please insert password")
                }else if(binding.reconfirmpasswordsss.text.toString()=="")
                {
                    errorDialog("Please insert re-enter password")
                }else if(binding.reconfirmpasswordsss.text.toString()!=binding.passwords.text.toString())
                {
                    errorDialog("Both password is not the same please check")
                }else{
                    auths.updateUser(
                        binding.adminEmails.text.toString(),
                        binding.names.text.toString(),
                        binding.passwords.text.toString(),
                        binding.contactnums.text.toString()
                    )


                    nav.navigate(R.id.profileFragment)
                }


            }



//            db.collection("admin").document(adminid).update(
//                "name",
//                binding.names.toString(),
//                "password",
//                binding.passwords.toString(),
//                "adminEmail",
//                binding.adminEmails.toString(),
//                "phoneNo",
//                binding.contactnums.toString()
//            )
        }

    }
    private fun backs(){
        nav.navigateUp()
    }

}