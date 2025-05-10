package com.example
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.databinding.ActivityMainBinding
import io.mdp43140.ael.ErrorLogger
class MainActivity: Activity(){
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		ActivityMainBinding.inflate(layoutInflater).apply {
			btn1.setOnClickListener {
				try {
					// do something
					throw Exception("Exception caught with try-catch goes through ErrorLogger...")
				} catch (e: Exception){
					ErrorLogger.instance?.handleError(e)
				}
			};
			btn2.setOnClickListener {
				// do something
				throw Exception("Uncaught Exception goes through ErrorLogger...")
			};
			setContentView(root)
		}
	}
}
