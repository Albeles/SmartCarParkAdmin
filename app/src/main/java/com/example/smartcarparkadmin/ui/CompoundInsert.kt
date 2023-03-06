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
import com.example.smartcarparkadmin.data.User
import com.example.smartcarparkadmin.util.errorDialog
import com.example.smartcarparkadmin.util.hideKeyboard
import kotlinx.coroutines.launch

class CompoundInsert : Fragment(){
    private lateinit var binding: CompoundInsertBinding
    private val nav by lazy { findNavController() }
    private val auths: CompoundViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = CompoundInsertBinding.inflate(inflater, container, false)

        binding.compoundsubmit.setOnClickListener{compoundInsert()}


        return binding.root
}
    private fun compoundInsert() {
        hideKeyboard()

        val ctx      = requireContext()
        val carplate    = binding.carplate.text.toString().trim()


        // TODO(3): Login -> auth.login(...)
        //          Clear navigation backstack
        lifecycleScope.launch{


                val success = auths.checkUser(ctx,carplate)
                if (success) {
                    auths.addCC(ctx,carplate)
                    auths.addCompound(carplate)
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


