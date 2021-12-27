package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.models.Election

class ElectionsFragment : Fragment() {

    //TODO: Declare ViewModel

    private val viewModel: ElectionsViewModel by lazy {
        ViewModelProvider(this).get(ElectionsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentElectionBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.upcomingElectionsRecyclerview.adapter =
            ElectionListAdapter(ElectionListAdapter.ElectionListener {
                viewModel.displayElectionDetails(it)
            })

        binding.savedElectionsRecyclerview.adapter =
            ElectionListAdapter(ElectionListAdapter.ElectionListener {
                viewModel.displayElectionDetails(it)
            })

        viewModel.navigateToSelectedElection.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(it)
                )
                viewModel.displayElectionDetailsComplete()
            }
        })

        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters

        //TODO: Populate recycler adapters

        return binding.root
    }

    //TODO: Refresh adapters when fragment loads

}

@BindingAdapter("electionsList")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Election>?){
    val adapter = recyclerView.adapter as ElectionListAdapter
    adapter.submitList(data)
}