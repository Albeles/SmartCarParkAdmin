package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.smartcarparkadmin.data.AnnouncementViewModel
import com.example.smartcarparkadmin.data.AuthViewModel
import com.example.smartcarparkadmin.data.CompoundViewModel
import com.example.smartcarparkadmin.databinding.AnnoucementeditBinding
import com.example.smartcarparkadmin.databinding.CompoundUpdateBinding
import com.example.smartcarparkadmin.util.errorDialog
import com.example.smartcarparkadmin.util.hideKeyboard
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class AnnouncementEditFragment : Fragment(){
    private lateinit var binding: AnnoucementeditBinding
    private val nav by lazy { findNavController() }
    private val auths: AnnouncementViewModel by activityViewModels()
    private val id by lazy { arguments?.getString("id") ?: "" }
    private val dateFormat= SimpleDateFormat("dd/MM/yyyy")
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = AnnoucementeditBinding.inflate(inflater, container, false)
        reset()
        binding.compoundsubmits.setOnClickListener{editAnnounce()}
        binding.ComBack.setOnClickListener{back()}

        return binding.root
    }
    private fun editAnnounce() {
        hideKeyboard()

        val ctx      = requireContext()
        val ids = id


        lifecycleScope.launch{

            if (ids == "") {
                errorDialog("Invalid announcement selected")
            }else
            {
//                auths.updateNo(id,binding.titles.text.toString(),binding.ustatuss.text.toString(),binding.dates)
                db.collection("notifications").document(id).update("title",binding.titles.text.toString(),"desc",binding.ustatuss.text.toString())
                nav.navigateUp()

            }


            {
                errorDialog("Invalid car plate")
            }
        }


    }






    private fun back(){
        nav.navigateUp()
    }
    private fun reset() {

        val f = auths.getanno(id)

        if (f == null) {
            nav.navigateUp()
            return
        }

        binding.titles.setText(f.title.toString())



        binding.ustatuss.setText(f.desc.toString())

        binding.udates.setText(dateFormat.format(f.date).toString().trim())



        binding.ustatuss.requestFocus()
    }
}
