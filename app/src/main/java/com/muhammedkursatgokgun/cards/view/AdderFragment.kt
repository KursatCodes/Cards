package com.muhammedkursatgokgun.cards.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.room.Room
import com.muhammedkursatgokgun.cards.databinding.FragmentAdderBinding
import com.muhammedkursatgokgun.cards.model.Word
import com.muhammedkursatgokgun.cards.roomdb.WordDao
import com.muhammedkursatgokgun.cards.roomdb.WordDb
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

private var myDisposable= CompositeDisposable()
private var _binding: FragmentAdderBinding? = null
private val binding get() = _binding!!
private var repeatWord = true
private var wordList = ArrayList<Word>()

private lateinit var wordDao: WordDao
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
        wordDao = db.wordDao()
        getirbiMutluluk()

        binding.buttonAddWord.setOnClickListener {
            val englishW = binding.editTextEnglishWord.text.toString()
            val turkW = binding.editTextTurkishWord.text.toString()
            val newWord = Word(englishW, turkW)
            repeatWord= false
            if(englishW.isNotEmpty() && turkW.isNotEmpty()) {
                for (word in wordList){
                    println("selamünaleyküm")
                    if (word.englishW == newWord.englishW){
                        repeatWord = true
                        //wordList.remove(newWord)
                        Toast.makeText(requireContext(),"Word is repeating.",Toast.LENGTH_LONG).show()
                    }
                }
                if(!repeatWord){
                    wordList.add(newWord)
                    myDisposable.add(wordDao.insert(newWord)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleResponse))
                    Toast.makeText(requireContext(),"Word is added.",Toast.LENGTH_LONG).show()
                }

            }else{
                Toast.makeText(requireContext(),"Enter Turkish and English word mean.",Toast.LENGTH_LONG).show()
            }
            //val action = AdderFragmentDirections.actionAdderFragmentToWordFragment()
            //Navigation.findNavController(it).navigate(action)
        }
    }
    private fun getirbiMutluluk() {
        myDisposable.add(wordDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse2))
    }
    private fun handleResponse2(wordlisttt : List<Word>){
        wordList = wordlisttt as ArrayList<Word>
    }
    private fun handleResponse(){
        var englishW :String = binding.editTextEnglishWord.text.toString()
        var turkW: String = binding.editTextTurkishWord.text.toString()
        var action : NavDirections = AdderFragmentDirections.actionAdderFragmentToWordFragment(englishW,turkW)
        Navigation.findNavController(requireView()).navigate(action)
//        var action = AdderFragmentDirections.actionAdderFragmentToWordFragment()
//        Navigation.findNavController(requireView()).navigate(action)
        binding.editTextEnglishWord.text.clear()
        binding.editTextTurkishWord.text.clear()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}