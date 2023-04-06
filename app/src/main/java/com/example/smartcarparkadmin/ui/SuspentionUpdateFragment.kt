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
import com.example.smartcarparkadmin.data.SuspensionViewMode
import com.example.smartcarparkadmin.databinding.SuspentionupdateBinding
import com.example.smartcarparkadmin.util.errorDialog
import com.example.smartcarparkadmin.util.hideKeyboard
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


class SuspentionUpdateFragment: Fragment(){
    private lateinit var binding: SuspentionupdateBinding
    private val nav by lazy { findNavController() }
    private val auths: SuspensionViewMode by activityViewModels()
    private val authss: CompoundViewModel by activityViewModels()
    private val id by lazy { arguments?.getString("carPlate") ?: "" }
    private val dateFormat= SimpleDateFormat("dd/MM/yyyy")
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SuspentionupdateBinding.inflate(inflater, container, false)
        reset()
        binding.compoundsubmits2.setOnClickListener{compoundInsert()}
        binding.comBack.setOnClickListener{back()}

        return binding.root
    }

    private fun compoundInsert() {
        hideKeyboard()

        val ctx      = requireContext()
        val carplate    = binding.ucarplate.text.toString().trim()


        lifecycleScope.launch{


            val success = auths.checkUser(ctx,carplate)
            if (success) {
                auths.edituser(ctx,carplate)
//                db.collection("compounds").document(id).delete()
//                auths.deleteCt(carplate)

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

//        db.collection("users").document("20WBM12387").get()
//        val f = auths.gets("20WBM12387")
//        val f = db.collection("users").document("20WBM12387").get().result
//        val j = f.id
        val o = auths.gets(id)

        if (o == null) {
            nav.navigateUp()
            return
        }
        var cp = o.carPlate
        binding.ucarplate.setText(o.carPlate.toString().trim())
        var statuss = o.status


        binding.ccs.setText(o.compoundCount.toString().trim())

        binding.contactnumssss.setText(o.phoneNo).toString().trim()

        binding.emailss.setText(o.email.toString().trim())

        binding.unames.setText(o.name.toString().trim())

        binding.ustatuss.setText(o.status.toString())
    }
}