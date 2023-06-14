package com.example.thindie.astonrickandmorty.ui.basis

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutsourceLogic

fun Fragment.FOC(e: OutsourceLogic.UiState.BadResult) {
    Toast.makeText(requireActivity(), e.error.message.toString(), Toast.LENGTH_LONG).show()
}
//som mapper