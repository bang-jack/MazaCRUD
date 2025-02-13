package com.samapp.mazacrud

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.samapp.mazacrud.adapter.UserAdapter
import com.samapp.mazacrud.data.AppDatabase
import com.samapp.mazacrud.data.entity.User
import com.samapp.mazacrud.ui.theme.MazaCRUDTheme

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private var list = mutableListOf<User>()
    private lateinit var adapter: UserAdapter
    private lateinit var database: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        fab = findViewById(R.id.fab)

        database = AppDatabase.getInstance(applicationContext)
        adapter = UserAdapter(list)
        adapter.setDialog(object : UserAdapter.Dialog{
            override fun onClick(position: Int) {
                // Membuat dialog view
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle(list[position].fullName)
                dialog.setItems(R.array.items_option, DialogInterface.OnClickListener{ dialog, which ->
                    if (which == 0) {
                        // Ubah
                        val intent = Intent(this@MainActivity, EditorActivity::class.java)
                        intent.putExtra("id", list[position].uid)
                        startActivity(intent)
                    }else if (which==1){
                        // hapus
                        database.userDao().delete(list[position])
                        getData()
                    }else{
                        dialog.dismiss()
                    }
                })
                val dialogView = dialog.create()
                dialogView.show()
            }

        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, VERTICAL))

        fab.setOnClickListener {
            startActivity(Intent(this, EditorActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getData(){
        list.clear()
        list.addAll(database.userDao().getAll())
        adapter.notifyDataSetChanged()
    }

}