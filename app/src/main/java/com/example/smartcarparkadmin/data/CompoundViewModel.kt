package com.example.smartcarparkadmin.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.type.Date
import com.google.type.DateTime
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class CompoundViewModel : ViewModel(){

    private val usersLiveData = MutableLiveData<Users?>()
    private val compLiveData = MutableLiveData<List<Compounds>>()
    private var listener: ListenerRegistration? = null
    private var compList = listOf<Compounds>()
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val users = MutableLiveData<List<Users>>()
    private val col = Firebase.firestore.collection("compounds")
    private var tamount = 50
    private var comcount = 0

    private var name = ""       // Search
    private var field = ""      // Sort
    private var reverse = false // Sort

    override fun onCleared() {
        super.onCleared()
        listener?.remove()

    }

    fun getCarPlateLiveData(): LiveData<Users?> {
        return usersLiveData
    }

    fun getUser(): Users? {
        return usersLiveData.value
    }

    fun getStudent(): Users? {
        return usersLiveData.value
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

    suspend fun checkUser2(ctx: Context,carplate: String,statuss:String): Boolean {
        // TODO(1A): Get the user record with matching email + password
        //           Return false is no matching found
        val compound = COMPOUND



            .whereEqualTo("carplate", carplate)
            .whereEqualTo("status",statuss)
            .get()
            .await()
            .toObjects<Compounds>()
            .firstOrNull() ?: return false


        return true

    }

    suspend fun addCompound( carplate: String,location:String,adminName:String):Boolean {
        val calendar = Calendar.getInstance()

        tamount=50

        val users = USERS
            .whereEqualTo("carPlate", carplate)
            .get()
            .await()
            .toObjects<Users>()
            .firstOrNull() ?: return false

            if(users.compoundCount >= 3){
                tamount=150
            }

        val l = Compounds(
            id= "",
            amount = tamount,
            date = Date(),
            status = "Pending",
            carplate = carplate.toString().trim(),
            location = location,
            aName = adminName
            )
        Firebase.firestore
            .collection("compounds")
            .document()
            .set(l)


        return true
    }
    suspend fun addCC(ctx: Context,carplate: String): Boolean {
        // TODO(1A): Get the user record with matching email + password
        //           Return false is no matching found

        val users = USERS
            .whereEqualTo("carPlate", carplate)
            .get()
            .await()
            .toObjects<Users>()
            .firstOrNull() ?: return false


        var ccount = users.compoundCount.toInt()
        comcount = ccount

        var status = "suspended"
        var statuss = "barred"
            if (ccount in 2..4){
                ccount+=1
                db.collection("users").document(users.id).update("compoundCount",ccount.toInt(),"status",status.toString())
            }else if(ccount ==5){
                ccount+=1
                db.collection("users").document(users.id).update("compoundCount",ccount.toInt(),"status",statuss.toString())

            }else{
            ccount+=1
            db.collection("users").document(users.id).update("compoundCount",ccount.toInt())
            }



        return true
    }

    suspend fun decreaseCC(ctx: Context,carplate: String): Boolean {
        // TODO(1A): Get the user record with matching email + password
        //           Return false is no matching found

        val users = USERS
            .whereEqualTo("carPlate", carplate)
            .get()
            .await()
            .toObjects<Users>()
            .firstOrNull() ?: return false


        var ccount = users.compoundCount.toInt()
        comcount = ccount
        var status = "Active"
        if (ccount >= 3){
            ccount-=1
            db.collection("users").document(users.id).update("compoundCount",ccount.toInt(),"status",status.toString())
        }else{
            ccount-=1
            db.collection("users").document(users.id).update("compoundCount",ccount.toInt())
        }



        return true
    }

    suspend fun updateCompound(ctx: Context,carplates: String,id: String): Boolean {
        // TODO(1A): Get the user record with matching email + password
        //           Return false is no matching found

        val users = USERS
            .whereEqualTo("carPlate", carplates)
            .get()
            .await()
            .toObjects<Users>()
            .firstOrNull() ?: return false

        var ccount = users.compoundCount.toInt()
        comcount = ccount
        var status = "Active"
        if (ccount == 3){
            ccount-=1
            db.collection("users").document(users.id).update("compoundCount",ccount.toInt(),"status",status.toString())
        }else{
            ccount-=1
            db.collection("users").document(users.id).update("compoundCount",ccount.toInt())
        }

        val compounds = COMPOUND
            .whereEqualTo("id", id)
            .get()
            .await()
            .toObjects<Compounds>()
            .firstOrNull() ?: return false

        var cstatus = "Cancel"
        db.collection("compounds").document(compounds.id).delete()




        return true
    }


    //    init {
//        col.addSnapshotListener { value, _ -> posts.value = value?.toObjects() }
//    }

    init {
        viewModelScope.launch {
            col.addSnapshotListener { value, _ ->
                if (value == null) return@addSnapshotListener
                compList = value.toObjects<Compounds>()

                updateResult()
            }
        }
    }
    fun init() = Unit // void

    fun get(id: String) = users.value?.find { it.id == id }

    fun gets(id: String) = compLiveData.value?.find { it.id == id }

    fun getcom() = compLiveData.value?.forEach{col.document().get()}

    fun getAll() = usersLiveData // live data

    fun getComp() = compLiveData // live data

    fun getEx() = compList

    fun delete(id: String) {
        col.document(id).delete()
    }

    fun deleteAll() {
        users.value?.forEach { col.document(it.id).delete() }
    }

    fun deleteCt(carplatess:String) {
        db.collection("compounds").document(carplatess).delete()
    }

    fun set(f: Compounds) {
        col.document(f.id).set(f)
    }

    fun sets(u: Users) {
        col.document(u.id).set(u)
    }

    private fun updateResult() {
        var list = compList

        list = list.filter {
            it.carplate.contains(name, true)
        }

        list = when (field) {
            "date" -> list.sortedBy { it.date }
            "carplate" -> list.sortedBy { it.carplate }
            else -> list
        }
        if (reverse) list = list.reversed()

            compLiveData.value = list
    }

    fun search(name: String) {
        this.name = name
        updateResult()
    }

    fun sort(field: String): Boolean {
        reverse = if (this.field == field) !reverse else false

        this.field = field
        updateResult()
        return reverse
    }

    // Validation
    //----------------------------------------------------------------------------------------------

    private fun idExists(id: String) = users.value?.any { it.id == id } ?: false

    fun validate(f: Compounds, insert: Boolean = true): String {
        val regexId = Regex("""^[A-Z]\d{3}$""")
        var e = ""

//        if (insert) {
//            e += if (f.id == "") "- Id is required.\n"
//            else if (!f.id.matches(regexId)) "- Id format is invalid (format: X999).\n"
//            else if (idExists(f.id)) "- Id is duplicated.\n"
//            else ""
//        }

        e += if (f.carplate == "") "- Car plate number is required.\n"
        else if (f.carplate.length < 10) "- Car plate number is too long (no more than 10 letters).\n"

        else ""

   //     e += if (f.photo.toBytes().isEmpty()) "- Photo is required.\n"
    //    else ""

        return e
    }

}