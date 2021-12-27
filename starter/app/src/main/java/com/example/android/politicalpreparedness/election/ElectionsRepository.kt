package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionsRepository (private val database: ElectionDatabase) {

    // load followed elections
    val followedElections: LiveData<List<Election>> = database.electionDao.getFollowedElectionList()

    suspend fun followElection(election: Election){
        withContext(Dispatchers.IO){
            database.electionDao.insertFollowedElection(election)
        }
    }

    suspend fun unfollowElection(election: Election){
        withContext(Dispatchers.IO){
            database.electionDao.deleteFollowedElectionById(election.id)
        }
    }

}