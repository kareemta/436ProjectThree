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
import org.json.JSONObject
import androidx.navigation.fragment.findNavController





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
                    navigateToBreedDetails(breed)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        return view
    }

    private fun navigateToBreedDetails(breed: JSONObject) {
        val bundle = Bundle().apply {
            putString("name", breed.getString("name"))
            putString("description", breed.getString("description"))
            putString("origin", breed.getString("origin"))
            putString("temperament", breed.getString("temperament"))
        }
        findNavController().navigate(R.id.action_breedSelector_to_breedDetails, bundle) // Ensure this ID matches your navigation graph
    }

    private fun fetchCatBreeds() {
        val catURL = "https://api.thecatapi.com/v1/breeds" + "?api_key=live_Zgt4g7I1nVR1vZGaKgM3PQoa5CzhigPmAULsek27alt7EfoOPbA6KNVl6PIjALpm"
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())

        val stringRequest = StringRequest(Request.Method.GET, catURL,
            Response.Listener<String> { response ->
                breedList = parseCatBreeds(response)
                val catNames = breedList.map { it.getString("name") }
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
        val jsonArray = JSONArray(response)
        for (i in 0 until jsonArray.length()) {
            breeds.add(jsonArray.getJSONObject(i))
        }
        return breeds
    }
}