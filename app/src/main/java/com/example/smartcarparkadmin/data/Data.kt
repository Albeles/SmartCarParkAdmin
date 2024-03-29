package com.example.smartcarparkadmin.data

import android.content.Context
import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

data class Post(
    @DocumentId
    var id    : String = "",
    var desc  : String = "",
    var photo : Blob = Blob.fromBytes(ByteArray(0)),
)

data class Users(
    @DocumentId
    var id      : String = "",
    var carPlate : String = "",
    var email   : String = "",
    var name    : String = "",
    var phoneNo : String = "",
    var compoundCount : Int = 0,
    var password : String = "",
    var status : String = ""
    )

data class Admin(
    @DocumentId
    var id:String="",
    var adminEmail: String = "",
    var password: String = "",
    var name: String = "",
    var phoneNo: String = "",
    var role: String = ""

)

data class Compounds (
    @DocumentId
    var id:String="",
    var amount: Int =0,
    var date: java.util.Date = Date(),
    var status: String = "",
    var carplate : String = "",
    var location : String = "",
    var aName : String = ""
)

data class Notification (
    @DocumentId
    var id:String="",
    var Date: java.util.Date = Date(),
    var Title: String = "",
    var Description: String = "",


    )

data class uNotification (
    @DocumentId
    var id:String="",
    var date: java.util.Date = Date(),
    var title: String = "",
    var desc: String = "",


    )

val ADMIN = Firebase.firestore.collection("admin")
val USERS = Firebase.firestore.collection("users")
val COMPOUND = Firebase.firestore.collection("compounds")
val UNO = Firebase.firestore.collection("notifications")

fun RESTORE_USERS(ctx: Context) {
    // (1) DELETE users

    USERS.get().addOnSuccessListener { snap ->
        for (doc in snap.documents) {
            USERS.document(doc.id).delete()
        }
        val admin1 = Admin(
            adminEmail = "admin1@gmail.com",
            password = "password",
            name = "Yeoh Wei Liang",

        )

        USERS.document().set(admin1)
        val admin2 = Admin(
            adminEmail = "admin2@gmail.com",
            password = "password",
            name = "Lum Chun Hong",

        )
        USERS.document().set(admin2)

    }
}
