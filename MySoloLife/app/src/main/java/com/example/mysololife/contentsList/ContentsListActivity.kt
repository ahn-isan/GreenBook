package com.example.mysololife.contentsList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysololife.R
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ContentsListActivity : AppCompatActivity() {

    lateinit var myRef : DatabaseReference

    val bookmarkIdList = mutableListOf<String>()

    lateinit var rvAdapter: ContentRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents_list)

        val items = ArrayList<ContentModel>()

//        FBRef.category1.push().setValue(
//            ContentModel("마음챙김의 시","https://image.yes24.com/goods/92462696/L","http://www.yes24.com/Product/Goods/92462696","11,700원","시를 읽는 것은 자기 자신으로 돌아오는 것이고, 세상을 경이롭게 여기는 것이며, 여러 색의 감정을 경험하는 것이다. 살아온 날들이 살아갈 날들에게 묻는다.")
//        )
//        FBRef.category1.push().setValue(
//            ContentModel("윤동주 전 시집 하늘과 바람과 별과 시","http://image.yes24.com/goods/107487179/XL","http://www.yes24.com/Product/Goods/107487179","13,500원","시집 『그대가 곁에 있어도 나는 그대가 그립다』 『외눈박이 물고기의 사랑』 『나의 상처는 돌 너의 상처는 꽃』으로 수많은 독자의 사랑을 받는 한편, 엮은 시집 『지금 알고 있는 걸 그때도 알았더라면』")
//        )
//        FBRef.category1.push().setValue(
//            ContentModel(" 꽃샘바람에 흔들린다면 너는 꽃","http://image.yes24.com/goods/108590831/XL","http://www.yes24.com/Product/Goods/108590831","12,150원","섬세한 언어 감각과 서정성 -\n" +
//                    "삶 속에서 심호흡이 필요할 때\n" +
//                    "가슴으로 암송하는 시들")
//        )
//        FBRef.category1.push().setValue(
//            ContentModel("자기돌봄의 시","http://image.yes24.com/goods/109888114/XL","http://www.yes24.com/Product/Goods/109888114","14,220원","내가 나를 사랑하며 돌볼 때 비로소 나의 인생이 시작된다\n" +
//                    "풀꽃 시인 나태주가 권하는 자기돌봄의 시")
//        )
//        FBRef.category1.push().setValue(
//            ContentModel("사랑하라 한번도 상처받지 않은 것처럼","http://image.yes24.com/momo/TopCate48/MidCate04/4737610.jpg","http://www.yes24.com/Product/Goods/1472519","9,000원","사랑하라, 한번도 상처받지 않은 것처럼\n" +
//                    "춤추라, 아무도 바라보고 있지 않은 것처럼.\n" +
//                    "사랑하라, 한번도 상처받지 않은 것처럼.")
//        )
//        FBRef.category1.push().setValue(
//            ContentModel("600그램의 詩","http://image.yes24.com/goods/115143013/XL","http://www.yes24.com/Product/Goods/115143013","10,800원","음미할수록 깊은 맛이 나는 명시 79편,\n" +
//                    "캘리그라피와 함께하는 감성 라이팅북")
//        )
//        FBRef.category1.push().setValue(
//            ContentModel("매일, 시 한 잔","http://image.yes24.com/goods/71050635/XL","http://www.yes24.com/Product/Goods/71050635","11,700원","나를 위해 준비하는,\n" +
//                    "매일 매일의 따뜻한 시(詩) 한 잔")
//        )

        val itemKeyList = ArrayList<String>()

        rvAdapter = ContentRVAdapter(baseContext, items, itemKeyList, bookmarkIdList)

        // Write a message to the database
        val database = Firebase.database

        val category = intent.getStringExtra("category")

        if(category == "category1") {

            myRef = database.getReference("contents")

        } else if(category == "category2") {

            myRef = database.getReference("contents2")

        }

        //컨텐츠 가져오기
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children) {
                    Log.d("ContentListActivity", dataModel.toString())
                    Log.d("ContentListActivity", dataModel.key.toString())
                    val item = dataModel.getValue(ContentModel::class.java)
                    items.add(item!!)
                    itemKeyList.add(dataModel.key.toString())

                }
                rvAdapter.notifyDataSetChanged()
                Log.d("ContentListActivity", items.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        myRef.addValueEventListener(postListener)

        val rv : RecyclerView = findViewById(R.id.rv)

        rv.adapter = rvAdapter

        rv.layoutManager = GridLayoutManager(this, 1)

        getBookmarkData()
    }


    private fun getBookmarkData() {

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                bookmarkIdList.clear()

                for (dataModel in dataSnapshot.children) {
                    bookmarkIdList.add(dataModel.key.toString())
                }
                Log.d("Bookmark : ", bookmarkIdList.toString())
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)


    }
}