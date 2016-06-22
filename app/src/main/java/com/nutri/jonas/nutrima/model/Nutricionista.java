package com.nutri.jonas.nutrima.model;

import android.content.ContentValues;

import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class Nutricionista
{

    public final static String TABLE_NAME = "Nutricionista";

    public final static String ID = "id";
    public final static String CONSULTORIO_ID = "consultorioId";
    public final static String EMAIL = "email";
    public final static String CRN = "crn";

    public final static String CREATE_TABLE_SQL_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CONSULTORIO_ID + " INTEGER NOT NULL, " +
                    EMAIL + " TEXT NOT NULL, " +
                    CRN + " TEXT NOT NULL );";

    public long id;
    public long consultorioId;
    public String email;
    public String crn;

    public Nutricionista() {}

    public Nutricionista( HashMap<String, String> nutricionistaHashMap )
    {

        this.id = Long.parseLong( nutricionistaHashMap.get( ID ) );
        this.consultorioId = Long.parseLong( nutricionistaHashMap.get(CONSULTORIO_ID) );
        this.email = nutricionistaHashMap.get( EMAIL );
        this.crn = nutricionistaHashMap.get( CRN );

    }

    public ContentValues toContentValues()
    {

        ContentValues contentValues = new ContentValues();

        contentValues.put( ID, id );
        contentValues.put(CONSULTORIO_ID, consultorioId);
        contentValues.put( EMAIL, email );
        contentValues.put( CRN, crn );

        return contentValues;

    }

}