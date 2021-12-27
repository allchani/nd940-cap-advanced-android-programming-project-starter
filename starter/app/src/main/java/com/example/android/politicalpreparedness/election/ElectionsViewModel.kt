package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.ElectionDatabase.Companion.getInstance
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(application: Application): AndroidViewModel(application) {

    private val electionDatabase = getInstance(application)
    private val electionsRepository = ElectionsRepository(electionDatabase)

    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    val followedElections: LiveData<List<Election>>
        get() = electionsRepository.followedElections

    private val _navigateToSelectedElection = MutableLiveData<Election?>()
    val navigateToSelectedElection: LiveData<Election?>
        get() = _navigateToSelectedElection

    init {
        viewModelScope.launch{
            try{
              _upcomingElections.value = CivicsApi.retrofitService.getElections().elections
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun displayElectionDetails(election: Election) {
        _navigateToSelectedElection.value = election
    }

    fun displayElectionDetailsComplete() {
        _navigateToSelectedElection.value = null
    }

    //TODO: Create live data val for upcoming elections

    //TODO: Create live data val for saved elections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info

}