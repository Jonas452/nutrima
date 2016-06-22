package com.nutri.jonas.nutrima.model;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class Paciente
{

    public final static String TABLE_NAME = "Paciente";

    public final static String ID = "id";
    public final static String NOME = "nome";
    public final static String TELEFONE = "telefone";
    public final static String DATA_NASCIMENTO = "datanascimento";
    public final static String EMAIL = "email";
    public final static String PESO_MINIMO_IDEAL = "pesominimoideal";
    public final static String PESO_MAXIMO_IDEAL = "pesomaximoideal";

    public final static String CREATE_TABLE_SQL_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOME + " TEXT NOT NULL, " +
                    TELEFONE + " TEXT NOT NULL, " +
                    DATA_NASCIMENTO + " TEXT NOT NULL, " +
                    EMAIL + " TEXT, " +
                    PESO_MINIMO_IDEAL + " REAL NOT NULL, " +
                    PESO_MAXIMO_IDEAL + " REAL NOT NULL );";

    public long id;
    public String nome;
    public String telefone;
    public String dataNascimento;
    public String email;
    public float pesoMinimoIdeal;
    public float pesoMaximoIdeal;

    public Paciente() {}

    public Paciente( HashMap<String, String> pacienteHashMap )
    {

        this.id = Long.parseLong(pacienteHashMap.get(ID));
        this.nome = pacienteHashMap.get( NOME );
        this.telefone = pacienteHashMap.get( TELEFONE );
        this.dataNascimento = pacienteHashMap.get( DATA_NASCIMENTO );
        this.email = pacienteHashMap.get(EMAIL);
        this.pesoMinimoIdeal = Float.parseFloat( pacienteHashMap.get( PESO_MINIMO_IDEAL ) );
        this.pesoMaximoIdeal = Float.parseFloat( pacienteHashMap.get( PESO_MAXIMO_IDEAL ) );

    }

    public Paciente( JSONObject pacienteJSONObject )
    {

        try
        {

            this.id = pacienteJSONObject.getInt( ID );
            this.nome = pacienteJSONObject.getString( NOME );
            this.telefone = pacienteJSONObject.getString( TELEFONE );
            this.dataNascimento = pacienteJSONObject.getString( DATA_NASCIMENTO );
            this.email = pacienteJSONObject.getString( EMAIL );
            this.pesoMinimoIdeal = pacienteJSONObject.getInt( PESO_MINIMO_IDEAL );
            this.pesoMaximoIdeal = pacienteJSONObject.getInt( PESO_MAXIMO_IDEAL );

        }catch( JSONException e )
        {

            e.printStackTrace();

        }

    }

    public ContentValues toContentValues( boolean withId )
    {

        ContentValues contentValues = new ContentValues();

        if( withId )
            contentValues.put( ID, id );

        contentValues.put( NOME, nome );
        contentValues.put( TELEFONE, telefone );
        contentValues.put( DATA_NASCIMENTO, dataNascimento );
        contentValues.put( EMAIL, email );
        contentValues.put( PESO_MINIMO_IDEAL, pesoMinimoIdeal );
        contentValues.put( PESO_MAXIMO_IDEAL, pesoMaximoIdeal );

        return contentValues;

    }

}