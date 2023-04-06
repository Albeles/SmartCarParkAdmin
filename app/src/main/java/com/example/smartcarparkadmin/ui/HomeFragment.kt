package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.smartcarparkadmin.R
import com.example.smartcarparkadmin.data.AuthViewModel
import com.example.smartcarparkadmin.databinding.FragmentHomeBinding
import com.example.smartcarparkadmin.databinding.HeaderBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

//    private lateinit var bindings: HeaderBinding
    private val auth: AuthViewModel by activityViewModels()
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        bindings = HeaderBinding.inflate(inflater, container, false)
        binding.parkinglota.setOnClickListener { parka() }
        binding.parkinglotb.setOnClickListener { parkb() }





//        bindings.welcomes.text = "shit"

//        reset()

        return binding.root

    }

    private fun parka(){
        nav.navigate(R.id.parkingLotA)
    }
    private fun report(){
        nav.navigate(R.id.report)
    }
//    private fun reset(){
//        bindings.welcomes.text = "shit"
//        val profile = auth.getUser()
//        if (profile != null) {
//            bindings.welcomes.text = profile.name
//
//        }
//    }

    private fun parkb(){
        nav.navigate(R.id.parkingLotB)
    }



    //private fun restore() {
    //    val ctx = requireContext()
    //    RESTORE_USERS(ctx)
    //    snackbar("Users restored.")
    }

