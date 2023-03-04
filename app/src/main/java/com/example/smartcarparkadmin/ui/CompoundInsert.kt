package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.smartcarparkadmin.data.CompoundViewModel
import com.example.smartcarparkadmin.util.CompoundAdapter
import com.example.smartcarparkadmin.databinding.CompoundBinding
import com.example.smartcarparkadmin.databinding.CompoundInsertBinding
import com.example.smartcarparkadmin.databinding.HeaderLoginBinding
import com.example.smartcarparkadmin.util.setImageBlob
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.smartcarparkadmin.R
class CompoundInsert : Fragment(){
    private lateinit var binding: CompoundInsertBinding
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = CompoundInsertBinding.inflate(inflater, container, false)

        return binding.root
}

}