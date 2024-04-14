package com.example.projectthree

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class BreedDetails : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_breed_details, container, false)

        // Retrieve arguments passed from BreedSelectorFragment
        val args = arguments
        if (args != null) {
            val catName = args.getString("name")
            val catDescription = args.getString("description")
            val catOrigin = args.getString("origin")
            val catTemperament = args.getString("temperament")

            // Populate views with cat information
            view.findViewById<TextView>(R.id.catName).text = "Name: $catName"
            view.findViewById<TextView>(R.id.catDescription).text = "Description: $catDescription"
            view.findViewById<TextView>(R.id.catOrigin).text = "Origin: $catOrigin"
            view.findViewById<TextView>(R.id.catTemperament).text = "Temperament: $catTemperament"
        }

        return view
    }
}