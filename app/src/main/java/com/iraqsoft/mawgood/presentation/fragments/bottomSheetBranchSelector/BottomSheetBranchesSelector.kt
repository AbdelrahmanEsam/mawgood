package com.iraqsoft.mawgood.presentation.fragments.bottomSheetBranchSelector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.dialogfragment.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.databinding.BottomSheetBranchesSelectorBinding
import com.iraqsoft.mawgood.util.SpacesItemDecoration
import com.iraqsoft.mawgood.util.toast
import com.iraqsoft.mawgood.presentation.activities.mainActivity.MainActivity


class BottomSheetBranchesSelector() : BottomSheetDialogFragment(), BranchesAdapter.OnBranchListener{

    private val binding: BottomSheetBranchesSelectorBinding by viewBinding()
    private lateinit var branchesAdapter: BranchesAdapter

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
          val cached =   (requireActivity() as MainActivity).mainViewModel.cacheSelectedBranches()
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
        (requireActivity() as MainActivity).mainViewModel.error.observe(viewLifecycleOwner) {
            toast(requireContext(), it)
        }

    }

    private fun getBranchesFromDataBase()
    {
        (requireActivity() as MainActivity).mainViewModel.getBranches()
    }

    private fun branchesObserver()
    {
        (requireActivity() as MainActivity).mainViewModel.branches.observe(viewLifecycleOwner) {
            branchesAdapter = BranchesAdapter(requireContext(), this)
            branchesAdapter.setDataAdapter(it)
            branchesRecycler()
        }
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

        if( (requireActivity() as MainActivity).mainViewModel.oldPositionSelectedBranch.value!! != -1) {
            (requireActivity() as MainActivity).mainViewModel.branches.value?.get((requireActivity() as MainActivity).mainViewModel.oldPositionSelectedBranch.value!!)!!.selected = false
            branchesAdapter.notifyItemChanged((requireActivity() as MainActivity).mainViewModel.oldPositionSelectedBranch.value!!)
        }

      (requireActivity() as MainActivity).mainViewModel.setOldSelectedBranch(position)
        branchesAdapter.notifyItemChanged(position)


    }
}