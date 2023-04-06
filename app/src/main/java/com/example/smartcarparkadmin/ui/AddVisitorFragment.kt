package com.example.smartcarparkadmin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.smartcarparkadmin.data.AuthViewModel
import com.example.smartcarparkadmin.data.SuspensionViewMode
import com.example.smartcarparkadmin.data.Users
import com.example.smartcarparkadmin.databinding.AddvBinding
import com.example.smartcarparkadmin.util.errorDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddVisitorFragment : Fragment() {

    private lateinit var binding: AddvBinding
    private val nav by lazy { findNavController() }
    private val auth: SuspensionViewMode by activityViewModels()
    private val authss: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = AddvBinding.inflate(inflater, container, false)
        binding.compoundsubmit.setOnClickListener{register()}
//        binding.btnLogin.setOnClickListener { nav.navigate(R.id.loginFragment) }
//        binding.btnSignUp.setOnClickListener { register() }
        return binding.root
    }

    private fun register() {
        var errorMessage = ""
        val email = binding.names.text
        val contact = binding.contactnums.text
        val carPlate = binding.names.text
        val password = binding.adminEmails.text

        // Validate student email or staff email
//        val regexStudentEmail = Regex("""^[a-z0-9-]+@student.tarc.edu.my""")
//        val regexStaffEmail = Regex("""^[a-z0-9-]+@tarc.edu.my""")
//        errorMessage += if (email.toString() == "" || email == null) "- Please insert your email!\n"
//        else if (!email.matches(regexStudentEmail) && !email.matches(regexStaffEmail)) "- Please insert a valid student or staff email address!\n"
//        else ""

        // Validate student ID or staff ID
//        val regexStudentId = Regex("""^[0-9]{2}[A-Z]{3}[0-9]{5}""")
//        val regexStaffId = Regex("""^[0-9]{4}""")
//        errorMessage += if (id.toString() == "" || id == null) "- Please insert your student or staff ID!\n"
//        else if (!id.matches(regexStudentId) && !id.matches(regexStaffId)) "- Please insert a valid student ID or staff ID!\n"
//        else if (idExist) "- Your ID has been registered!\n"
//        else ""

        // Validate name
        errorMessage += if (password.toString() == "" || password == null) "- Please insert your name!\n"
        else if (password.toString().length < 3) "- Name is too short!\n"
        else ""

        // Validate contact number
        val regexContact = Regex("""^\d{10}${'$'}""")
        errorMessage += if (contact.toString() == "" || contact == null) "- Please insert your contact number!\n"
        else if (!contact.matches(regexContact)) "- Please insert a valid phone number!\n"
        else ""

        // Validate car plane number
        val regexCarPlate = Regex("""^[A-Z]{1,3}\d{1,4}[A-Z]?${'$'}""")
        errorMessage += if (carPlate.toString() == "" || carPlate == null) "- Please insert your car plate number!\n"
        else if (!carPlate.matches(regexCarPlate)) "- Please enter a valid car plate format! (e.g.: ABC1234A, PQ456, WXY7890).\n"
        else ""

//        // Validate password
//        errorMessage += if (password.toString() == "" || password == null) "- Please insert your password!\n"
//        else if (binding.edtConfirmPassword.text.toString() != password.toString()) "- Please enter the same password to confirm your password!\n"
//        else ""

        if (errorMessage != "") {
            errorDialog(errorMessage)
        } else {
            val user = Users(
                //id = id.toString().trim(),
                email = carPlate.toString().trim(),
                name = "Visitor",
                phoneNo = contact.toString().trim(),
                carPlate = carPlate.toString().trim(),
                password = password.toString().trim(),
                status = "Visitor"
            )

            Firebase.firestore.collection("users").document().set(user)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Successfully Register", Toast.LENGTH_SHORT)
                        .show()
                    nav.popBackStack()
                }
        }
    }

    /* if (email.toString() == "" || email == null) { errorDialog("Please insert your email!") }
     else if (id.toString() == "" || id == null) { errorDialog("Please insert your student ID!") }
     else if (name.toString() == "" || name == null) { errorDialog("Please insert your name!") }
     else if (contact.toString() == "" || contact == null) { errorDialog("Please insert your contact number!") }
     else if (carPlate.toString() == "" || carPlate == null) { errorDialog("Please insert your car plate number!") }
     else if (password.toString() == "" || password == null) { errorDialog("Please insert your password!") }
     else if (password.toString() != binding.edtConfirmPassword.toString()) { errorDialog("Please enter the same password to confirm your password!") }
     else {
         val user = User(
             id = id.toString().trim(),
             email = email.toString().trim(),
             name = name.toString().trim(),
             phoneNo = contact.toString().trim(),
             carPlate = carPlate.toString().trim(),
             password = password.toString().trim(),
         )

         Firebase.firestore.collection("users").document(user.id).set(user)
             .addOnSuccessListener {
                 Toast.makeText(requireContext(), "Successfully Register", Toast.LENGTH_SHORT)
                     .show()
                 nav.popBackStack()
             }
     }*/
}