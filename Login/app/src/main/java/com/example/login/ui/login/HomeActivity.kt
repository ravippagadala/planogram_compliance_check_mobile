package com.example.login.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.login.R
import com.google.gson.Gson

class HomeActivity : AppCompatActivity() {
    //lateinit var storenamevar: TextView
    //val product_details: List<ProductDetails>?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val firstButton : Button = findViewById(R.id.firstBTN)
        val secondButton : Button = findViewById(R.id.secondBTN)

        val storeName: TextView = findViewById(R.id.storeNameTV)
        val storeAddress: TextView = findViewById(R.id.storeAddressTV)
        val storeContact: TextView = findViewById(R.id.storeContactTV)
        val numShelves: TextView = findViewById(R.id.numShelvesTV)
        var jsonString: String = ""
        val utils = Utils()
                //val jsonString = utils.getJsonDataFromAsset(applicationContext,"planogram.json")
        val queue = Volley.newRequestQueue(this)
        val url = "https://getplanogram-az4z9nrb.ew.gateway.dev/getPlanogram"
        // Request a string response from the provided URL.
        val stringRequestH = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->

                // Display the first 500 characters of the response string.
                jsonString = response.toString()
            },
            Response.ErrorListener { error -> jsonString = error.toString() })

// Add the request to the RequestQueue.
        queue.add(stringRequestH)
        Thread.sleep(10000)
        println("home json "+stringRequestH)
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


        for (item in shelf_details){
            //println("shelf"+item.shelf_id)
            item.number_of_racks

        }
        val shelf_one = findViewById(R.id.firstBTN) as Button
        // set on-click listener
        shelf_one.setOnClickListener {
            val intent1 = Intent(this, MainActivity::class.java)

            intent1.putExtra("shelfID", "0")
            // start your next activity
            startActivity(intent1)

        }
        val shelf_two = findViewById(R.id.secondBTN) as Button
        // set on-click listener
        shelf_two.setOnClickListener {
            val intent2 = Intent(this, MainActivity::class.java)
            intent2.putExtra("shelfID", "1")
            // start your next activity
            startActivity(intent2)

        }

    }

    //fun parseJSON() {}
}