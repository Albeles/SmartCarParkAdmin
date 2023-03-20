package com.example.smartcarparkadmin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.smartcarparkadmin.data.CompoundViewModel
import com.example.smartcarparkadmin.databinding.CompoundUpdateBinding
import androidx.navigation.ui.setupWithNavController
import com.example.smartcarparkadmin.R
import com.example.smartcarparkadmin.data.AuthViewModel
import com.example.smartcarparkadmin.data.Compounds
import com.example.smartcarparkadmin.data.User
import com.example.smartcarparkadmin.util.errorDialog
import com.example.smartcarparkadmin.util.hideKeyboard
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CompoundUpdateFragment : Fragment(){
    private lateinit var binding: CompoundUpdateBinding
    private val nav by lazy { findNavController() }
    private val auths: CompoundViewModel by activityViewModels()
    private val id by lazy { arguments?.getString("id") ?: "" }
    private val dateFormat=SimpleDateFormat("dd/MM/yyyy")
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = CompoundUpdateBinding.inflate(inflater, container, false)
        reset()
        binding.compoundsubmits.setOnClickListener{compoundInsert()}
        binding.ComBack.setOnClickListener{back()}

        return binding.root
    }
    private fun compoundInsert() {
        hideKeyboard()

        val ctx      = requireContext()
        val carplate    = binding.ucarplate.text.toString().trim()


        lifecycleScope.launch{


            val success = auths.checkUser(ctx,carplate)
            if (success) {
                auths.decreaseCC(ctx,carplate)
                db.collection("compounds").document(id).delete()
                auths.deleteCt(carplate)
                nav.navigateUp()
            }else
            {
                errorDialog("Invalid car plate")
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

        val f = auths.gets(id)

        if (f == null) {
            nav.navigateUp()
            return
        }
        var cp = f.carplate
        binding.ucarplate.setText(f.carplate.toString().trim())
        var statuss = f.status


        binding.uamounts.setText(f.amount.toString().trim())

        binding.date.setText(dateFormat.format(f.date).toString().trim())

        binding.ulocate.setText(f.location.toString().trim())

        binding.ustatus.requestFocus()
    }
}