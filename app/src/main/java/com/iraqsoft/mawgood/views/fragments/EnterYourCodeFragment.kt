package com.iraqsoft.mawgood.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.databinding.FragmentEnterYourCodeBinding
import com.iraqsoft.mawgood.util.SpacesItemDecoration
import com.iraqsoft.mawgood.util.toast
import com.iraqsoft.mawgood.views.activities.MainActivity2
import com.iraqsoft.mawgood.views.adapters.EmployeesNeedsToBeSyncedAdapter

class EnterYourCodeFragment : Fragment() {

    private lateinit var  binding : FragmentEnterYourCodeBinding
    private lateinit var employeeAdapter: EmployeesNeedsToBeSyncedAdapter
    private lateinit var nav: NavController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_enter_your_code, container, false)
        binding.viewModel = (requireActivity() as MainActivity2).mainViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(view)
        setNeedToBeSyncedRecycler()
        buttonsListeners()
        needToBeSyncedObserver()


    }



    private fun needToBeSyncedObserver()
    {
        (requireActivity() as MainActivity2).mainViewModel.empNeedsToBeSynced.observe(viewLifecycleOwner,{

            if (it.isEmpty()){
                binding.circularProgressIndicator.visibility = View.GONE
            }
            employeeAdapter.setDataAdapter(it)
            binding.syncNeededEmployees.adapter = employeeAdapter
        })

    }

    private fun setNeedToBeSyncedRecycler()
    {employeeAdapter= EmployeesNeedsToBeSyncedAdapter(requireContext())
        val linear = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.syncNeededEmployees.apply {
            layoutManager = linear
            addItemDecoration(SpacesItemDecoration(20))
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as MainActivity2).mainViewModel.enteredCode.value = ""
    }

    private fun cacheButtonListener()
    {
        (requireActivity() as MainActivity2).mainViewModel.getCachedBranches()
        binding.cacheButton.setOnClickListener {
            if(  (requireActivity() as MainActivity2).mainViewModel.enteredCode.value.isNullOrEmpty()){
                toast(requireContext(),"")
            }else{
                if (  (requireActivity() as MainActivity2).mainViewModel.cachedBranches.value?.get(0)?.code.isNullOrEmpty()){
                     (requireActivity() as MainActivity2).mainViewModel.setCacheBranches()
                    (requireActivity() as MainActivity2).mainViewModel.error.observe(viewLifecycleOwner,{
                        if (it == "no errors") {
                            requireActivity().onBackPressed()
                        }
                    })
                    // to do
                    toast(requireContext(),"code added")
                }else{
                    if (  (requireActivity() as MainActivity2).mainViewModel.cachedBranches.value?.get(0)?.code
                        ==(requireActivity() as MainActivity2).mainViewModel.enteredCode.value){
                        (requireActivity() as MainActivity2).mainViewModel.getEmployeesNeedsToBeSynced()
                         binding.employees.visibility = View.VISIBLE
                        binding.sync.visibility  = View.VISIBLE
                        binding.syncNeededEmployees.visibility= View.VISIBLE
                    }else{

                        // to do
                        toast(requireContext(),"wrong password")
                    }

                }
            }


        }
    }

    private fun buttonsListeners()
    {
        employeesButtonListener()
        syncButtonListener()
        cacheButtonListener()
        backListener()
    }

    private fun backListener()
    {
        binding.backImageView.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun employeesButtonListener()
    {
        binding.employees.setOnClickListener {
            nav.navigate(R.id.action_enterYourCodeFragment_to_defineEmployeeFragment)
        }
    }

    private fun syncButtonListener()
    {
        binding.sync.setOnClickListener {
            binding.circularProgressIndicator.visibility = View.VISIBLE
           (requireActivity() as MainActivity2).mainViewModel.syncNeedToBeCachedEmployees()
        }
    }







}