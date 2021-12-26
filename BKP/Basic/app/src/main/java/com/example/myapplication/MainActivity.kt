package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    //lateinit var storenamevar: TextView
    //val product_details: List<ProductDetails>?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstButton : Button = findViewById(R.id.firstBTN)
        val secondButton : Button = findViewById(R.id.secondBTN)

        val storeName: TextView = findViewById(R.id.storeNameTV)
        val storeAddress: TextView = findViewById(R.id.storeAddressTV)
        val storeContact: TextView = findViewById(R.id.storeContactTV)
        val numShelves: TextView = findViewById(R.id.numShelvesTV)

        val utils = Utils()
        val jsonString = utils.getJsonDataFromAsset(applicationContext,"planogram.json")
        val gson = Gson()
        val planogram = gson.fromJson(jsonString,Planogram::class.java)

        storeName.setText("Store Name: " + planogram.store_name)
        storeAddress.setText("Store Address: " + planogram.store_address)
        storeContact.setText("Store Contact: " + planogram.store_contact)



        //storenamevar.isVisible = true
        //var buttonName = "Hi Ravi"
        //enterButton.setText(buttonName)
        val shelf_details = planogram.shelf_details
        numShelves.setText("Number of Shelves: " + shelf_details.size)

        val product_details = planogram.product_details
        for (item in product_details){
            println(item.product_label)

        }

        for (item in shelf_details){
            println("shelf"+item.shelf_id)
            item.number_of_racks
            for (rack in item.rack_details){
                println("rack"+rack.rack_id)
            }
        }
        val btn_click_me = findViewById(R.id.firstBTN) as Button
        // set on-click listener
        btn_click_me.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@MainActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
        }

    }

    //fun parseJSON() {}
}