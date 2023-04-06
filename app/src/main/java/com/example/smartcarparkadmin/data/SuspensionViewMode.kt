package com.example.smartcarparkadmin.data

import android.content.Context
import android.icu.text.CaseMap.Title
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class SuspensionViewMode : ViewModel(){
    private var listener: ListenerRegistration? = null
    private val userLiveData = MutableLiveData<Users?>()
    private var uLists = listOf<Users>()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val ulist = MutableLiveData<List<Users>>()
    private val col = Firebase.firestore.collection("users")

    private var name = ""       // Search
    private var carplates = ""       // Search
    private var field = ""      // Sort
    private var reverse = false // Sort

    override fun onCleared() {
        super.onCleared()
        listener?.remove()

    }
    fun gets(id: String) = ulist.value?.find { it.carPlate == id }
//suspend fun gets(id:String): Boolean {
//    val users = USERS
//
//        .whereEqualTo("id", id)
//        .get()
//        .await()
//        .toObjects<Users>()
//        .firstOrNull() ?: return false
//
//    }
    fun getUser(): Users? {
        return userLiveData.value
    }

    suspend fun checkUser(ctx: Context,carplate: String): Boolean {
        // TODO(1A): Get the user record with matching email + password
        //           Return false is no matching found

        val users = USERS

            .whereEqualTo("carPlate", carplate)
            .get()
            .await()
            .toObjects<Users>()
            .firstOrNull() ?: return false

        return true
    }

    suspend fun edituser(ctx: Context, carplate: String):Boolean {
        val calendar = Calendar.getInstance()



        val users = USERS
            .whereEqualTo("carPlate", carplate)
            .get()
            .await()
            .toObjects<Users>()
            .firstOrNull() ?: return false


        val l = Users(
         id      =  users.id,
        carPlate =  users.carPlate,
        email   =  users.email,
        name    =  users.name,
        phoneNo =  users.phoneNo,
        compoundCount =  users.compoundCount,
        password =  users.password,
        status =  "Active"

        )
        Firebase.firestore
            .collection("compounds")
            .document()
            .set(l)
        db.collection("users").document(users.id).update("status","Active")

        return true
    }

    init {
        viewModelScope.launch {
            col.addSnapshotListener { value, _ ->
                if (value == null) return@addSnapshotListener
                uLists = value.toObjects<Users>()
                updateResult()
            }
        }
    }

    fun getU() = ulist // live data

    private fun updateResult() {
        var list = uLists

        list = list.filter {
            it.status.contains(name, true)
        }

        list = when (field) {
            "status" -> list.sortedBy { it.status }
            "carPlate" -> list.sortedBy { it.carPlate }
            else -> list
        }
        if (reverse) list = list.reversed()

        ulist.value = list
    }

    private fun updateResult2() {
        var list = uLists

        list = list.filter {
            it.status.contains(name, true)
            it.carPlate.contains(carplates, true)
        }

        list = when (field) {
            "status" -> list.sortedBy { it.status }
            "carPlate" -> list.sortedBy { it.carPlate }
            else -> list
        }
        if (reverse) list = list.reversed()

        ulist.value = list
    }




    fun search(name: String) {
        this.name = name
        updateResult()
    }
    fun search2(name: String,carplate: String) {
        this.name = name
        this.carplates = carplate
        updateResult2()
    }

}
