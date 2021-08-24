package com.iraqsoft.mawgood.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.dialogfragment.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.databinding.BottomSheetBranchesSelectorBinding
import com.iraqsoft.mawgood.databinding.FragmentEnterYourCodeBinding
import com.iraqsoft.mawgood.util.SpacesItemDecoration
import com.iraqsoft.mawgood.util.toast
import com.iraqsoft.mawgood.views.activities.MainActivity2
import com.iraqsoft.mawgood.views.adapters.BranchesAdapter


class BottomSheetBranchesSelector() : BottomSheetDialogFragment(),BranchesAdapter.OnBranchListener{

    private val binding: BottomSheetBranchesSelectorBinding by viewBinding()
    private lateinit var branchesAdapter:BranchesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.bottomSheetTheme)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBranchesFromDataBase()
        branchesObserver()
        errorObserver()
        close()

        binding.confirmedButton.setOnClickListener {
          val cached =   (requireActivity() as MainActivity2).mainViewModel.cacheSelectedBranches()
            if (cached) {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun close()
    {

        binding.closeImageView.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }


    private fun errorObserver()
    {
        (requireActivity() as MainActivity2).mainViewModel.error.observe(viewLifecycleOwner,{
            toast(requireContext(),it)
        })

    }

    private fun getBranchesFromDataBase()
    {
        (requireActivity() as MainActivity2).mainViewModel.getBranches()
    }

    private fun branchesObserver()
    {
        (requireActivity() as MainActivity2).mainViewModel.branches.observe(viewLifecycleOwner,{
            branchesAdapter= BranchesAdapter(requireContext(),this)
            branchesAdapter.setDataAdapter(it)
            branchesRecycler()
        })
    }

    private fun branchesRecycler() {
        val linear = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.branchesRecyclerView.apply {
            layoutManager = linear
          adapter = branchesAdapter
            addItemDecoration(SpacesItemDecoration(10))
        }

    }

    override fun onBranchListener(position: Int) {

        if( (requireActivity() as MainActivity2).mainViewModel.oldPositionSelectedBranch.value!! != -1) {
            (requireActivity() as MainActivity2).mainViewModel.branches.value?.get((requireActivity() as MainActivity2).mainViewModel.oldPositionSelectedBranch.value!!)!!.selected = false
            branchesAdapter.notifyItemChanged((requireActivity() as MainActivity2).mainViewModel.oldPositionSelectedBranch.value!!)
        }

      (requireActivity() as MainActivity2).mainViewModel.setOldSelectedBranch(position)
        branchesAdapter.notifyItemChanged(position)


    }
}