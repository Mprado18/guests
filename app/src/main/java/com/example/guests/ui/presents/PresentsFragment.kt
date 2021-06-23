package com.example.guests.ui.presents

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guests.constants.GuestConstants
import com.example.guests.databinding.FragmentPresentsBinding
import com.example.guests.ui.all_guests.GuestAdapter
import com.example.guests.ui.view_model.GuestsViewModel
import com.example.guests.ui.all_guests.listener.GuestListener
import com.example.guests.ui.guests.GuestFormActivity

class PresentsFragment : Fragment() {

    private lateinit var mGuestsViewModel: GuestsViewModel

    private var _binding: FragmentPresentsBinding? = null
    private val binding get() = _binding!!

    private val mAdapter: GuestAdapter = GuestAdapter()

    private lateinit var mListener: GuestListener

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        mGuestsViewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)

        _binding = FragmentPresentsBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerAllGuests = binding.recyclerPresents
        recyclerAllGuests.layoutManager = LinearLayoutManager(context)
        recyclerAllGuests.adapter = mAdapter

        mGuestsViewModel.loadGuests(GuestConstants.FILTER.PRESENT)

        //funções de click nos convidados, ver situação e deletar da lista e bundle para armazenar estado
        mListener = object : GuestListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(GuestConstants.GUESTID, id)

                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mGuestsViewModel.delete(id)
                mGuestsViewModel.loadGuests(GuestConstants.FILTER.PRESENT)
            }
        }

        mAdapter.attachListener(mListener)

        observerGuestList()

        return view
    }

    override fun onResume() {
        super.onResume()
        mGuestsViewModel.loadGuests(GuestConstants.FILTER.PRESENT)
    }

    private fun observerGuestList() {
        mGuestsViewModel.guestList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateGuests(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}