package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.smartcarparkadmin.R
import com.example.smartcarparkadmin.data.NotificationViewMode
import com.example.smartcarparkadmin.databinding.NotificationBinding
import com.example.smartcarparkadmin.util.CompoundAdapter
import com.example.smartcarparkadmin.util.NotificationAdapter

class NotificationFragment : Fragment() {

    private lateinit var binding: NotificationBinding
    private val nav by lazy { findNavController() }
    private val nrrv: NotificationViewMode by activityViewModels()

    private lateinit var adapters: NotificationAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = NotificationBinding.inflate(inflater, container, false)
        adapters = NotificationAdapter { holder, nomps ->
            // Item click -> navigate to UpdateFragment (id)
//            holder.binding.btnEdit.setOnClickListener() {
//                nav.navigate(R.id.compoundUpdateFragment, bundleOf("id" to comps.id,"carplatesss" to comps.carplate))
//            }
            // Delete button click -> delete record
//            holder.binding.btnDelete.setOnClickListener {
//                delete(post.id)
//            }
        }


        binding.nrv.adapter = adapters
        binding.nrv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        nrrv.getNo().observe(viewLifecycleOwner) {
            adapters.submitList(it)
    }
        return binding.root



}
}