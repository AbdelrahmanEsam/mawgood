package com.iraqsoft.mawgood.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.viewmodels.MainFragmentViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity2 : AppCompatActivity() {
    val mainViewModel by viewModel<MainFragmentViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }


}