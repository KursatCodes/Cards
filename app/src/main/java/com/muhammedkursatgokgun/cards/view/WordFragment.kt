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
import com.muhammedkursatgokgun.cards.roomdb.WordDao
import com.muhammedkursatgokgun.cards.roomdb.WordDb
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

private var myDisposable= CompositeDisposable()
private lateinit var wordListFromDB : List<Word>
private lateinit var wordDao : WordDao


private var _binding: FragmentWordBinding? = null
private var showingWordId :Int =0

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
        ).allowMainThreadQueries().build()
        wordDao = db.wordDao()

        myDisposable.add(wordDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse)
        )
        binding.buttonNext.setOnClickListener {
            println("selam 1")
                println("selam 2")
            if(wordListFromDB.isNotEmpty()){
                if(showingWordId+1<wordListFromDB.size){
                    println("selam 3")
                    showingWordId+=1
                    println("selam 4")
                    println(showingWordId.toString())
                    var showThis =(showingWordId+1).toString() + "- "+ wordListFromDB[showingWordId].englishW
                    println("selam 5")
                    binding.textviewWord.text = showThis
                    println("selam 6")
                }else{
                    println("selam 7")
                    showingWordId=0
                    var showThis = (showingWordId+1).toString() + "- "+ wordListFromDB[0].englishW
                    println("selam 8")
                    binding.textviewWord.text = showThis
                }
            }
        }

        binding.buttonBack.setOnClickListener {
            if(wordListFromDB.isNotEmpty()){
                if(showingWordId>0) {
                    showingWordId-=1
                    var showThis = (showingWordId+1).toString() + "- "+ wordListFromDB[showingWordId].englishW
                    binding.textviewWord.text= showThis
                }else{
                    showingWordId= wordListFromDB.size-1
                    var showThis = (showingWordId+1).toString() + "- "+ wordListFromDB[showingWordId].englishW
                    binding.textviewWord.text= showThis

                }
            }

        }
        binding.buttonDelete.setOnClickListener {
            if(wordListFromDB.isNotEmpty()){
                myDisposable.add(wordDao.delete(wordListFromDB[showingWordId])
                    .observeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponseForDelete)
                )
            }

        }

        binding.textviewWord.setOnClickListener {
            if(binding.textviewWord.text.equals((showingWordId+1).toString() + "- "+wordListFromDB[showingWordId].englishW)){
                var showThis = (showingWordId+1).toString() + "- "+ wordListFromDB[showingWordId].turkW
                    binding.textviewWord.text = showThis
            }else{
                var showThis = (showingWordId+1).toString() + "- "+ wordListFromDB[showingWordId].englishW
                binding.textviewWord.text = showThis
            }
        }
    }
    private fun handleResponseForDelete() {
        myDisposable.add(wordDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))
    }
    private fun handleResponse(wordList: List<Word>){
        wordListFromDB = wordList
        if(wordListFromDB.isNotEmpty()){
            var showThis = (showingWordId+1).toString() + "- "+ wordListFromDB[0].englishW
                binding.textviewWord.text = showThis
        }else{
            binding.textviewWord.text = "Liste Boş"
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        myDisposable.clear()
    }
}

