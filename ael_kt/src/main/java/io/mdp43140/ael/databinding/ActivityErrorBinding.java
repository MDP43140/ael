/*
 * SPDX-FileCopyrightText: 2024-2025 MDP43140
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package io.mdp43140.ael.databinding;
// Lightweight view binding alternative that doesn't use AndroidX.
// disadvantage: manual config, no error checks :(
import io.mdp43140.ael.R;
import android.view.LayoutInflater;
import android.view.View;
// change this depending on your needs
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
// change this depending on what layout you're aiming (eg. activity_main.xml -> ActivityMainBinding)
public final class ActivityErrorBinding {
	// change this depending on what root layout you use
	private final LinearLayout root;
	private final LinearLayout rootView;
	// change this depending on your needs
	public final TextView error;
	public final Button reportIssueBtn;
	public final Button shareFormattedLogBtn;
	public final Button shareLogBtn;
	private ActivityErrorBinding(View view){
		this.root = (LinearLayout) view;
		this.rootView = (LinearLayout) view;
		// change this depending on your needs
		this.error = view.findViewById(R.id.error);
		this.reportIssueBtn = view.findViewById(R.id.reportIssueBtn);
		this.shareFormattedLogBtn = view.findViewById(R.id.shareFormattedLogBtn);
		this.shareLogBtn = view.findViewById(R.id.shareLogBtn);
	}
	public LinearLayout getRoot(){
		return this.rootView;
	}
	public static ActivityErrorBinding inflate(LayoutInflater layoutInflater){
		return new ActivityErrorBinding(layoutInflater.inflate(R.layout.activity_error,null,false));
	}
}
