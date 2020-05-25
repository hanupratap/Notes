package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthActionCodeException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_take_notes.*

class TakeNotes : AppCompatActivity() {

    val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_notes)
        supportActionBar?.hide()


        submit.setOnClickListener {
            val title:String = textField_Title.editText?.text.toString()
            val desc:String = textField_Desc.editText?.text.toString()
            var map = mutableMapOf<String, String>()
            map.put("title", title)
            map.put("description", desc)
            if (currentUser != null) {
                FirebaseFirestore.getInstance().collection("Users").document(currentUser.uid).collection("Notes").add(map).addOnSuccessListener {
                    Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT)
                    finish()
                }
            }
        }
    }
}
