package com.example.thindie.astonrickandmorty.ui.basis.uiApi

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.thindie.astonrickandmorty.R
import kotlin.coroutines.cancellation.CancellationException

fun <T> T.FOC(e: Throwable) where T : FragmentActivity {
    when (e::class.java.name) {
        NullPointerException::class.java.name -> {
            /* ignore */
        }
        CancellationException::class.java.name -> {
            /* ignore */
        }

        java.net.UnknownHostException::class.java.name -> {
            Toast.makeText(this, R.string.message_internet_trouble, Toast.LENGTH_SHORT).show()
        }
        IllegalStateException::class.java.name -> {
            Toast.makeText(this, R.string.message_noting_found, Toast.LENGTH_SHORT).show()
        }
    }
}

fun Fragment.FOC(message: String) {
    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.FOC(@StringRes res: Int) {
    Toast.makeText(requireContext(), getString(res), Toast.LENGTH_SHORT).show()
}
