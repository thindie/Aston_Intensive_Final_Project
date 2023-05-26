package com.example.thindie.astonrickandmorty.ui.basis

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.ui.MainActivity
import com.example.thindie.astonrickandmorty.ui.uiutils.beatMotion
import com.example.thindie.astonrickandmorty.ui.uiutils.widGet

class BaddestResultFragment : Fragment(R.layout.fragment_baddest_result) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val heart = view.widGet<ImageView>(R.id.image_heart)
        beatMotion(heart)

        view.widGet<Button>(R.id.baddest_result_finish_activity).setOnClickListener {
            requireActivity().finish()
        }
        view.widGet<Button>(R.id.baddest_result_retry).setOnClickListener {
            if (requireActivity()::class.java.isAssignableFrom(MainActivity::class.java)) {
                val activity = requireActivity() as MainActivity
                activity.navigate(null, false)
            }
        }
    }
}
