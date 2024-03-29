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



class NotificationViewMode : ViewModel(){
    private var listener: ListenerRegistration? = null
    private var noList = listOf<Notification>()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val nomlist = MutableLiveData<List<Notification>>()
    private val col = Firebase.firestore.collection("adminNotification")
    private val uno = Firebase.firestore.collection("notifications")
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
                noList = value.toObjects<Notification>()
                updateResult()
            }
        }
    }

    fun getNo() = nomlist // live data

    suspend fun addNo(Title:String,date:Date,desc:String){
        val l = uNotification(
            id= "",
            date = date,
            desc = desc,
            title = Title
        )
        Firebase.firestore
            .collection("notifications")
            .document()
            .set(l)
    }

    private fun updateResult() {
        var list = noList

        list = list.filter {
            it.Title.contains(name, true)
        }

        list = when (field) {
            "Date" -> list.sortedBy { it.Date }
            else -> list
        }
        if (reverse) list = list.reversed()

        nomlist.value = list
    }
    fun sort(field: String): Boolean {
        reverse = if (this.field == field) !reverse else false

        this.field = field
        updateResult()
        return reverse
    }



}