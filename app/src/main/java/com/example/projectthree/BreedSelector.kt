package com.example.projectthree

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class BreedSelector : Fragment() {

    private lateinit var catSpinner: Spinner
    private var breedList = mutableListOf<JSONObject>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_breed_selector, container, false)
        catSpinner = view.findViewById(R.id.catSpinner)
        fetchCatBreeds()

        catSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position >= 0 && position < breedList.size) {
                    val breed = breedList[position]
                    showBreedDetails(breed)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        return view
    }

    private fun showBreedDetails(breed: JSONObject) {
        // Create an instance of the BreedDetails fragment and set arguments
        val breedDetails = BreedDetails().apply {
            arguments = Bundle().apply {
                putString("name", breed.optString("name"))
                putString("description", breed.optString("description"))
                putString("origin", breed.optString("origin"))
                putString("temperament", breed.optString("temperament"))
            }
        }

        // Perform the fragment transaction to replace the current fragment with the BreedDetailsFragment
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView9, breedDetails)
            .addToBackStack(null) // Add transaction to the back stack (optional)
            .commit()
    }

    private fun fetchCatBreeds() {
        val catURL = "https://api.thecatapi.com/v1/breeds" + "?api_key=live_i2Acp03KkwXNDBZW6mznEQDcQz96BP61s991dCiQmFOPq5FSbvJ3f7EyX2MKu3q8"
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())

        val stringRequest = StringRequest(Request.Method.GET, catURL,
            Response.Listener<String> { response ->
                breedList = parseCatBreeds(response)
                val catNames = breedList.map { it.optString("name") }
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, catNames)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                catSpinner.adapter = adapter
            },
            Response.ErrorListener { error ->
                Log.e("BreedSelectorFragment", "Error fetching cat breeds: ${error.message}")
            })

        queue.add(stringRequest)
    }

    private fun parseCatBreeds(response: String): MutableList<JSONObject> {
        val breeds = mutableListOf<JSONObject>()
        try {
            val jsonArray = JSONArray(response)
            for (i in 0 until jsonArray.length()) {
                breeds.add(jsonArray.getJSONObject(i))
            }
        } catch (e: JSONException) {
            Log.e("BreedSelectorFragment", "Error parsing JSON data: ${e.message}")
        }
        return breeds
    }
}


