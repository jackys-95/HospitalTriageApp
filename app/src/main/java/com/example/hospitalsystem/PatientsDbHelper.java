package com.example.hospitalsystem;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * A Database helper that creates, maintains, and updates the database that 
 * is used to store information for the application.
 */
public class PatientsDbHelper extends SQLiteOpenHelper {
	
	/**
	 * Database version. 
	 */
	private static final int DB_VERSION = 1; 
	
	/**
	 * Name of the database. 
	 */
	private static final String DB_NAME = "PatientsDatabase";
	
	//Table Names 
	/**
	 * Table name for Patients. 
	 */
	private static final String PATIENTS_DATA = "Patients";
	
	/**
	 * Table name for Users. 
	 */
	private static final String USERS_DATA = "Users";
	
	/**
	 * Table name for Records. 
	 */
	private static final String RECORDS = "Records";
	
	/**
	 * Table name for Prescriptions. 
	 */
	private static final String PRESCRIPTIONS = "Prescriptions"; 
	
	/**
	 * Table name for Vitals.
	 */
	private static final String VITAL_SIGNS = "Vitals";
	
	/**
	 * Table name for Times seen by Doctor. 
	 */
	private static final String TIMES_SEEN = "Times_Seen_By_Doctor"; 
	
	//Common Column Names
	/**
	 * Name for the Patients ID column. 
	 */
	private static final String KEY_PATIENTS_ID = "Patients_ID";
	
	/**
	 * Name for the Records ID column.
	 */
	private static final String KEY_RECORDS_ID = "Records_ID";
	
	/**
	 * Name for the Urgency column.
	 */
	private static final String URGENCY = "Urgency";
	
	/**
	 * Name for the Time Recorded column.
	 */
	private static final String TIME_RECORDED = "Time_Recorded";
			
	
	//Patients Table Column Names: 
	/**
	 * Name for the Patient Name column. 
	 */
	private static final String NAME = "Name";
	
	/**
	 * Name for the Health Card column.
	 */
	private static final String HEALTH_CARD = "Health_Card";
	
	/**
	 * Name for the Date of Birth column.
	 */
	private static final String DOB = "Date_of_Birth"; 
	
	//Records Table Column Names:
	/**
	 * Name for the Arrival Time column.  
	 */
	private static final String ARRIVAL_TIME = "Arrival_Time";
	
	/**
	 * Name for the Vitals ID column.
	 */
	private static final String KEY_VITALS_ID = "Vitals_ID";
	
	/**
	 * Name for the Systolic blood pressure column.
	 */
	private static final String SYSTOLIC = "Systolic";
	
	/**
	 * Name for the Diastolic blood pressure column.  
	 */
	private static final String DIASTOLIC = "Diastolic";
	
	/**
	 * Name for the Heart Rate column. 
	 */
	private static final String HEART_RATE = "Heart_Rate";
	
	/**
	 * Name for the Temperature column. 
	 */
	private static final String TEMPERATURE = "Temperature";
	
	//Prescription Table Column Names:
	/**
	 * Name for the Prescriptions ID column.  
	 */
	private static final String KEY_PRESCRIPTIONS_ID = "Prescriptions_ID";
	
	/**
	 * Name for the Medication column.
	 */
	private static final String MEDICATION = "Medication";
	
	/**
	 * Name for the Instructions column.  
	 */
	private static final String INSTRUCTIONS = "Instructions";
	
	//Times Seen by Doctor Table Column Names:
	/**
	 * Name for the Times ID column.  
	 */
	private static final String KEY_TIMES_ID = "Times_ID";
	
	/**
	 * Name for the Time Seen column.  
	 */
	private static final String TIME_SEEN = "Time_Seen";
	
	//Users Table Column Names:
	/**
	 * Name for the User ID column.  
	 */
	private static final String USERS_ID = "Users_ID";
	
	/**
	 * Name for the Username column.  
	 */
	private static final String USER_NAME = "Username";
	
	/**
	 * Name for Password column. 
	 */
	private static final String PASSWORD = "Password";
	
	/**
	 * Name for the Is Physician column.
	 */
	private static final String IS_PHYSICIAN = "Physician_Status";
	
	//Table Create Statements
	//Patients Table 
	
	/**
	 * SQL statement to create Patients table.
	 */
	private static final String CREATE_TABLE_PATIENTS = "CREATE TABLE " + 
	PATIENTS_DATA + "(" + KEY_PATIENTS_ID + " INTEGER PRIMARY KEY," + NAME + 
	" TEXT, " + HEALTH_CARD + " TEXT, " + DOB + " TEXT, " + URGENCY +
	" INTEGER" + ")"; 
	
	//Records Table
	/**
	 * SQL statement to create Records table.
	 */
	private static final String CREATE_TABLE_RECORDS = "CREATE TABLE " + RECORDS
			+ "(" + KEY_RECORDS_ID + " INTEGER PRIMARY KEY," + KEY_PATIENTS_ID
			+ " INTEGER," + ARRIVAL_TIME + " TEXT," + URGENCY + " INTEGER" + 
			")";
	
	//Vitals Table
	/**
	 * SQL statement to create Vitals table.
	 */
	private static final String CREATE_TABLE_VITALS = "CREATE TABLE " + 
			VITAL_SIGNS + "(" + KEY_VITALS_ID + " INTEGER PRIMARY KEY," + KEY_PATIENTS_ID + " INTEGER,"
			+ KEY_RECORDS_ID + " INTEGER," + DIASTOLIC + " INTEGER," + SYSTOLIC + " INTEGER," + TEMPERATURE + 
			" REAL," + HEART_RATE + " INTEGER," + TIME_RECORDED + " TEXT" 
			+ ")";
	
	//Prescriptions Table
	/**
	 * SQL statement to create Prescriptions table.
	 */
	private static final String CREATE_TABLE_PRESCRIPTIONS = "CREATE TABLE " +
			PRESCRIPTIONS + "(" + 
			KEY_PRESCRIPTIONS_ID + " INTEGER PRIMARY KEY," + KEY_PATIENTS_ID + 
			" INTEGER," + KEY_RECORDS_ID + 
			" INTEGER," + MEDICATION + " TEXT," + INSTRUCTIONS + " TEXT," + 
			TIME_RECORDED + " TEXT" + ")"; 
	
	//Times Seen by Doctor Table
	/**
	 * SQL statement to create Doctor table.
	 */
	private static final String CREATE_TABLE_DOC_TIMES = "CREATE TABLE " + TIMES_SEEN + "(" + 
			KEY_TIMES_ID + " INTEGER PRIMARY KEY," + KEY_RECORDS_ID + " INTEGER," 
			+ KEY_VITALS_ID + " INTEGER,"
			+ TIME_SEEN + " TEXT" + ")";
 	
	//Users
	/**
	 * SQL statement to create Users table.
	 */
	private static final String CREATE_TABLE_USERS = "CREATE TABLE " + USERS_DATA
			+ "(" + USER_NAME + " TEXT," + PASSWORD + " TEXT," +  IS_PHYSICIAN 
			+ " TEXT" + ")";
	
	/**
	 * Constructs a PatientsDbHelper object given a Context object.
	 * @param context A Context object.
	 */
	public PatientsDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	/**
	 * Creates all of the tables for the SQLite database.
	 */
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_PATIENTS);
		db.execSQL(CREATE_TABLE_RECORDS);
		db.execSQL(CREATE_TABLE_VITALS);
		db.execSQL(CREATE_TABLE_PRESCRIPTIONS);
		db.execSQL(CREATE_TABLE_DOC_TIMES);
		db.execSQL(CREATE_TABLE_USERS);
	}

	@Override
	/**
	 * Upgrades the SQL tables. 
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + PATIENTS_DATA);
		db.execSQL("DROP TABLE IF EXISTS" + RECORDS);
		db.execSQL("DROP TABLE IF EXISTS" + VITAL_SIGNS);
		db.execSQL("DROP TABLE IF EXISTS" + PRESCRIPTIONS);
		db.execSQL("DROP TABLE IF EXISTS" + TIMES_SEEN);
		db.execSQL("DROP TABLE IF EXISTS" + USERS_DATA);
		onCreate(db);
	}
	
	// -------------------Patients Table Methods-------------------- 
	/**
	 * Add a Patient object's information into the Patients table of the Database.
	 * @param patient A Patient object
	 * @return patient_id The unique Row ID of a row in the Patients table that the data was inserted into.
	 */
	public long addPatient(Patient patient) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put(NAME, patient.getName());
		content.put(HEALTH_CARD, patient.getHealthCardNumber());
		content.put(DOB, patient.getDob());
		content.put(URGENCY, patient.getUrgency());
		long patient_id = db.insert(PATIENTS_DATA, null, content);
		db.close();
		return patient_id;
	}
	
	/**
	 * Takes a healthCardNumber and returns a Patient with that Health Card Number. 
	 * @param healthCardNumber A Health Card Number
	 * @return p A Patient Object
	 */
	public Patient getPatient(long patient_id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM " + PATIENTS_DATA + " WHERE " +
		 KEY_PATIENTS_ID + " = " + patient_id;
		
		Cursor c = db.rawQuery(selectQuery, null);
		if (c != null)
				c.moveToFirst();
		
		//Creating a Patient object out of data from the database
		String name = c.getString(c.getColumnIndex(NAME)); 
		String dob = c.getString(c.getColumnIndex(DOB));
		String healthCard = c.getString(c.getColumnIndex(HEALTH_CARD));
	    int urgency = c.getInt(c.getColumnIndex(URGENCY));
		List <Record> records = new ArrayList<Record>();
		records = getAllRecords(patient_id); 
		Time dateOB = new Time(dob);
		Patient p = new Patient(name, healthCard, dateOB);
		p.setUrgency(urgency);
		p.setRecord(records);
		
		return p;
	}
	
	/**
	 * Given a Patient object, returns the Unique Row ID where the Patient's data is stored 
	 * in the Patients table.
	 * @param patient A Patient object
	 * @return a unique Row ID if the patient is in the database, -1 otherwise.
	 */
	public long getPatientID(Patient patient) {
		SQLiteDatabase db = this.getReadableDatabase();
		String healthcard = patient.getHealthCardNumber();
		String selectQuery = "SELECT " + KEY_PATIENTS_ID + " FROM " + PATIENTS_DATA + " WHERE " +
		 HEALTH_CARD + " = " + healthcard;
		
		Cursor c = db.rawQuery(selectQuery, null);
		if (c != null)
				c.moveToFirst();
		return c.getLong(c.getColumnIndex(KEY_PATIENTS_ID));
	}
	
	/**
	 * Get all the patients from the database.
	 * @return a list of all Patients from the database.
	 */
	public List<Patient> getAllPatients() {
		List<Patient> p = new ArrayList<Patient>();
		String selectQuery = "SELECT * FROM " + PATIENTS_DATA; 
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				String name = c.getString(c.getColumnIndex(NAME)); 
				String dob = c.getString(c.getColumnIndex(DOB));
				String healthCard = c.getString(c.getColumnIndex(HEALTH_CARD));
			    int urgency = c.getInt(c.getColumnIndex(URGENCY));
				List <Record> records = new ArrayList<Record>();
				long patient_id = c.getLong(c.getColumnIndex(KEY_PATIENTS_ID));
				records = getAllRecords(patient_id);
				Time dateOB = new Time(dob, true);
				Patient patient = new Patient(name, healthCard, dateOB);
				patient.setRecord(records);
				p.add(patient);
			} while (c.moveToNext());
		}
		return p;
	}
	
	/**
	 * Updates a Patient's urgency. 
	 * @param patient_id The row where the Patient object has its data stored.
	 * @param urgency The urgency that is to be saved to the database.
	 */
	public void updatePatientUrgency(long patient_id, int urgency) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(URGENCY, urgency);
		db.update(PATIENTS_DATA, values,  KEY_PATIENTS_ID + " = " + patient_id, null);
		
	}
	
	// -------------------Records Table Methods-------------------
	/**
	 * Add a record belonging to a Patient to the Records table inside the
	 * database.- 
	 * @param record A Record object
	 * @param patient_id The row ID of the Patient from the Patients table,
	 *  that the Record belongs to.
	 * @return The unique row ID that the Record's data is save to inside the 
	 * Records table.
	 */
	public long addRecord(Record record, long patient_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put(KEY_PATIENTS_ID, patient_id);
		content.put(ARRIVAL_TIME, record.getArrivalTime().toString());
		content.put(URGENCY, record.getUrgency());
		long record_id = db.insert(RECORDS, null, content);
		db.close();
		return record_id;
	}
	
	/**
	 * Given a Patient object and a Record belonging to that Patient, return
	 * the row ID that the Record's data is saved to inside the Records table
	 * of the Database.
	 * @param record
	 * @param patient
	 * @return The unique Row ID of the Record, -1 if it isn't in the table.
	 */
	public long getRecordID(Record record, Patient patient) {
		SQLiteDatabase db = this.getReadableDatabase();
		long patient_id = getPatientID(patient);
		String selectQuery = "SELECT " + KEY_RECORDS_ID + " FROM " + RECORDS 
				+ " WHERE " + KEY_PATIENTS_ID + " = " + patient_id;
		Cursor c = db.rawQuery(selectQuery, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c.getLong(c.getColumnIndex(KEY_RECORDS_ID));
	}
	
	/**
	 * Returns a List of Record object of a given Patient. 
	 * @param patient_id The row ID of where the Patient's data is stored inside
	 * the Patients table of the database. 
	 * @return a List of Record objects belonging to that patient. 
	 */
	public List<Record> getAllRecords(long patient_id) {
		List<Record> records = new ArrayList<Record>();
		String selectQuery = "SELECT * FROM " + RECORDS + " WHERE " + 
		KEY_PATIENTS_ID + " = " + patient_id; 
		
		//get all rows from the Database Record Table
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		//Loop through the rows to create a list of Records
		if (c.moveToFirst()) {
			do {
				//Instantiate Record object
				Time time = new Time(c.getString(c.getColumnIndex(ARRIVAL_TIME)));
				Record record = new Record(time);
				
				//Get the record_id
				long record_id = c.getLong(c.getColumnIndex(KEY_RECORDS_ID));
				
				//Create a list of all VitalSigns belonging to this Record
				List<VitalSigns> v = getAllVitalSigns(patient_id, records.size() + 1);
				
				record.setVitalSignsRecord(v);
				
				//Create a list of all Prescriptions belonging to this Record
				List<Prescription> p = getAllPrescriptions(patient_id, records.size() + 1);
			
				record.setPrescriptions(p);
				
				//Create a list of all Times Seen by Doctor belonging to this Record
				List<Time> timesSeen = getAllTimesSeenByDoctor(records.size() + 1, patient_id);
				
				record.setTimesSeenByDoctor(timesSeen);
				records.add(0, record);
			} while (c.moveToNext());
		}
		return records;
	}
	
	// -------------------Vitals Table Methods-------------------- 
	/**
	 * Adds the information of a specific VitalSigns object belonging to a specific 
	 * Record of a Patient to the Vitals table of the database.
	 * @param vitals A VitalSigns object
	 * @param record_id The row ID of the Record from Records table that this 
	 * VitalSigns object belongs to.
	 * @param patient_id The row ID of the Patient from the Patients table that
	 * the record belongs to.
	 * @return The unique row ID that the VitalSigns' data is saved to inside 
	 * of the Vitals table.
	 */
	public long addVitals(VitalSigns vitals, long record_id, long patient_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put(KEY_PATIENTS_ID, patient_id);
		content.put(KEY_RECORDS_ID, record_id);
		content.put(DIASTOLIC, vitals.getDiastolic());
		content.put(SYSTOLIC, vitals.getSystolic());
		content.put(TEMPERATURE, vitals.getTemp());
		content.put(HEART_RATE, vitals.getHeartRate());
		content.put(TIME_RECORDED, vitals.getTimeRecorded().toString());
		
		long vitals_id = db.insert(VITAL_SIGNS, null, content);
		db.close();
		return vitals_id;
	}
	
	/**
	 * Returns a complete list of VitalSigns of a Record. 
	 * @param record_id The row ID of the Record from the Records table.
	 * @return A list of VitalSigns objects belonging to that Record.
	 */
	public List<VitalSigns> getAllVitalSigns(long patient_id, long vital_id) {
		List<VitalSigns> v = new ArrayList<VitalSigns>();
		String selectQuery = "SELECT * FROM " + VITAL_SIGNS + " WHERE " + 
		KEY_PATIENTS_ID + " = " + patient_id + " AND " + KEY_RECORDS_ID + " = " + vital_id;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				float temperature = c.getFloat(c.getColumnIndex(TEMPERATURE));
				int diastolic = c.getInt(c.getColumnIndex(DIASTOLIC));
				int systolic = c.getInt(c.getColumnIndex(SYSTOLIC));
				int heartRate = c.getInt(c.getColumnIndex(HEART_RATE));
				Time timeTaken = new Time(c.getString(c.getColumnIndex(TIME_RECORDED)));
				VitalSigns vitals = new VitalSigns(temperature, systolic, diastolic, heartRate
						,timeTaken);
				v.add(0, vitals);

			} while (c.moveToNext()); //Whilst there are still vitals left
		}
		
		return v;
	}
	
	// -------------------Prescriptions Table Methods-------------------- 
	/**
	 * Adds a Prescription object of a specific Record of a Patient's to the 
	 * Prescriptions table in the database.
	 * @param prescription A Prescription object.
	 * @param record_id The row ID of the Record from the Records table.
	 * @param patient_id The row ID of the Patient from the Patients table.
	 * @return The unique row ID that the Prescriptions data was saved to inside 
	 * the Prescriptions table.
	 */
	public long addPrescription(Prescription prescription, long record_id, long patient_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put(KEY_PATIENTS_ID, patient_id);
		content.put(KEY_RECORDS_ID, record_id);
		content.put(MEDICATION, prescription.getMedication());
		content.put(INSTRUCTIONS, prescription.getInstructions());
		content.put(TIME_RECORDED, prescription.getTime().toString());
		
		long prescription_id = db.insert(PRESCRIPTIONS, null, content);
		db.close();
		return prescription_id;
	}
	
	/**
	 * Returns a list of Prescription objects belonging to a specific Record of 
	 * a Patient.
	 * @param patient_id The row ID where the Patient's data is saved inside the
	 * database.
	 * @param record_id The row ID where the Record's data is saved inside the 
	 * database.
	 * @return a list of Prescription objects.
	 */
	public List<Prescription> getAllPrescriptions(long patient_id, long record_id) {
		List<Prescription> p = new ArrayList<Prescription>();
		String selectQuery = "SELECT * FROM " + PRESCRIPTIONS + " WHERE " + 
		KEY_RECORDS_ID +  " = " + patient_id + " AND " + KEY_PATIENTS_ID + " = " +record_id;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				String medication = c.getString(c.getColumnIndex(MEDICATION));
				String instructions = c.getString(c.getColumnIndex(INSTRUCTIONS));
				Time time =  new Time(c.getString(c.getColumnIndex(TIME_RECORDED)));
				Prescription prescription = new Prescription(medication, instructions, time);
				p.add(0, prescription);
			} while (c.moveToNext());
		} 
		
		
		return p;
	}
	
	// ----------------Times Seen by Doctor Table Methods------------------
	
	/**
	 * Add the Times seen by Doctor of a specific Record of a Patient. 
	 * @param time A Time object.
	 * @param record_id the row ID of where the Record is saved in the Records
	 * table. 
	 * @param patient_id The row ID of where the Patient is saved in the Patients
	 * table. 
	 * @return the unique row ID of where the Times are saved in the Times_Seen_By_Doctor
	 * table.
	 */
	public long addTimes(Time time, long record_id, long patient_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put(KEY_RECORDS_ID, patient_id);
		content.put(KEY_VITALS_ID, record_id);
		content.put(TIME_SEEN, time.toString());
		
		long times_id = db.insert(TIMES_SEEN, null, content);
		db.close();
		return times_id;
	}
	
	/**
	 * Returns the Time object 
	 * @param times_id
	 * @return
	 */
	/*
	public Time getTimeSeenByDoctor(long times_id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TIMES_SEEN + " WHERE "
		+ KEY_TIMES_ID + " = " + times_id; 
		
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c != null) 
			c.moveToFirst();
		String time = c.getString(c.getColumnIndex(TIME_SEEN));
		Time t = new Time(time);
		return t;		
	}*/
	
	
	/**
	 * Returns all of the Times Seen By Doctor of a specific Record of a Patient.
	 * @param recordId The row ID where the Record's data is saved inside the
	 * Records table.
	 * @param patientId The row ID where the Patient's data is saved inside the
	 * Patients table.
	 * @return A list of Time objects.
	 */
	public List<Time> getAllTimesSeenByDoctor(long recordId, long patientId) {
		List<Time> t = new ArrayList<Time>();
		String selectQuery = "SELECT * FROM " + TIMES_SEEN + " WHERE " + 
		KEY_RECORDS_ID + " = " + patientId + " AND " + KEY_VITALS_ID + " = " + recordId; 
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()) {
			do {
				Time time = new Time(c.getString(c.getColumnIndex(TIME_SEEN)));
				t.add(0, time);
			} while (c.moveToNext());
		}
		return t; 
		
	}
	
	
	// -------------------Users Table Methods-------------------- 
	/**
	 * Adds a User to the Users table of the database.
	 * @param user a User object.
	 * @return The unique row ID where the User object is stored.
	 */
	public long addUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues content = new ContentValues();
		content.put(USER_NAME, user.username);
		content.put(PASSWORD, user.password);
		content.put(IS_PHYSICIAN, user.isPhysician); 
		
		long user_id = db.insert(USERS_DATA, null, content);
		db.close();
		return user_id;
	}
	
	/**
	 * Gets a specific User from the Users table in the database.
	 * @param user_id The row ID where that User's data is saved in the Users
	 * table.
	 * @return a User object.
	 */
	public User getUser(long user_id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + USERS_DATA + " WHERE "
		+ USERS_ID + " = " + user_id; 
		
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c != null) 
			c.moveToFirst();
		String username = c.getString(c.getColumnIndex(USER_NAME));
		String password = c.getString(c.getColumnIndex(PASSWORD)); 
	    String isPhysician = c.getString(c.getColumnIndex(IS_PHYSICIAN));
		User user = new User(username, password, isPhysician);
		
		return user;		
	}
	
	/**
	 * Returns a list of User objects from the database. 
	 * @return a list of User objects.
	 */
	public List<User> getAllUsers() {
			List <User> u = new ArrayList<User>();
			String selectQuery = "SELECT * FROM " + USERS_DATA; 
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor c = db.rawQuery(selectQuery, null);
			
			if (c.moveToFirst()) {
				do {
					String username = c.getString(c.getColumnIndex(USER_NAME));
					String password = c.getString(c.getColumnIndex(PASSWORD)); 
				    String isPhysician = c.getString(c.getColumnIndex(IS_PHYSICIAN));
				    if (isPhysician.equals("0")) {
				    	isPhysician = "nurse";
				    }
				    else {
				    	isPhysician = "physician";
				    }
					User user = new User(username, password, isPhysician);
					u.add(user);
				} while (c.moveToNext());
			}
			return u;
		}
	
	/**
	 * Closes the database connection.
	 */
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
