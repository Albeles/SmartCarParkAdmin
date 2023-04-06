package com.example.smartcarparkadmin.data

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
//import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AuthViewModel : ViewModel() {


    private val userLiveData = MutableLiveData<Admin?>()
    private var listener: ListenerRegistration? = null
    var adminid = ""
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()



    // Remove snapshot listener when view model is destroyed
    override fun onCleared() {
        super.onCleared()
        listener?.remove()

    }

    // Return observable live data
    fun getUserLiveData(): LiveData<Admin?> {
        return userLiveData
    }



    // Return user from live data

    fun getUser(): Admin? {
        return userLiveData.value
    }


    // TODO(1): Login
    // TODO(1): Login
    suspend fun login(ctx: Context, email: String, password: String, remember: Boolean = false): Boolean {
        // TODO(1A): Get the user record with matching email + password
        //           Return false is no matching found

        val admin = ADMIN

            .whereEqualTo("adminEmail", email)
            .whereEqualTo("password", password)
            .get()
            .await()
            .toObjects<Admin>()
            .firstOrNull() ?: return false

        listener?.remove()
        listener = ADMIN.document(admin.id).addSnapshotListener { doc, _ ->
            userLiveData.value = doc?.toObject()

        }
        adminid = admin.toString()
        if (remember) {
            getPreferences(ctx)
                .edit()
                .putString("adminEmail", email)
                .putString("password", password)
                .apply()
        }

        // TODO(1B): Setup snapshot listener
        //           Update live data -> user
        //if(user.id != null) {
        // listener = USERS.document(user.id).addSnapshotListener { doc, _ ->
        //   userLiveData.value = doc?.toObject()
        //  }

        // TODO(6A): Handle remember-me -> add shared preferences

        return true
    }
    suspend fun checkemail(ctx: Context,email: String): Boolean {
        // TODO(1A): Get the user record with matching email + password
        //           Return false is no matching found

        val users = ADMIN

            .whereEqualTo("adminEmail", email)
            .get()
            .await()
            .toObjects<Admin>()
            .firstOrNull() ?: return false


        return true
    }

    suspend fun checkRole(ctx: Context,email: String): Any {
        // TODO(1A): Get the user record with matching email + password
        //           Return false is no matching found
        var role ="Admin"
        val users = ADMIN

            .whereEqualTo("adminEmail", email)
            .get()
            .await()
            .toObjects<Admin>()
            .firstOrNull() ?: return false

//        if(users.status.toString()==""){
            role = users.role
//        }

        role ="Admin"
        return role
    }
    suspend fun addProfile( adminEmail: String,name:String,password: String,phone:String):Boolean {



        val l = Admin(
            id = "",
            adminEmail = adminEmail,
            name =name,
            password = password,
            phoneNo = phone

        )
        validate(l,false)

        Firebase.firestore
            .collection("admin")
            .document()
            .set(l)


        return true
    }

    suspend fun updateUser(adminEmail: String, name:String, password: String, phone:String):Boolean {

        val user = ADMIN

            .whereEqualTo("adminEmail", adminEmail)
            .get()
            .await()
            .toObjects<Admin>()
            .firstOrNull() ?: return false

        val l = Admin(

            id = user.id,
            adminEmail = adminEmail,
            name =name,
            password = password,
            phoneNo = phone

        )

        Firebase.firestore
            .collection("admin")
            .document(user.id)
            .set(l)

        return true
    }



    // TODO(2): Logout
    fun logout(ctx: Context) {
        // TODO(2A): Remove snapshot listener
        //           Update live data -> null
        listener?.remove()

        userLiveData.value =null




        // TODO(6B): Handle remember-me -> clear shared preferences
        getPreferences(ctx)
            .edit()
            //    .remove("email")
            //    .remove("password")
            .clear()
            .apply()

        //[OR] ctx.deleteSharedPreferences("AUTH")
    }

    // TODO(6): Get shared preferences
    private fun getPreferences(ctx: Context): SharedPreferences {
        //return ctx.getSharedPreferences("Auth",Context.MODE_PRIVATE)
        return EncryptedSharedPreferences.create(
            "AUTH",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            ctx,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    // TODO(7): Auto login from shared preferences
    suspend fun loginFromPreferences(ctx: Context) {
        val p = getPreferences(ctx)
        val email = p.getString("email",null)
        val password = p.getString("password",null)
        if(email != null && password != null){
            login(ctx,email,password)
        }
    }
    fun validate(f: Admin, insert: Boolean = true): String {
        val regexId = Regex("""^[A-Z]\d{3}$""")
        var e = ""

//        if (insert) {
//            e += if (f.id == "") "- Id is required.\n"
//            else if (!f.id.matches(regexId)) "- Id format is invalid (format: X999).\n"
//            else if (idExists(f.id)) "- Id is duplicated.\n"
//            else ""
//        }

        e += if (f.adminEmail == "") "- Admin Email is required.\n"
        else if (f.adminEmail.length < 10) "- Car plate number is too long (no more than 10 letters).\n"

        else ""

        //     e += if (f.photo.toBytes().isEmpty()) "- Photo is required.\n"
        //    else ""

        return e
    }



}