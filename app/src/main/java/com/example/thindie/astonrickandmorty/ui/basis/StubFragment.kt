package com.example.thindie.astonrickandmorty.ui.basis

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.ui.uiutils.rotate
import com.example.thindie.astonrickandmorty.ui.uiutils.widGet

class StubFragment : Fragment(R.layout.fragment_stub) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val underConstruction = view.widGet<ImageView>(R.id.image_under_construction)
        rotate(underConstruction)
    }
}
