package com.muhammedkursatgokgun.cards.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muhammedkursatgokgun.cards.R
import com.muhammedkursatgokgun.cards.databinding.FragmentAdderBinding
import com.muhammedkursatgokgun.cards.databinding.FragmentListBinding

private var _binding: FragmentListBinding? = null
private val binding get() = _binding!!

class ListFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}