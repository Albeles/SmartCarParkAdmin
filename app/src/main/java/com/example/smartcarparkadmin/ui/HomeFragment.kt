package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.smartcarparkadmin.R
import com.example.smartcarparkadmin.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.parkinglota.setOnClickListener { parka() }
        binding.parkinglotb.setOnClickListener { parkb() }

        return binding.root
    }

    private fun parka(){
        nav.navigate(R.id.parkingLotA)
    }

    private fun parkb(){
        nav.navigate(R.id.parkingLotB)
    }



    //private fun restore() {
    //    val ctx = requireContext()
    //    RESTORE_USERS(ctx)
    //    snackbar("Users restored.")
    }

