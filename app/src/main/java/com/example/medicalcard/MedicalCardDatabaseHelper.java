package com.example.medicalcard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MedicalCardDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MedicalCardDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_MEDICAL_CARDS = "medical_cards";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMERGENCY_CONTACT = "emergency_contact";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_BLOOD_GROUP = "blood_group";

    private static final String CREATE_TABLE_MEDICAL_CARDS =
            "CREATE TABLE " + TABLE_MEDICAL_CARDS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_EMERGENCY_CONTACT + " TEXT, " +
                    COLUMN_ADDRESS + " TEXT, " +
                    COLUMN_GENDER + " TEXT, " +
                    COLUMN_BLOOD_GROUP + " TEXT);";

    public MedicalCardDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MEDICAL_CARDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Add any necessary upgrade logic here
    }

    public long insertMedicalCard(String name, String emergencyContact, String address, String gender, String bloodGroup) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMERGENCY_CONTACT, emergencyContact);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_BLOOD_GROUP, bloodGroup);

        return db.insert(TABLE_MEDICAL_CARDS, null, values);
    }

    public MedicalCard retrieveMedicalCard() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_MEDICAL_CARDS + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1";

        Cursor cursor = db.rawQuery(query, null);
        MedicalCard medicalCard = null;

        if (cursor != null && cursor.moveToFirst()) {
            // Extract data from the cursor
            long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String emergencyContact = cursor.getString(cursor.getColumnIndex(COLUMN_EMERGENCY_CONTACT));
            String address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
            String gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER));
            String bloodGroup = cursor.getString(cursor.getColumnIndex(COLUMN_BLOOD_GROUP));

            // Create a MedicalCard object
            medicalCard = new MedicalCard(id, name, emergencyContact, address, gender, bloodGroup);

            cursor.close();
        }

        return medicalCard;
    }
}
