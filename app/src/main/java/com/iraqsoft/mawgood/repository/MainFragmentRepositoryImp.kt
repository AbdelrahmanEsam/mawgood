package com.iraqsoft.mawgood.repository
import com.iraqsoft.mawgood.db.SelectedBranchesDao
import com.iraqsoft.mawgood.db.UserDao
import com.iraqsoft.mawgood.db.model.Branch

class MainFragmentRepositoryImp(private val userDao : UserDao,private val dao : SelectedBranchesDao) :MainFragmentRepositoryInterface {
    override suspend fun getSelectedBranches(): MutableList<Branch> {
      return dao.getSelectedBranches()
    }

    override suspend fun addSelectedBranches(selectedBranches: MutableList<Branch>) {
        dao.addSelectedBranches(selectedBranches)
    }

    override suspend fun getBranches(): List<Branch> {
        return userDao.findOne().branches
    }


}