package com.iraqsoft.mawgood.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.databinding.FragmentDefineEmpolyeeBinding
import com.iraqsoft.mawgood.util.SpacesItemDecoration
import com.iraqsoft.mawgood.viewmodels.DefineEmployeeViewModel
import com.iraqsoft.mawgood.views.adapters.EmployeesAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class DefineEmployeeFragment : Fragment(R.layout.fragment_define_empolyee) ,EmployeesAdapter.OnEmployeeListener{

    private lateinit var  binding : FragmentDefineEmpolyeeBinding
    private   val defineViewModel by viewModel<DefineEmployeeViewModel>()
    private lateinit var employeeAdapter: EmployeesAdapter
    private lateinit var nav: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_define_empolyee, container, false)
        binding.viewModel = defineViewModel
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(view)
        employeesAdapter()
        branchesObserver()
        queryObserver()
        back()
        listenToFragmentCallBack()


    }

    private fun listenToFragmentCallBack()
    {
        nav.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("key")?.observe(
            viewLifecycleOwner) {
            setSelectedEmpToDefault()
        }
    }

    private fun setSelectedEmpToDefault()
    {
     if (defineViewModel.oldEmployee.value != -1){
         defineViewModel.oldEmployee.value?.let { employeeAdapter.setSelectedItemToDefault(it) }
     }
    }


    private fun branchesObserver()
    {
        defineViewModel.searchedList.observe(viewLifecycleOwner,{
            employeeAdapter.setDataAdapter(it)
            binding.employeeRecyclerView.adapter = employeeAdapter
        })
    }


    private fun employeesAdapter() {
        employeeAdapter= EmployeesAdapter(requireContext(),this)
        val linear = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.employeeRecyclerView.apply {
            layoutManager = linear
            addItemDecoration(SpacesItemDecoration(20))
        }

    }

    private fun queryObserver()
    {
       defineViewModel.query.observe(viewLifecycleOwner,{
           if (!it.isNullOrBlank()) {
               defineViewModel.searchEmployeeList(it)
               employeeAdapter.notifyDataSetChanged()
           }else{
               defineViewModel.setDefaultSearchedList()
           }
       })
    }

    override fun onEmployeeListener(position: Int) {
        if( defineViewModel.oldEmployee.value != -1) {
            defineViewModel.employees.value?.get(defineViewModel.oldEmployee.value!!)?.selected   = false
            employeeAdapter.notifyItemChanged(defineViewModel.oldEmployee.value!!)
        }
        defineViewModel.setOldEmployee(position)
        employeeAdapter.notifyItemChanged(position)
        val list = defineViewModel.employees.value
        nav.navigate(DefineEmployeeFragmentDirections.actionDefineEmployeeFragmentToPrintBottomSheet(
            list!! ,position))


    }
    private fun back()
    {
        binding.backImageView.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }



}