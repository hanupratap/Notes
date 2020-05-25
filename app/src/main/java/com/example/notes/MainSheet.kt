package com.example.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_main_sheet.*
import javax.sql.StatementEvent

class MainSheet : AppCompatActivity() {


    private lateinit var myAdapter: MyAdapter

    private val db:FirebaseFirestore = FirebaseFirestore.getInstance()
    private var list = mutableListOf<Blogs>()

    private val currentuser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private val USERS:String = "Users"
    private val NOTES:String = "Notes"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_sheet)


        if(currentuser!=null)
        {
            db.collection(USERS).document(currentuser.uid).collection(NOTES).addSnapshotListener{ querySnapshot: QuerySnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
                if (querySnapshot != null) {
                    list.clear()
                    for(document in querySnapshot) {
                        val title = document.getString("title").toString()
                        val desc = document.getString("description").toString()
                        list.add(Blogs(title, desc))
                    }
                    initRecyclerView()
                }
            }

//            db.collection(USERS).document(currentuser.uid).collection(NOTES).get().addOnSuccessListener {querySnapshot ->
//                for(document in querySnapshot)
//                {
//                    val title = document.getString("title").toString()
//                    val desc = document.getString("description").toString()
//                    list.add(Blogs(title, desc))
//                }
//                initRecyclerView()
//            }

        }


        fab.setOnClickListener{
            val intent = Intent(this, TakeNotes::class.java)
            startActivity(intent)
        }







    }


    fun initRecyclerView()
    {
        blogRecycler.layoutManager = StaggeredGridLayoutManager( 2, LinearLayoutManager.VERTICAL)
        myAdapter = MyAdapter()
        myAdapter.submitList(list)
        blogRecycler.adapter = myAdapter

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            R.id.toolbar_signout -> logout()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu , menu)
        return true
    }

    fun logout()
    {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(FirebaseAuth.getInstance().currentUser==null)
        {
            if (menu != null) {
                menu.getItem(0).isEnabled = false
            }
        }
        else{
            if (menu != null) {
                menu.getItem(0).isEnabled = true
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

}
