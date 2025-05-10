package com.example;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.example.databinding.ActivityMainBinding;
import io.mdp43140.ael.ErrorLogger;
public class MainActivity extends Activity {
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
		binding.btn1.setOnClickListener(v -> {
			try {
				// do something
				throw new Exception("Exception caught with try-catch goes through ErrorLogger...");
			} catch (Exception e) {
				ErrorLogger.instance.handleError(e);
			}
		});
		binding.btn2.setOnClickListener(v -> {
			// do something
			System.out.print(1 / 0);
		});
		setContentView(binding.getRoot());
	}
}
