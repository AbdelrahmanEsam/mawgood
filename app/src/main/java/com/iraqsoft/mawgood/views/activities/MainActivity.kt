package com.iraqsoft.mawgood.views.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.util.replaceFragment
import com.iraqsoft.mawgood.viewmodels.FingerPrintViewModel
import com.iraqsoft.mawgood.views.fragments.FingerPrintFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment()
    }

    private fun addFragment() {
        /* Display First Fragment initially */
        replaceFragment(FingerPrintFragment(),
            R.id.container
        )
    }

}