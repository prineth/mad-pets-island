package com.example.petsisland

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.log



private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)


        val logoutBtn = view.findViewById<Button>(R.id.btn_logout)



        logoutBtn.setOnClickListener {
//            Toast.makeText(requireContext(), "LogOut", Toast.LENGTH_SHORT).show()
            Firebase.auth.signOut()
            var navlogin = activity as LoginActivity

        }

       

        return view

    }


}