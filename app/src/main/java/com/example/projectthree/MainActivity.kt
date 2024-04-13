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

        binding.btnGetCatData.setOnClickListener { printCatData() }

        //setSupportActionBar(binding.toolbar)

        //val navController = findNavController(R.id.nav_host_fragment_content_main)
        //appBarConfiguration = AppBarConfiguration(navController.graph)
        //setupActionBarWithNavController(navController, appBarConfiguration)

        //binding.fab.setOnClickListener { view ->
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show()
        }

    fun printCatData() {
        var catURL =
            "https://api.thecatapi.com/v1/breeds" + "?api_key=live_Zgt4g7I1nVR1vZGaKgM3PQoa5CzhigPmAULsek27alt7EfoOPbA6KNVl6PIjALpm"

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
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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