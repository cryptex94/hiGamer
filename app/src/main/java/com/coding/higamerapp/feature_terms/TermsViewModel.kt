package com.coding.higamerapp.feature_terms

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.coding.higamerapp.common.util.Constants.TERMS
import com.coding.higamerapp.feature_profile.data.DataStoreManager
import com.coding.higamerapp.feature_terms.data.Terms
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TermsViewModel
@Inject constructor(
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {

    private val _checkTerms = mutableStateOf(false)
    val checkTerms: MutableState<Boolean> = _checkTerms


    suspend fun saveTermsDataStore() {
        dataStoreManager.storeValue(TERMS, _checkTerms.value)
        Terms.terms = true
    }

    fun setCheckTerms(checked: Boolean) {
        _checkTerms.value = !checked
    }

}