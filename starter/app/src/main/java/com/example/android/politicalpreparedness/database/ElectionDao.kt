package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    //TODO: Add insert query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowedElection(election: Election)

    //TODO: Add select all election query
    @Query("SELECT * FROM election_table")
    fun getFollowedElectionList(): LiveData<List<Election>>

    //TODO: Add select single election query
    @Query("SELECT * FROM election_table WHERE id = :electionId" )
    suspend fun getFollowedElectionById(electionId: Int) : Election?

    @Query("SELECT CASE id WHEN NULL THEN 0 ELSE 1 END FROM election_table WHERE id = :electionId")
    fun isFollowedElection(electionId: Int): LiveData<Int>

    //TODO: Add delete query
    @Query("DELETE FROM election_table WHERE id = :electionId")
    suspend fun deleteFollowedElectionById(electionId: Int)

    //TODO: Add clear query
    @Query("DELETE FROM election_table")
    suspend fun clearFollowedElections()

}