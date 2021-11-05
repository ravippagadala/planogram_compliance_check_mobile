package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    //lateinit var storenamevar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val enterButton : Button = findViewById(R.id.enterBTN)
        val storenamevar: TextView = findViewById(R.id.storeNameTV)
        val utils = Utils()
        val jsonString = utils.getJsonDataFromAsset(applicationContext,"planogram.json")
        val gson = Gson()
        val planogram = gson.fromJson(jsonString,Planogram::class.java)
        val store_name = planogram.store_name
        storenamevar.setText("Store Name: " + store_name)
        //storenamevar.isVisible = true
        var buttonName = "Hi Ravi"
        enterButton.setText(buttonName)

        val product_details = planogram.product_details
        for (item in product_details){
            println(item.product_label)

        }
         val shelf_details = planogram.shelf_details
        for (item in shelf_details){
            println("shelf"+item.shelf_id)
            item.number_of_racks
            for (rack in item.rack_details){
                println("rack"+rack.rack_id)
            }
        }

    }

    //fun parseJSON() {}
}