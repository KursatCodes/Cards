package com.muhammedkursatgokgun.cards.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.muhammedkursatgokgun.cards.adapter.RecyclerViewListAdapter
import com.muhammedkursatgokgun.cards.databinding.FragmentListBinding
import com.muhammedkursatgokgun.cards.model.Word
import com.muhammedkursatgokgun.cards.roomdb.WordDao
import com.muhammedkursatgokgun.cards.roomdb.WordDb
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

private lateinit var myAdapter : RecyclerViewListAdapter
private lateinit var db : WordDb
private lateinit var myDao : WordDao
private lateinit var myWordList : List<Word>

private var myDisposable = CompositeDisposable()
private var _binding: FragmentListBinding? = null
private val binding get() = _binding!!

class ListFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(requireContext(),WordDb::class.java,"Word").build()
        myDao = db.wordDao()




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
        myDisposable.add(myDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse)
        )
    }
    private fun handleResponse(wordList : List<Word>){
        binding.recyclerViewList.layoutManager = LinearLayoutManager(requireContext())
        myAdapter = RecyclerViewListAdapter(wordList)
        binding.recyclerViewList.adapter = myAdapter
    }

}