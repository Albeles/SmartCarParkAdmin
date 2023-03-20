package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.smartcarparkadmin.data.NotificationViewMode
import androidx.navigation.ui.setupWithNavController
import com.example.smartcarparkadmin.R
import com.example.smartcarparkadmin.data.AuthViewModel
import com.example.smartcarparkadmin.data.Compounds
import com.example.smartcarparkadmin.data.User
import com.example.smartcarparkadmin.databinding.NotificationAddBinding
import com.example.smartcarparkadmin.util.errorDialog
import com.example.smartcarparkadmin.util.hideKeyboard
import kotlinx.coroutines.launch
import java.util.Date

class NotificationAddFragment : Fragment() {
    private lateinit var binding: NotificationAddBinding
    private val nav by lazy { findNavController() }
    private val auths: NotificationViewMode by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = NotificationAddBinding.inflate(inflater, container, false)

        binding.compoundsubmit.setOnClickListener{addNoti()}


        return binding.root

}private fun addNoti() {
        hideKeyboard()

        val ctx      = requireContext()
        val title = binding.ntitles.text.toString()
        val date = Date()
        val desc = binding.ndescs.text.toString()

        lifecycleScope.launch{

                auths.addNo(title,date,desc)



    }

    }

}