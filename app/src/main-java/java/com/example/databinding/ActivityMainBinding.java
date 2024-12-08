package com.example.databinding;
// Lightweight view binding alternative that doesn't use AndroidX.
// disadvantage: manual config, no error checks :(
import com.example.R;
import android.view.LayoutInflater;
import android.view.View;
// change this depending on your needs
import android.widget.LinearLayout;
import android.widget.Button;
// change this depending on what layout you're aiming (eg. activity_main.xml -> ActivityMainBinding)
public final class ActivityMainBinding {
	// change this depending on what root layout you use
	private final LinearLayout root;
	private final LinearLayout rootView;
	// change this depending on your needs
	public final Button btn1;
	public final Button btn2;
	private ActivityMainBinding(View view){
		this.root = (LinearLayout) view;
		this.rootView = (LinearLayout) view;
		// change this depending on your needs
		this.btn1 = view.findViewById(R.id.btn1);
		this.btn2 = view.findViewById(R.id.btn2);
	}
	public LinearLayout getRoot(){
		return this.rootView;
	}
	public static ActivityMainBinding inflate(LayoutInflater layoutInflater){
		return new ActivityMainBinding(layoutInflater.inflate(R.layout.activity_main,null,false));
	}
}