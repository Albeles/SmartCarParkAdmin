package com.example.smartcarparkadmin.data

import android.content.Context
import android.icu.text.CaseMap.Title
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private var uLists = listOf<User>()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val ulist = MutableLiveData<List<User>>()
    private val col = Firebase.firestore.collection("users")

    private var name = ""       // Search
    private var field = ""      // Sort
    private var reverse = false // Sort

    override fun onCleared() {
        super.onCleared()
        listener?.remove()

    }

    init {
        viewModelScope.launch {
            col.addSnapshotListener { value, _ ->
                if (value == null) return@addSnapshotListener
                uLists = value.toObjects<User>()
                updateResult()
            }
        }
    }

    fun getU() = ulist // live data

    private fun updateResult() {
        var list = uLists

        list = list.filter {
            it.id.contains(name, true)
        }

        list = when (field) {
            "name" -> list.sortedBy { it.name }
            "carPlate" -> list.sortedBy { it.carPlate }
            else -> list
        }
        if (reverse) list = list.reversed()

        ulist.value = list
    }


}
