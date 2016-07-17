package com.example.hospitalsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class NurseMain extends Activity {
	public final static String TRACE_BACK = "com.example.hospitalsystem.TRACE_BACK";
	public final static String PERMISSION = "com.example.hospitalsystem.PERMISSION";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nurse_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nurse_main, menu);
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
	
	public void onClickNewPatient(View view){
		Intent intent = new Intent(this, NewPatient.class);
		String userLevel = "Nurse";
		intent.putExtra(PERMISSION, userLevel);
		startActivity(intent);
	}
	
	public void onClickPaitentList(View view){
		Intent intent = new Intent(this, WaitingList.class);
		String userLevel = "Nurse";
		intent.putExtra(PERMISSION, userLevel);
		startActivity(intent);
	}
	
	public void onClickLookUpPatient(View view){
		Intent intent = new Intent(this, LookUpPatient.class);
		String userLevel = "Nurse";
		String traceBack = "LookUpPatient";
		intent.putExtra(PERMISSION, userLevel);
		intent.putExtra(TRACE_BACK, traceBack);
		startActivity(intent);
	}
	
	public void onClickCreateNewRecord(View view){
		Intent intent = new Intent(this, LookUpPatient.class);
		String userLevel = "Nurse";
		String traceBack = "CreateNewRecord";
		intent.putExtra(PERMISSION, userLevel);
		intent.putExtra(TRACE_BACK, traceBack);
		startActivity(intent);
	}
}
