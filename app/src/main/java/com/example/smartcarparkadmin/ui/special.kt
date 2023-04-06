package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.smartcarparkadmin.R
import com.example.smartcarparkadmin.data.AuthViewModel
import com.example.smartcarparkadmin.databinding.FragmentHomeBinding
import com.example.smartcarparkadmin.databinding.HeaderBinding

class special: Fragment()  {

    private lateinit var bindings: HeaderBinding
    //    private lateinit var bindings: HeaderBinding
    private val auth: AuthViewModel by activityViewModels()
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        bindings = HeaderBinding.inflate(inflater, container, false)
//        bindings = HeaderBinding.inflate(inflater, container, false)
       val admins = auth.getUser()

        if (admins != null) {
            bindings.welcomes.text = admins.name.toString()
        }
        bindings.welcomes.text = "ssss"
//        bindings.welcomes.text = "shit"

//        reset()

        return bindings.root

    }
}