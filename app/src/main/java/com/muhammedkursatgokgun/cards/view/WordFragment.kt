package com.muhammedkursatgokgun.cards.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.muhammedkursatgokgun.cards.R
import com.muhammedkursatgokgun.cards.databinding.FragmentEnterBinding
import com.muhammedkursatgokgun.cards.databinding.FragmentWordBinding
import com.muhammedkursatgokgun.cards.model.Word
import com.muhammedkursatgokgun.cards.roomdb.WordDb
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

private var myDisposable= CompositeDisposable()
private lateinit var wordListFromDB : List<Word>

private var _binding: FragmentWordBinding? = null

// This property is only valid between onCreateView and
// onDestroyView.
private val binding get() = _binding!!
class WordFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
        //return inflater.inflate(R.layout.fragment_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var db = Room.databaseBuilder(
            requireContext(), WordDb::class.java,"Word"
        ).build()
        val wordDao = db.wordDao()

        myDisposable.add(wordDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse)
        )

        }
    private fun handleResponse(wordList: List<Word>){
        wordListFromDB = wordList
        if(wordListFromDB[0].englishW.isNotEmpty()){
            binding.textviewWord.setText(wordListFromDB[0].englishW)
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        myDisposable.clear()
    }

}

