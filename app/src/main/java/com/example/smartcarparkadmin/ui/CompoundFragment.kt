package com.example.smartcarparkadmin.ui

import androidx.fragment.app.activityViewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import com.example.smartcarparkadmin.data.AuthViewModel
import com.example.smartcarparkadmin.databinding.CompoundBinding
import com.example.smartcarparkadmin.util.errorDialog
import com.example.smartcarparkadmin.util.hideKeyboard
import com.example.smartcarparkadmin.util.snackbar
import kotlinx.coroutines.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.smartcarparkadmin.databinding.ActivityMainBinding
import com.example.smartcarparkadmin.databinding.HeaderLoginBinding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.smartcarparkadmin.databinding.FragmentHomeBinding

class CompoundFragment : Fragment() {
    private lateinit var binding: CompoundBinding
    private val nav by lazy { findNavController() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = CompoundBinding.inflate(inflater, container, false)

        return binding.root
    }
}