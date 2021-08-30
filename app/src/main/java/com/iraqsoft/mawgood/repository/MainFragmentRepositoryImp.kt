package com.iraqsoft.mawgood.repository
import com.iraqsoft.mawgood.ApiProvider
import com.iraqsoft.mawgood.db.EmpNeedsToBeSyncedDao
import com.iraqsoft.mawgood.db.SelectedBranchesDao
import com.iraqsoft.mawgood.db.UserDao
import com.iraqsoft.mawgood.db.model.Branch
import com.iraqsoft.mawgood.db.model.CheckInAndOutResponse
import com.iraqsoft.mawgood.db.model.EmpNeedsToBeSynced
import retrofit2.Response

class MainFragmentRepositoryImp(private val api:ApiProvider,private val userDao : UserDao,private val dao : SelectedBranchesDao,private val empNeedToBeSyncedDao: EmpNeedsToBeSyncedDao) :MainFragmentRepositoryInterface {
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