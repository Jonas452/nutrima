package com.nutri.jonas.nutrima.model;

import android.content.ContentValues;

import com.nutri.jonas.nutrima.util.JSONHandler;

import org.json.JSONObject;

import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class Dieta
{

    public final static String TABLE_NAME = "Dieta";

    public final static String ID = "id";
    public final static String PACIENTE_ID = "pacienteId";
    public final static String TIPO_DIETA_ID = "tipoDietaId";
    public final static String NUTRICIONISTA_ID = "nutricionistaId";
    public final static String DATA_INICIO = "dataInicio";
    public final static String DATA_FIM = "dataFim";
    public final static String OBJETIVO_LONGO = "objetivoLongo";
    public final static String INSTRUCAO = "instrucao";

    public final static String CREATE_TABLE_SQL_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PACIENTE_ID + " INTEGER NOT NULL, " +
                    TIPO_DIETA_ID + " INTEGER NOT NULL, " +
                    NUTRICIONISTA_ID + " INTEGER NOT NULL, " +
                    DATA_INICIO + " TEXT NOT NULL, " +
                    DATA_FIM + " TEXT, " +
                    OBJETIVO_LONGO + " TEXT, " +
                    INSTRUCAO + " TEXT );";

    public long id;
    public long pacienteId;
    public long tipoDietaId;
    public long nutricionistaId;
    public String dataInicio;
    public String dataFim;
    public String objetivoLongo;
    public String instrucao;

    public Dieta() {}

    public Dieta( HashMap<String, String> dietaHashMap )
    {

        if( dietaHashMap != null )
        {

            this.id = Long.parseLong(dietaHashMap.get(ID));
            this.pacienteId = Long.parseLong(dietaHashMap.get(PACIENTE_ID));
            this.tipoDietaId = Long.parseLong(dietaHashMap.get(TIPO_DIETA_ID));
            this.nutricionistaId = Long.parseLong(dietaHashMap.get(NUTRICIONISTA_ID));
            this.dataInicio = dietaHashMap.get(DATA_INICIO);
            this.dataFim = dietaHashMap.get(DATA_FIM);
            this.objetivoLongo = dietaHashMap.get(OBJETIVO_LONGO);
            this.instrucao = dietaHashMap.get(INSTRUCAO);

        }

    }

    public Dieta( JSONObject jsonObject )
    {

        if( jsonObject != null )
        {

            this.id = Integer.parseInt(JSONHandler.getStringFromJSONObject(jsonObject, ID));
            this.pacienteId = Long.parseLong(JSONHandler.getStringFromJSONObject(jsonObject, PACIENTE_ID));
            this.tipoDietaId = Long.parseLong(JSONHandler.getStringFromJSONObject(jsonObject, TIPO_DIETA_ID));
            this.nutricionistaId = Long.parseLong(JSONHandler.getStringFromJSONObject(jsonObject, NUTRICIONISTA_ID));
            this.dataInicio = JSONHandler.getStringFromJSONObject(jsonObject, DATA_INICIO);
            this.dataFim = JSONHandler.getStringFromJSONObject(jsonObject, DATA_FIM);
            this.objetivoLongo = JSONHandler.getStringFromJSONObject(jsonObject, OBJETIVO_LONGO);
            this.instrucao = JSONHandler.getStringFromJSONObject(jsonObject, INSTRUCAO);

        }
    }

    public ContentValues toContentValues( boolean withId )
    {

        ContentValues contentValues = new ContentValues();

        if( withId )
            contentValues.put( ID, id );

        contentValues.put( PACIENTE_ID, pacienteId );
        contentValues.put( TIPO_DIETA_ID, tipoDietaId );
        contentValues.put( NUTRICIONISTA_ID, nutricionistaId );
        contentValues.put( DATA_INICIO, dataInicio );
        contentValues.put( DATA_FIM, dataFim );
        contentValues.put(OBJETIVO_LONGO, objetivoLongo );
        contentValues.put( INSTRUCAO, instrucao );

        return contentValues;

    }

}