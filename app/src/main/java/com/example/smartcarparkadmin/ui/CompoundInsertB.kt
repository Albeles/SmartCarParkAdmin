package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.smartcarparkadmin.data.CompoundViewModel
import com.example.smartcarparkadmin.databinding.CompoundInsertBinding
import androidx.navigation.ui.setupWithNavController
import com.example.smartcarparkadmin.R
import com.example.smartcarparkadmin.data.AuthViewModel
import com.example.smartcarparkadmin.data.Compounds
import com.example.smartcarparkadmin.data.Users
import com.example.smartcarparkadmin.databinding.CompoundInsert2Binding
import com.example.smartcarparkadmin.util.errorDialog
import com.example.smartcarparkadmin.util.hideKeyboard
import kotlinx.coroutines.launch

class CompoundInsertB : Fragment(){
    private lateinit var binding: CompoundInsert2Binding
    private val nav by lazy { findNavController() }
    private val auths: CompoundViewModel by activityViewModels()
    private val authss: AuthViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = CompoundInsert2Binding.inflate(inflater, container, false)

        binding.compoundsubmit.setOnClickListener{compoundInsert()}


        return binding.root
    }
    private fun compoundInsert() {
        hideKeyboard()

        val ctx      = requireContext()
        val carplate    = binding.carplate.text.toString().trim()


        lifecycleScope.launch{


            val success = auths.checkUser(ctx,carplate)
            if (success) {
                auths.addCC(ctx,carplate)
                val admin = authss.getUser()
                var adminName = ""
                if (admin != null){
                    adminName = admin.name

                    auths.addCompound(carplate,"Block V",adminName)
                nav.navigateUp()
            }else
            {
                errorDialog("Invalid car plate")
            }


            {
                errorDialog("Invalid car plate")
            }
        }


    }
}
}


