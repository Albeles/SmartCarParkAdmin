package com.example.smartcarparkadmin.data

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*

class AnnouncementViewModel : ViewModel(){
    private var listener: ListenerRegistration? = null
    private var noList = listOf<uNotification>()
    private val nodata = MutableLiveData<uNotification?>()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val nomlist = MutableLiveData<List<uNotification>>()
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
            uno.addSnapshotListener { value, _ ->
                if (value == null) return@addSnapshotListener
                noList = value.toObjects<uNotification>()
                updateResult()
            }
        }
    }
    fun getanno(id: String) = nomlist.value?.find { it.id == id }
    fun getNos(): uNotification? {
        return nodata.value
    }

    fun getNo() = nomlist // live data

    suspend fun addUNo(Title:String, date: Date, desc:String){
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

    suspend fun updateNo(id:String,Title: String, Desc:String, date:Date):Boolean {

        val noti = uno

            .whereEqualTo("id", id)
            .get()
            .await()
            .toObjects<Admin>()
            .firstOrNull() ?: return false

        val l = uNotification(

            id = uno.id,
           title = Title,
            desc = Desc,
            date = date,


        )

        Firebase.firestore
            .collection("notifications")
            .document(uno.id)
            .set(l)

        return true
    }

    private fun updateResult() {
        var list = noList

        list = list.filter {
            it.title.contains(name, true)
        }

        list = when (field) {
            "date" -> list.sortedBy { it.date }
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