package com.example.projectthree

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projectthree.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        }

    fun printCatData() {
        var catURL =
            "https://api.thecatapi.com/v1/breeds" + "?api_key=live_i2Acp03KkwXNDBZW6mznEQDcQz96BP61s991dCiQmFOPq5FSbvJ3f7EyX2MKu3q8"

        val queue = Volley.newRequestQueue(this)

        val stringReq = StringRequest(
            com.android.volley.Request.Method.GET, catURL,
            { response ->
                var catsArray: JSONArray = JSONArray(response)

                for (i in 0 until catsArray.length()) {

                    var theCat: JSONObject = catsArray.getJSONObject(i)

                    Log.i(
                        "MainActivity",
                        "Cat name: ${theCat.getString("name")}"
                    )

                    Log.i(
                        "MainActivity",
                        "Cat Description: ${theCat.getString("description")}"
                    )
                }
            },
            {
                Log.i("MainActivity", "That didn't work!")
            }
        )

        queue.add(stringReq)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}