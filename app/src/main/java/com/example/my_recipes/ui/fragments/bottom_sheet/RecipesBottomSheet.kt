package com.example.my_recipes.ui.fragments.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.my_recipes.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class RecipesBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.recipes_bottom_sheet, container, false)
        return view
    }
}