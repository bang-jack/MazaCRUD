package com.samapp.mazacrud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.samapp.mazacrud.data.AppDatabase
import com.samapp.mazacrud.data.entity.User

class EditorActivity : AppCompatActivity() {
    private lateinit var fullName: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var btnsave: Button
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        fullName = findViewById(R.id.full_name)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        btnsave = findViewById(R.id.btn_save)

        database = AppDatabase.getInstance(applicationContext)

        var intent = intent.extras
        if (intent!=null){
            var id = intent.getInt("id", 0)
            var user = database.userDao().get(id)

            fullName.setText(user.fullName)
            email.setText(user.email)
            phone.setText(user.phone)
        }

        btnsave.setOnClickListener {
            if (fullName.text.isNotEmpty() && email.text.isNotEmpty() && phone.text.isNotEmpty()){
                if(intent!=null){
                    database.userDao().update(
                        User(
                            intent.getInt("id", 0),
                            fullName.text.toString(),
                            email.text.toString(),
                            phone.text.toString()
                        )
                    )
                }else{
                    // tambah data
                    database.userDao().insertAll(
                        User(
                            null,
                            fullName.text.toString(),
                            email.text.toString(),
                            phone.text.toString()
                        )
                    )
                }

                finish()
        } else{
            Toast.makeText(
                applicationContext,
                "Silahkan isi semua data dengan valid",
                Toast.LENGTH_SHORT
            ).show()
        }
        }
    }
}