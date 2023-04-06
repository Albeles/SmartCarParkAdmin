package com.example.smartcarparkadmin.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.smartcarparkadmin.data.COMPOUND
import com.example.smartcarparkadmin.data.CompoundViewModel
import com.example.smartcarparkadmin.data.Compounds
import com.example.smartcarparkadmin.databinding.CompoundBinding
import com.example.smartcarparkadmin.databinding.ReportsBinding
import com.example.smartcarparkadmin.util.CompoundAdapter
import com.example.smartcarparkadmin.util.total
import com.google.firebase.firestore.FirebaseFirestore

class Report: Fragment() {
    private lateinit var binding: ReportsBinding
    private val nav by lazy { findNavController() }
    private val vm: CompoundViewModel by activityViewModels()
    private lateinit var adapter: CompoundAdapter
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var ex =0
    var totals = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ReportsBinding.inflate(inflater, container, false)

        adapter = CompoundAdapter { holder, comps ->


            val tada = vm.gets(comps.id)
            if (tada != null) {
                ex =  tada.amount
            }
        }

        vm.getComp().observe(viewLifecycleOwner) {

            adapter.submitList(it)
//             ex = it.size
            binding.compoundshow.text = it.size.toString()
        }


        dead()



//        totals +=20
//        binding.textView2.text = totals.toString()
//        binding.textView2.text = totals.toString()
        return binding.root
    }
    fun dead(){
        var totals = 0
          db.collection("compounds")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")


                        val tada = document.id

                        val gg = vm.gets(tada)
                        totals += gg!!.amount





                    binding.textView2.text = totals.toString()

                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

}