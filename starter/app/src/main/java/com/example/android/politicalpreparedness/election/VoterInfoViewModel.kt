package com.example.android.politicalpreparedness.election

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase.Companion.getInstance
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VoterInfoViewModel(application: Application, val election: Election) : AndroidViewModel(application) {

    // Get voter info
    private val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val voterInfo: LiveData<VoterInfoResponse>
        get() = _voterInfo

    private val state = election.division?.state
    private val country = election.division?.country
    private val id = election.id
    private var stateAndCountry = ""

    private fun setStateAndCountry() {
        if(state != "") {
            stateAndCountry += state
        }

        if(country != "") {
            if(stateAndCountry != "") {
                stateAndCountry += ", "
            }
            stateAndCountry += country
        }
    }

    private suspend fun getVoterInfoFromCivicsApi() = withContext(Dispatchers.IO) {
        try {
            _voterInfo.value = CivicsApi.retrofitService.getVoterInfo(stateAndCountry, id)

        } catch (e: Exception) {
            Log.d(TAG, e.printStackTrace().toString())
        }
    }

    // initialization
    init {
        setStateAndCountry()
        viewModelScope.launch {
            getVoterInfoFromCivicsApi()
        }
    }

    // check follow or not
    private val electionDatabase = getInstance(application)
    private val electionsRepository = ElectionsRepository(electionDatabase)

    private val _isFollowedElection: LiveData<Int>
        get() = electionDatabase.electionDao.isFollowedElection(id)

    val isFollowedElection = Transformations.map(_isFollowedElection) { followedOrNot ->
        followedOrNot?.let {
            followedOrNot == 1
        }
    }

    fun onFollowButtonClick() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (isFollowedElection.value == true) {
                    electionsRepository.unfollowElection(election)
                } else {
                    electionsRepository.followElection(election)
                }
            }
        }
    }

    // Set url
    private val _url = MutableLiveData<String?>()
    val url: LiveData<String?>
        get() = _url

    fun setUrl(url: String?) {
        _url.value = url
    }


    //TODO: Add live data to hold voter info

    //TODO: Add var and methods to populate voter info

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}