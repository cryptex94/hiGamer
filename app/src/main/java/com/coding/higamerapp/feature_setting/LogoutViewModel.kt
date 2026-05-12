package com.coding.higamerapp.feature_setting

import androidx.lifecycle.ViewModel
import com.coding.higamerapp.feature_gamers.domain.use_case.delete_gamer.DeleteGamer
import com.coding.higamerapp.feature_login.presentation.util.UserRepository.myFirebaseId
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val deleteGamer: DeleteGamer,

    ) : ViewModel() {

    private val firebase = FirebaseAuth.getInstance()


    suspend fun signOut() {
        deleteGamerFromDatabase()
        firebase.signOut()
    }

    private suspend fun deleteGamerFromDatabase() {
        myFirebaseId?.let {
            deleteGamer(it).collect()
        }
    }
}