package com.example.alber.prueba10.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by alber on 30/04/2017.
 */

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // Todas las variables estaticas
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Nombre de la base de datos
    private static final String DATABASE_NAME = "android_api";

    // Nombre de la table logeo
    private static final String TABLE_USER = "user";

    // Nombre de las columnas
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Crear las tablas
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Tablas de la base de datos creadas");
    }

    // Actualizando la tabla
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar la tabla si ya existe
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Crear las tablas otra vez
        onCreate(db);
    }

    /**
     * Almacenar los detalles en la tabla
     * */
    public void addUser(String name, String email, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Nombre
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Fecha de creación

        // Insertar tabla
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Cerra la conexion con la base de datos

        Log.d(TAG, "Nuevo usuario insertado en sqlite: " + id);
    }

    /**
     * Devolver los usuarios de la base de datos
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Mover a la primera de fila
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();
        // Devolver el usuario
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Crear la base de datos otra vez
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Eleminar la base de datos
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Se ha eliminado todo de SQLite");
    }

}