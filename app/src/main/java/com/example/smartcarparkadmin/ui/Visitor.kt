package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.smartcarparkadmin.R
import com.example.smartcarparkadmin.data.AuthViewModel
import com.example.smartcarparkadmin.data.CompoundViewModel
import com.example.smartcarparkadmin.data.SuspensionViewMode
import com.example.smartcarparkadmin.databinding.SuspensionBinding
import com.example.smartcarparkadmin.databinding.VisitorviewBinding
import com.example.smartcarparkadmin.util.VisitorAdapter
import com.example.smartcarparkadmin.util.errorDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class Visitor : Fragment() {
    private lateinit var binding: VisitorviewBinding
    private val nav by lazy { findNavController() }
    private val cv: SuspensionViewMode by activityViewModels()
    private val authss: AuthViewModel by activityViewModels()
    private val auths: CompoundViewModel by activityViewModels()
    private var carplate2 =""

    private lateinit var adapter: VisitorAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = VisitorviewBinding.inflate(inflater, container, false)
//        binding.issueCompound.setOnClickListener { compound() }

//        cv.search("")
//        sort("date")

        adapter = VisitorAdapter { holder, users ->

            // Item click -> navigate to UpdateFragment (id)
//            holder.binding.btnEdit.setOnClickListener() {
//                nav.navigate(R.id.suspentionUpdateFragment, bundleOf("carPlate" to users.carPlate,))
//                //directly delete
//            }

            holder.binding.btnEdit.setOnClickListener{ delete(users.id,users.carPlate) }
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
        cv.search("Visitor")

        binding.sv2.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(carplate: String) = true
            override fun onQueryTextChange(carplate: String): Boolean {
                cv.search2("Visitor",carplate)
                if(carplate==""){
                    cv.search("Visitor")
                }else{
                    cv.search2("Visitor",carplate)

                }
                return true
            }
        })


        return binding.root
    }

    private fun delete (id:String,carplates:String){
        val ctx      = requireContext()
        val status = "Pending"
        lifecycleScope.launch{

        val success = auths.checkUser(ctx,carplates)
        if (success) {
            val success2 = auths.checkUser2(ctx,carplates,status)
            if(success2){
                errorDialog ("This user haven't pay the previous compound !")
            }
            else {
                Firebase.firestore
                    .collection("users")
                    .document(id)
                    .delete()
//                auths.addCC(ctx, carplate2)
//                val admin = authss.getUser()
//                var adminName = ""
//                if (admin != null) {
//                    adminName = admin.name
//
//                    auths.addCompound(carplate2, "Block K", adminName)
//                    nav.navigateUp()
                }
            }

    }
        }
}
