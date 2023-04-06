package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.smartcarparkadmin.MainActivity
import com.example.smartcarparkadmin.R
import com.example.smartcarparkadmin.data.Admin
import com.example.smartcarparkadmin.data.AuthViewModel
import com.example.smartcarparkadmin.data.RESTORE_USERS
import com.example.smartcarparkadmin.databinding.LoginBinding
import com.example.smartcarparkadmin.databinding.HeaderBinding
import com.example.smartcarparkadmin.util.errorDialog
import com.example.smartcarparkadmin.util.hideKeyboard
import com.example.smartcarparkadmin.util.snackbar
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class LoginFragment : Fragment(), IOnBackPressed {

    private lateinit var binding: LoginBinding
    private lateinit var bindings: HeaderBinding
    private val nav by lazy { findNavController() }
    private val auth: AuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {


        binding = LoginBinding.inflate(inflater, container, false)
        bindings = HeaderBinding.inflate(inflater, container, false)
        binding.btnLogin.setOnClickListener { login() }
     
        return binding.root
    }
    private fun restore() {
        val ctx = requireContext()
        RESTORE_USERS(ctx)
        snackbar("Users restored.")
    }

    private fun login() {
        hideKeyboard()

        val ctx      = requireContext()
        val email    = binding.edtEmails.text.toString().trim()
        val password = binding.edtPasswords.text.toString().trim()
        val remember = binding.chkRemember.isChecked

        // TODO(3): Login -> auth.login(...)
        //          Clear navigation backstack
        lifecycleScope.launch{
            val success = auth.login(ctx,email,password,remember)

            if (success) {

//                nav.popBackStack(R.id.homeFragment, false)
//                val profile = auth.getUser()
//                if (profile != null) {
//                    bindings.welcomes.text = profile.name
//
//                }

                nav.navigate(R.id.homeFragment)

            }
            else
            {
                errorDialog("Invalid credentials")
            }
        }


    }

    override fun onBackPressed(): Boolean {
        if(binding.edtEmails.text == null || binding.edtEmails.text.toString() ==""){
            exitProcess(0)
        }

        return true


    }

}