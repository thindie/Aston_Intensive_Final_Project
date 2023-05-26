package com.example.thindie.astonrickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesFragment

class MainActivity : AppCompatActivity(), FragmentsRouter {

    override val router: Router by lazy {
        Router.inject(this, R.id.activity_fragment_container, PersonagesFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router.navigate()

    }
}