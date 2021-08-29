package com.iraqsoft.mawgood.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iraqsoft.mawgood.db.model.Branch
import com.iraqsoft.mawgood.db.model.EmpNeedsToBeSynced
import com.iraqsoft.mawgood.repository.MainFragmentRepositoryInterface
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragmentViewModel(private val mainRepo: MainFragmentRepositoryInterface):ViewModel() {


     val enteredCode= MutableLiveData<String>()



    private val _cachedBranches= MutableLiveData<MutableList<Branch>>()
    val cachedBranches: LiveData<MutableList<Branch>> get()= _cachedBranches



    private val _branches= MutableLiveData<MutableList<Branch>>()
    val branches: LiveData<MutableList<Branch>> get()= _branches


    private val _oldPositionSelectedBranch= MutableLiveData<Int>()
    val oldPositionSelectedBranch: LiveData<Int> get()= _oldPositionSelectedBranch



    private val _selectedBranches= MutableLiveData<MutableList<Branch>>()
   val selectedBranches: LiveData<MutableList<Branch>> get()= _selectedBranches


    private val _empNeedsToBeSynced = MutableLiveData<MutableList<EmpNeedsToBeSynced>>()
    val empNeedsToBeSynced: LiveData<MutableList<EmpNeedsToBeSynced>> get()= _empNeedsToBeSynced

    private val _error= MutableLiveData<String>()
    val error: LiveData<String> get()= _error



  fun getCachedBranches()
    {
        viewModelScope.launch(IO) {
           val cachedBranches =  mainRepo.getSelectedBranches()
            withContext(Main){
                _cachedBranches.value = cachedBranches
            }
        }
    }

    fun setCacheBranches()
    {
            viewModelScope.launch(IO) {
                withContext(Main){
                    _cachedBranches.value?.get(0)?.code = enteredCode.value
                }
                mainRepo.addSelectedBranches(selectedBranches = _cachedBranches.value!!)
            }.invokeOnCompletion {
                _error.postValue("no errors")
            }
    }


    fun getBranches()
    {
        viewModelScope.launch(IO)
        {
            val branchesResponse  = mainRepo.getBranches()
            withContext(Main){
                _branches.value = branchesResponse.toMutableList()
            }
        }
    }


    fun setOldSelectedBranch(position: Int)
    {
        _branches.value?.get(position)?.selected = !_branches.value?.get(position)?.selected!!
        if(position != _oldPositionSelectedBranch.value ) {
            _oldPositionSelectedBranch.value = position
        }
    }




    fun cacheSelectedBranches():Boolean
    {

        _selectedBranches.value?.add(_branches.value?.get(_oldPositionSelectedBranch.value!!)!!)
        return if(!selectedBranches.value?.isNullOrEmpty()!!){
            viewModelScope.launch(IO) {
                mainRepo.addSelectedBranches(selectedBranches = selectedBranches.value!!)
            }
            _cachedBranches.value?.clear()
            _cachedBranches.postValue(selectedBranches.value)
            true
        }else{
            _error.value = "Please selected branch"
            false
        }

    }


    fun getEmployeesNeedsToBeSynced()
    {
        viewModelScope.launch(IO) {
            _empNeedsToBeSynced.postValue(mainRepo.getEmployeesNeedsToBeSynced().toMutableList())
        }
    }



init {
    _oldPositionSelectedBranch.value = -1
    _selectedBranches.value = mutableListOf()
    _branches.value = arrayListOf()
    getCachedBranches()
}


}