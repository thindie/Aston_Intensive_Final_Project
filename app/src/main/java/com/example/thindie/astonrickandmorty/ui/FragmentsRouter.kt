package com.example.thindie.astonrickandmorty.ui

import androidx.fragment.app.Fragment

interface FragmentsRouter {
    fun navigate(fragment: Fragment?, isAddToBackStack: Boolean)
    fun restore(fragment: Class<out Fragment>?, isAddToBackStack: Boolean)
}
