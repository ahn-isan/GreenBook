package com.example.mysololife.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mysololife.R
import com.example.mysololife.board.BoardInsideActivity
import com.example.mysololife.board.BoardListLVAdater
import com.example.mysololife.board.BoardModel
import com.example.mysololife.board.BoardWriteActivity
import com.example.mysololife.contentsList.ContentModel
import com.example.mysololife.databinding.FragmentHomeBinding
import com.example.mysololife.databinding.FragmentTalkBinding
import com.example.mysololife.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class TalkFragment : Fragment() {

    private lateinit var binding: FragmentTalkBinding

    private val TAG = TalkFragment::class.java.simpleName

    private val boardDataList = mutableListOf<BoardModel>()
    private val boardKeyList = mutableListOf<String>()

    private lateinit var boardRVAdapter : BoardListLVAdater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)

        boardRVAdapter = BoardListLVAdater(boardDataList)

        binding.boardListView.adapter = boardRVAdapter

        binding.boardListView.setOnItemClickListener { parent, view, position, id ->
//            intent.putExtra("title",boardDataList[position].title)
//            intent.putExtra("content",boardDataList[position].content)
//            intent.putExtra("time",boardDataList[position].time)
//            startActivity(intent)

            val intent = Intent(context, BoardInsideActivity::class.java)
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)
        }
        // listview??? ?????? ????????? title content time ??? ?????? ??????????????? ??????????????? ?????????

        // Firebase??? ?????? board??? ?????? ???????????? id??? ???????????? ?????? ???????????? ???????????? ?????????

        binding.writeBtn.setOnClickListener{
            val intent = Intent(context, BoardWriteActivity::class.java)
            startActivity(intent)
        }
        binding.homeTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)
        }
        binding.bookmarkTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_talkFragment_to_bookmarkFragment)
        }
        binding.tipTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_talkFragment_to_tipFragment2)
        }
        binding.storeTap.setOnClickListener{
            it.findNavController().navigate(R.id.action_talkFragment_to_storeFragment)
        }

        getFBBoardData()

        return binding.root
    }

    private fun getFBBoardData(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                boardDataList.clear()

                for (dataModel in dataSnapshot.children) {

                    Log.d(TAG, dataModel.toString())
//                    dataModel.key

                    val item = dataModel.getValue(BoardModel::class.java)
                    boardDataList.add(item!!)
                    boardKeyList.add(dataModel.key.toString())
                }
                boardKeyList.reverse()
                boardDataList.reverse()
                boardRVAdapter.notifyDataSetChanged()

                Log.d(TAG, boardDataList.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        FBRef.boardRef.addValueEventListener(postListener)
    }

}