package com.example.petsisland

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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

        database = FirebaseDatabase.getInstance("https://pets-island-default-rtdb.asia-southeast1.firebasedatabase.app/")
        reference = database?.getReference("products")

        val firebaseListner = object : ValueEventListener {
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



            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("test", error.message)
            }

        }
        reference?.addValueEventListener(firebaseListner)

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