package com.muhammedkursatgokgun.cards.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhammedkursatgokgun.cards.databinding.RecyclerRowBinding
import com.muhammedkursatgokgun.cards.model.Word

class RecyclerViewListAdapter(var wordList : List<Word>) : RecyclerView.Adapter<RecyclerViewListAdapter.WordHolder>() {
    class WordHolder(var binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WordHolder(binding)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        holder.binding.englishWord.text = wordList.get(position).englishW
        holder.binding.turkishWord.text = wordList.get(position).turkW
    }
}