package com.example.petsisland

import android.content.ContentValues.TAG
import android.content.Intent
import android.icu.number.NumberFormatter.with
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.petsisland.databinding.ActivityBillBinding
import com.example.petsisland.databinding.ActivitySignupBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class BillActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBillBinding
    private lateinit var database: DatabaseReference

    var quantity = 0
    var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBillBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var mytitle = binding.billTitle
        var myImg = binding.billImage
        var myPrice = binding.billPrice
        var myTotal = binding.billAmount

        var price = intent.getSerializableExtra("item_price").toString()
        var intPrice = price.replace("$", "").toInt()

        Picasso.get()
            .load(intent.getStringExtra("item_img").toString())
            .fit().centerCrop()
            .into(myImg)

        mytitle.text = intent.getSerializableExtra("item_name").toString()
        myPrice.text = price
        myTotal.text = quantity.toString()

        binding.buttonHigh.setOnClickListener {
            quantity++
            binding.billQuantity.text = quantity.toString()
            total = quantity * intPrice
            binding.billAmount.text = total.toString()
        }

        binding.buttonLow.setOnClickListener {
            quantity--
            binding.billQuantity.text = quantity.toString()
            total = quantity * intPrice
            binding.billAmount.text = total.toString()
        }


//checkout button
        binding.buttonCheckout.setOnClickListener {

//            addUser()

            val email = "user@gmail.com"
            val title = binding.billTitle.text.toString()
            val price = binding.billPrice.text.toString()
            val quantity = binding.billQuantity.text.toString()
            val amount = binding.billAmount.text.toString()
            var username = binding.username.text.toString()
            var address = binding.address.text.toString()

            database =
                FirebaseDatabase.getInstance("https://pets-island-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("bills")
            val order = Bill(email, title, price, quantity, amount, username, address)
            database.child(username).setValue(order).addOnSuccessListener {

                Toast.makeText(this, "Successfully Ordered $title", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, OrderSuccessActivity::class.java)
                startActivity(intent)

            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }


    }


}