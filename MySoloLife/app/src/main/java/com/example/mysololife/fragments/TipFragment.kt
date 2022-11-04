package com.example.mysololife.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mysololife.R
import com.example.mysololife.contentsList.ContentsListActivity
import com.example.mysololife.databinding.FragmentHomeBinding
import com.example.mysololife.databinding.FragmentTipBinding


class TipFragment : Fragment() {
    private lateinit var binding: FragmentTipBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tip, container, false)

        binding.category1.setOnClickListener{

            val intent = Intent(context,ContentsListActivity::class.java)
            intent.putExtra("category", "category1")

            startActivity(intent)
        }
        binding.category2.setOnClickListener{

            val intent = Intent(context,ContentsListActivity::class.java)
            intent.putExtra("category", "category2")
            startActivity(intent)
        }

        binding.homeTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_tipFragment2_to_homeFragment)
        }
        binding.storeTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_tipFragment2_to_storeFragment)
        }
        binding.talkTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_tipFragment2_to_talkFragment)
        }
        binding.bookmarkTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_tipFragment2_to_bookmarkFragment)
        }
        return binding.root
    }



}