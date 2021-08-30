package com.iraqsoft.mawgood.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.util.CheckConnection
import com.iraqsoft.mawgood.util.toast
import com.iraqsoft.mawgood.viewmodels.MainFragmentViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity2 : AppCompatActivity() {
    val mainViewModel by viewModel<MainFragmentViewModel>()
    private lateinit var checkConnection:CheckConnection
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        connectionObserver()



    }
    private fun connectionObserver()
    {
        mainViewModel.getEmployeesNeedsToBeSynced()
       checkConnection = CheckConnection(application)
        checkConnection.observe(this,{
            mainViewModel.syncNeedToBeCachedEmployees()
        })
    }




}