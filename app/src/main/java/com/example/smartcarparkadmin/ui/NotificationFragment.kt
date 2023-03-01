package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.smartcarparkadmin.data.RESTORE_USERS
import com.example.smartcarparkadmin.databinding.LoginBinding
import com.example.smartcarparkadmin.databinding.NotificationBinding
import com.example.smartcarparkadmin.util.snackbar

class NotificationFragment : Fragment() {

    private lateinit var binding: NotificationBinding
    private val nav by lazy { findNavController() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = NotificationBinding.inflate(inflater, container, false)

        return binding.root
    }



}