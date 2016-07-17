package com.example.hospitalsystem;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * The title screen, which contains a button to begin the program and search patient by health card number.
 * Also automatically loads the current patient database and serializes it for later use by other activites.
 */
public class MainActivity extends Activity implements OnClickListener {

	private PatientSystem patientSystem;
	private Users users;
	private File f; 
	private File serialize;
	private File userData;
	private File serializedSystem;
	private PatientsDbHelper db;

    private static final String PATIENT_RECORDS_TXT = "patient_records.txt";
    private static final String PASSWORDS_TXT = "passwords.txt";

	// Testing to grab the passwords and patient records from assets instead of necessitating the
    // usage of ADB to push a file onto the device

    private InputStream patientRecords;
    private InputStream passwords;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//f = new File(this.getApplicationContext().getFilesDir().getAbsolutePath() + "/patient_records.txt");
		//userData = new File(this.getApplicationContext().getFilesDir().getAbsolutePath() + "/passwords.txt");

        try
        {
            patientRecords = this.getAssets().open(PATIENT_RECORDS_TXT);
            passwords = this.getAssets().open(PASSWORDS_TXT);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

		patientSystem = new PatientSystem(this.getApplicationContext());
		
		boolean db_status = patientSystem.checkDatabase(this.getApplicationContext());
		if (db_status == false) {
			// users = new Users(userData);
            // Utilize the assets folder
            users = new Users(passwords);
			db = patientSystem.populateDatabaseFromTxt(patientRecords, users);
			System.out.println("Loaded from txt");
			Log.i("BLAH", "Loaded from text file");
		}
		else {
			db = patientSystem.db;
			patientSystem.populateSystemFromDatabase(this.getApplicationContext());
			List <User> u = db.getAllUsers();
			users = new Users(u);
			System.out.println("Loaded from existing database");
			Log.i("BLAH", "Loaded from existing database");
		}
		
	}
	



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Executed when clicking the log in button
	 */
	@Override
	public void onClick(View v) {
		//get the username and password
		EditText usernameView = (EditText)findViewById(R.id.MainUserName);
		String username = usernameView.getText().toString();
		
		EditText passwordView = (EditText)findViewById(R.id.MainPassword);
		String password = passwordView.getText().toString();
		TextView loginFeedback = (TextView)findViewById(R.id.LoginFeedback);
		
		User user = users.login(username, password);
		
		if(user != null){
			if(user.isPhysician()){
				Intent intentPhy = new Intent (this, PhysicianMain.class);
				startActivity(intentPhy);
				loginFeedback.setText("");
			}
			else{
					Intent intentNurse = new Intent (this, NurseMain.class);
					startActivity(intentNurse);
					loginFeedback.setText("");
			}
			usernameView.setText("");
			passwordView.setText("");
		}else{
			loginFeedback.setText(R.string.MainLoginFeedBack);
		}
	}

}
