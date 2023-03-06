package com.example.smartcarparkadmin.data

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.media.Image
import android.util.Log
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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcarparkadmin.R
import kotlinx.coroutines.launch


class AuthViewModel : ViewModel() {


    private val userLiveData = MutableLiveData<Admin?>()
    private var listener: ListenerRegistration? = null
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

    fun getStudent(): Admin? {
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

    suspend fun writeNewUser( email: String, name: String,password: String):Boolean {

        val user = ADMIN

            .whereEqualTo("adminEmail", email)
            .get()
            .await()
            .toObjects<Admin>()
            .firstOrNull() ?: return false

        db.collection("admin").document(user.id).update("name",name,"password",password)


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



}