package com.example.databinding
// Lightweight view binding alternative that doesn't use AndroidX.
// disadvantage: manual config, no error checks :(
import com.example.R
import android.view.LayoutInflater
import android.view.View
// change this depending on your needs
import android.widget.LinearLayout
import android.widget.Button
// change this depending on what layout you're aiming (eg. activity_main.xml -> ActivityMainBinding)
class ActivityMainBinding(private val view: View){
	// change this depending on what root layout you use
	private val root: LinearLayout = view as LinearLayout
	private val rootView: LinearLayout = view as LinearLayout
	// change this depending on your needs
	val btn1: Button = view.findViewById(R.id.btn1)
	val btn2: Button = view.findViewById(R.id.btn2)
	fun getRoot(): LinearLayout {
		return rootView
	}
	fun inflate(layoutInflater:LayoutInflater): ActivityMainBinding {
		return ActivityMainBinding(layoutInflater.inflate(R.layout.activity_main,null,false))
	}
}