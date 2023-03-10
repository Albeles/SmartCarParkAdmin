package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.example.smartcarparkadmin.data.CompoundViewModel
import com.example.smartcarparkadmin.util.CompoundAdapter
import com.example.smartcarparkadmin.databinding.CompoundBinding
import com.example.smartcarparkadmin.databinding.CompoundInsertBinding
import com.example.smartcarparkadmin.databinding.HeaderLoginBinding
import com.example.smartcarparkadmin.util.setImageBlob
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration

import com.example.smartcarparkadmin.R

class CompoundFragment : Fragment() {
    private lateinit var binding: CompoundBinding
    private val nav by lazy { findNavController() }
    private val vm: CompoundViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = CompoundBinding.inflate(inflater, container, false)
        binding.issueCompound.setOnClickListener { compound() }
        binding.CompoundList.setOnClickListener { comlist() }


        return binding.root
    }
    private fun compound(){
        nav.navigate(R.id.compoundInsert)

    }

    private fun comlist(){
        nav.navigate(R.id.compoundListFragment)

    }


}