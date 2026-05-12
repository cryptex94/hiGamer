package com.coding.higamerapp.feature_login.presentation.util

import com.google.firebase.auth.FirebaseAuth

object UserRepository {

    var myFirebaseId: String? = null
    var token: String? = null


    operator fun invoke(): Boolean {
        return (FirebaseAuth.getInstance().currentUser == null || FirebaseAuth.getInstance().currentUser!!.isAnonymous)
    }
}