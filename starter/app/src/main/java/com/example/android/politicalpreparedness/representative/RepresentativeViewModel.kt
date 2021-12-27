package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch

class RepresentativeViewModel: ViewModel() {

    //TODO: Establish live data for representatives and address
    private val _representativesList = MutableLiveData<List<Representative>>()
    val representativesList: LiveData<List<Representative>>
        get()=_representativesList

    private val address = MutableLiveData<Address?>()
    val addressLine1 = MutableLiveData<String>()
    val addressLine2 = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val state = MutableLiveData<String>()
    val zip = MutableLiveData<String>()

    //TODO: Create function to fetch representatives from API from a provided address


    fun fetchRepresentatives(address: Address){
        viewModelScope.launch {
            val response = CivicsApi.retrofitService.getRepresentatives(address.toFormattedString())
            val offices = response.offices
            val officials = response.officials

            val fetchedRepresentativesList = mutableListOf<Representative>()

            offices.forEach {
                fetchedRepresentativesList.addAll(it.getRepresentatives(officials))
            }
            _representativesList.value = fetchedRepresentativesList
        }
    }

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location

    fun getAddressFromGeoLocation(address: Address) {
        this.address.value = address
        addressLine1.value = address.line1
        addressLine2.value = address.line2
        city.value = address.city
        state.value = address.state
        zip.value = address.zip

    }

    //TODO: Create function to get address from individual fields

    fun getRepresentativesFromFields() {
        val address = Address(addressLine1.value!!, addressLine2.value, city.value!!, state.value!!, zip.value!! )
        fetchRepresentatives(address)
    }

}
