package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.smartcarparkadmin.R
import com.example.smartcarparkadmin.data.CompoundViewModel
import com.example.smartcarparkadmin.databinding.CompoundListBinding
import com.example.smartcarparkadmin.util.CompoundAdapter

class CompoundListFragment : Fragment() {

    private lateinit var binding: CompoundListBinding
    private val nav by lazy { findNavController() }
    private val cv: CompoundViewModel by activityViewModels()

    private lateinit var adapter: CompoundAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = CompoundListBinding.inflate(inflater, container, false)
//        binding.issueCompound.setOnClickListener { compound() }

        cv.search("")
        sort("date")

        adapter = CompoundAdapter { holder, comps ->
            // Item click -> navigate to UpdateFragment (id)
            holder.binding.btnEdit.setOnClickListener() {
                nav.navigate(R.id.compoundUpdateFragment, bundleOf("id" to comps.id,"carplatesss" to comps.carplate))
            }
            // Delete button click -> delete record
//            holder.binding.btnDelete.setOnClickListener {
//                delete(post.id)
//            }
        }
        binding.rv.adapter = adapter
        binding.rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        cv.getComp().observe(viewLifecycleOwner) {
            adapter.submitList(it)

            binding.txtCount.text = "${it.size} record(s)"
        }

        // Search
        binding.sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(name: String) = true
            override fun onQueryTextChange(name: String): Boolean {
                cv.search(name)
                return true
            }
        })

        binding.addNo.setOnClickListener { sort("carplate") }
        binding.btnDesc.setOnClickListener { sort("date") }

        return binding.root
    }

    private fun sort(field: String) {
        val reverse = cv.sort(field)

        binding.addNo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        binding.btnDesc.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

        val res = if (reverse) R.drawable.ic_down else R.drawable.ic_up

        when (field) {
            "carplate"    -> binding.addNo.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0)
            "date"  -> binding.btnDesc.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0)
        }
    }
}