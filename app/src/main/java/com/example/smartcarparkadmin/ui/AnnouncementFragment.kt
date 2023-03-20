package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.smartcarparkadmin.R
import com.example.smartcarparkadmin.data.AnnouncementViewModel
import com.example.smartcarparkadmin.data.NotificationViewMode
import com.example.smartcarparkadmin.databinding.AnnouncementlistBinding
import com.example.smartcarparkadmin.util.AnnouncementAdapter
import com.example.smartcarparkadmin.util.NotificationAdapter


class AnnouncementFragment : Fragment() {

    private lateinit var binding: AnnouncementlistBinding
    private val nav by lazy { findNavController() }
    private val nrrv: AnnouncementViewModel by activityViewModels()

    private lateinit var adapters: AnnouncementAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = AnnouncementlistBinding.inflate(inflater, container, false)
        binding.addNo.setOnClickListener{addNotification()}
        adapters = AnnouncementAdapter { holder, nomps ->
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
        binding.addNo2.setOnClickListener { sort("date") }
        return binding.root
    }
    fun addNotification(){
        nav.navigate(R.id.notificationAddFragment)
    }

    private fun sort(field: String) {
        val reverse = nrrv.sort(field)

        binding.addNo2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)


        val res = if (reverse) R.drawable.ic_down else R.drawable.ic_up

        when (field) {
            "date"    -> binding.addNo2.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0)

        }
        binding.nrv.scrollToPosition(0)
    }
}