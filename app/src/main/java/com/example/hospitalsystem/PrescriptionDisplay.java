package com.example.hospitalsystem;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class PrescriptionDisplay extends Activity {
	
	public final static String HEALTH_CARD_NUM = "com.example.hospitalsystem.HEALTH_CARD_NUMBER";
	public final static String TRACE_BACK = "com.example.hospitalsystem.TRACE_BACK";
	public final static String PERMISSION = "com.example.hospitalsystem.PERMISSION";
	public final static String RECORD_POSITION = "com.example.hospitalsystem.PreviousRecord.RECORD_POSITION";
	public final static String ROW_ID = "com.example.hospitalsystem.PreviousRecord.ROW_ID";
	public final static String MESSAGE = "com.example.hospitalsystem.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prescription_display);
		
		Intent intent = getIntent();
		String message = intent.getStringExtra(MESSAGE);
		
		TextView prescription = (TextView)findViewById(R.id.PrescriptionDisplay);
		prescription.setText(message);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
		    actionBar.setHomeButtonEnabled(false); // disable the button
		    actionBar.setDisplayHomeAsUpEnabled(false); // remove the left caret
		    actionBar.setDisplayShowHomeEnabled(false); // remove the icon
		}
		getMenuInflater().inflate(R.menu.prescription_display, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
