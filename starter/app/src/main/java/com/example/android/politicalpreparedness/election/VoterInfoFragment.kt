package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding

class VoterInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentVoterInfoBinding.inflate(inflater)
        val selectedElection = VoterInfoFragmentArgs.fromBundle(requireArguments()).argElection
        val viewModel = ViewModelProvider(this, VoterInfoViewModelFactory(requireActivity().application,selectedElection)).get(VoterInfoViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.election = selectedElection

        viewModel.url.observe(viewLifecycleOwner, Observer{
            it?.let{
                loadUrl(it)
                //viewModel.setUrl(null)
            }

        })


        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */


        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks

        return binding.root
    }

    //TODO: Create method to load URL intents
    private fun loadUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

}