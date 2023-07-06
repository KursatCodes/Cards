package com.muhammedkursatgokgun.cards.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.room.Room
import com.muhammedkursatgokgun.cards.R
import com.muhammedkursatgokgun.cards.databinding.FragmentAdderBinding
import com.muhammedkursatgokgun.cards.databinding.FragmentWordBinding
import com.muhammedkursatgokgun.cards.model.Word
import com.muhammedkursatgokgun.cards.roomdb.WordDao
import com.muhammedkursatgokgun.cards.roomdb.WordDb
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

private var myDisposable= CompositeDisposable()

private var _binding: FragmentAdderBinding? = null
// This property is only valid between onCreateView and
// onDestroyView.
private val binding get() = _binding!!
private var repeatWord = true
private var wordList = ArrayList<Word>()
class AdderFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdderBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
        //return inflater.inflate(R.layout.fragment_adder, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var db = Room.databaseBuilder(
            requireContext(),WordDb::class.java,"Word"
        ).build()
        var wordDao = db.wordDao()

        binding.buttonAddWord.setOnClickListener {
            val englishW = binding.editTextEnglishWord.text.toString()
            val turkW = binding.editTextTurkishWord.text.toString()
            val newWord = Word(englishW, turkW)
            var repeatWord= false
            wordList.add(newWord)
            if(englishW.isNotEmpty() && turkW.isNotEmpty()) {
                for (word in wordList){
                    if (word.englishW.equals(newWord.englishW)){
                        repeatWord = true
                        Toast.makeText(requireContext(),"Word is repeating.",Toast.LENGTH_LONG).show()
                    }
                }
                myDisposable.add(wordDao.insert(newWord)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse))
            }else{
                Toast.makeText(requireContext(),"Enter Turkish and English word mean.",Toast.LENGTH_LONG).show()
            }
            //val action = AdderFragmentDirections.actionAdderFragmentToWordFragment()
            //Navigation.findNavController(it).navigate(action)

        }
    }
    private fun handleResponse(){
        binding.editTextEnglishWord.text.clear()
        binding.editTextTurkishWord.text.clear()
        val action = AdderFragmentDirections.actionAdderFragmentToWordFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }
    private fun handleResponseWithName(word: Word){
            Toast.makeText(requireContext(),"Word is added.",Toast.LENGTH_LONG).show()

        binding.editTextEnglishWord.text.clear()
        binding.editTextTurkishWord.text.clear()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}