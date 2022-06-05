package com.example.petsisland

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ShopFragment : Fragment() {
    var recyclerView: RecyclerView? = null
    var productList = ArrayList<Product>()
    private var database: FirebaseDatabase? = null
    private var reference: DatabaseReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_shop, container, false)

        database =
            FirebaseDatabase.getInstance("https://pets-island-default-rtdb.asia-southeast1.firebasedatabase.app")
        reference = database?.getReference("products")

        val FirebaseListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val child = snapshot.children
                child.forEach {
                    var products = Product(
                        it.child("img").value.toString(),
                        it.child("name").value.toString(),
                        it.child("price").value.toString()
                    )
                    productList.add(products)
                }




                val adapter = ProductAdapter(productList)
                recyclerView?.adapter = adapter

//for recycler view click --start



                adapter.setOnItemClickListener(object :ProductAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        val itemImg = productList[position].img
                        val itemTitle = productList[position].title
                        val itemPrice = productList[position].price
                        Toast.makeText(requireContext(),"Clicked on item no $itemTitle $itemPrice",Toast.LENGTH_SHORT).show()

                        activity?.let {
                            val intent = Intent(it, BillActivity::class.java)
//          ----------------pass data to bill_fragment
                            intent.putExtra("item_img",itemImg)
                            intent.putExtra("item_name",itemTitle)
                            intent.putExtra("item_price",itemPrice)
                            it.startActivity(intent)
                        }



                    }

                })

//for recycler view click --end
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("test", error.message)
            }

        }
        reference?.addValueEventListener(FirebaseListener)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(
            context,
            2,
            GridLayoutManager.VERTICAL,
            false
        )

        return view
    }


}