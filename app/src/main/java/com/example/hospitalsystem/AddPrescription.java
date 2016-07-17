package com.example.hospitalsystem;

import java.io.FileNotFoundException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddPrescription extends Activity {
	
	public final static String HEALTH_CARD_NUM = "com.example.hospitalsystem.HEALTH_CARD_NUMBER";

	private PatientSystem patientSystem;
	private Button save;
	private EditText medicationInput;
	private EditText instructionInput;
	private String healthCardNumber;
	private PatientsDbHelper db; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_prescription);
		db = new PatientsDbHelper(this.getApplicationContext());
		patientSystem = new PatientSystem(this.getApplicationContext());
		patientSystem.populateSystemFromDatabase(getApplicationContext());
		save = (Button) findViewById(R.id.prescriptionSave);
		save.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onClickSave();
			}
		});
		//serialize
		instructionInput = (EditText) findViewById(R.id.instructionsInput);
		medicationInput = (EditText) findViewById(R.id.medicationInput);
		healthCardNumber = getIntent().getStringExtra(HEALTH_CARD_NUM);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_prescription, menu);
		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
		    actionBar.setHomeButtonEnabled(false); // disable the button
		    actionBar.setDisplayHomeAsUpEnabled(false); // remove the left caret
		    actionBar.setDisplayShowHomeEnabled(false); // remove the icon
		}
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
	
	private void onClickSave() {
		Patient p = patientSystem.findPatientByHealthCard(healthCardNumber);
		String medication = medicationInput.getText().toString();
		String instructions = instructionInput.getText().toString();
		
		Prescription prescription = new Prescription(medication, instructions, new Time());
		p.addPrescription(prescription);
		long record_id = db.getRecordID(p.getRecord(0), p);
		db.addPrescription(prescription, db.getPatientID(p), p.getNumRecords());
		
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle("Your patient has been prescribed!")
		        .setContentText("Congratulations, you are one step closer to saving and enhancing this person's life." +
		        		" Feel better about yourself.");
		NotificationManager mNotificationManager =
			    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(1, mBuilder.build());
		finish();
	}
}
