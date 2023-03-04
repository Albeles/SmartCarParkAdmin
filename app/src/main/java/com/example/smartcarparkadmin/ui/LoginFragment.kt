package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.smartcarparkadmin.R
import com.example.smartcarparkadmin.data.AuthViewModel
import com.example.smartcarparkadmin.data.RESTORE_USERS
import com.example.smartcarparkadmin.databinding.LoginBinding
import com.example.smartcarparkadmin.util.errorDialog
import com.example.smartcarparkadmin.util.hideKeyboard
import com.example.smartcarparkadmin.util.snackbar
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var binding: LoginBinding
    private val nav by lazy { findNavController() }
    private val auth: AuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LoginBinding.inflate(inflater, container, false)
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
        val email    = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        val remember = binding.chkRemember.isChecked

        // TODO(3): Login -> auth.login(...)
        //          Clear navigation backstack
        lifecycleScope.launch{
            val success = auth.login(ctx,email,password,remember)
            if (success) {
                nav.popBackStack(R.id.compoundFragment, false)
                nav.navigateUp()
            }else
            {
                errorDialog("Invalid credentials")
            }
        }


    }

}