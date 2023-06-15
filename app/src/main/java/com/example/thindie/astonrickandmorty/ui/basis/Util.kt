package com.example.thindie.astonrickandmorty.ui.basis

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.W
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutsourceLogic
import kotlin.coroutines.cancellation.CancellationException

fun Fragment.FOC(e: OutsourceLogic.UiState.BadResult) {
    when (e.error) {
       NullPointerException::class -> {
            W { e.error.message }
        }
        CancellationException::class -> {
            W { "cancellation coroutine" }
        }

        java.net.UnknownHostException::class -> {

        }
        else -> {
            Toast.makeText(
                requireActivity(),
                getString(R.string.message_error_something_wrong),
                Toast.LENGTH_SHORT
            )
                .show()
            W { " looks like something wrong - ${e.error} " }
        }

    }
}

fun Fragment.FOC(message: String) {
    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT)
        .show()
}
//som mapper


fun Throwable.mapMessage(): Int {
    when (this) {
        java.lang.NullPointerException::class -> {}
    }
    return 0
}