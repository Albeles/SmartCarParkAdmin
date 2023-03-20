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
import com.example.smartcarparkadmin.data.SuspensionViewMode
import com.example.smartcarparkadmin.databinding.SuspensionBinding
import com.example.smartcarparkadmin.util.SuspensionAdapter

class SuspensionFragment : Fragment() {
    private lateinit var binding: SuspensionBinding
    private val nav by lazy { findNavController() }
    private val cv: SuspensionViewMode by activityViewModels()

    private lateinit var adapter: SuspensionAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SuspensionBinding.inflate(inflater, container, false)
//        binding.issueCompound.setOnClickListener { compound() }

//        cv.search("")
//        sort("date")

        adapter = SuspensionAdapter { holder, comps ->
            // Item click -> navigate to UpdateFragment (id)

            // Delete button click -> delete record
//            holder.binding.btnDelete.setOnClickListener {
//                delete(post.id)
//            }
        }
        binding.nrv.adapter = adapter
        binding.nrv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        cv.getU().observe(viewLifecycleOwner) {
            adapter.submitList(it)

        }
        cv.search("suspended")
        return binding.root
}
}