package com.iraqsoft.mawgood.data.repository
import com.iraqsoft.mawgood.data.remote.ApiProvider
import com.iraqsoft.mawgood.data.dataBase.EmpNeedsToBeSyncedDao
import com.iraqsoft.mawgood.data.dataBase.SelectedBranchesDao
import com.iraqsoft.mawgood.data.dataBase.UserDao
import com.iraqsoft.mawgood.data.model.Branch
import com.iraqsoft.mawgood.data.model.CheckInAndOutResponse
import com.iraqsoft.mawgood.data.model.EmpNeedsToBeSynced
import com.iraqsoft.mawgood.domain.repository.MainFragmentRepositoryInterface
import retrofit2.Response

class MainFragmentRepositoryImp(private val api: ApiProvider, private val userDao : UserDao, private val dao : SelectedBranchesDao, private val empNeedToBeSyncedDao: EmpNeedsToBeSyncedDao) :
    MainFragmentRepositoryInterface {
    override suspend fun getSelectedBranches(): MutableList<Branch> {
      return dao.getSelectedBranches()
    }

    override suspend fun addSelectedBranches(selectedBranches: MutableList<Branch>) {
        dao.addSelectedBranches(selectedBranches)
    }

    override suspend fun getBranches(): List<Branch> {
        return userDao.findOne().branches
    }

    override suspend fun getEmployeesNeedsToBeSynced(): List<EmpNeedsToBeSynced> {
        return  empNeedToBeSyncedDao.getEmployeesNeedsToBeSynced()
    }

    override suspend fun syncCachedEmployees(time:Long,empId:String): Response<CheckInAndOutResponse> {
      return  api.syncCheck(time = time,empId = empId)
    }

    override suspend fun deleteEmp(emp: EmpNeedsToBeSynced) {
        empNeedToBeSyncedDao.deleteEmp(emp)
    }


}