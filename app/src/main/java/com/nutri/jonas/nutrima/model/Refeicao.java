package com.nutri.jonas.nutrima.model;

import android.content.ContentValues;

import com.nutri.jonas.nutrima.util.JSONHandler;

import org.json.JSONObject;

import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class Refeicao
{

    public final static String TABLE_NAME = "Refeicao";

    public final static String ID = "id";
    public final static String TIPO_REFEICAO_ID = "tipoRefeicaoId";
    public final static String DIETA_ID = "dietaId";
    public final static String HORARIO = "horario";
    public final static String OBSERVACAO = "observacao";

    public final static String CREATE_TABLE_SQL_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TIPO_REFEICAO_ID + " INTEGER NOT NULL, " +
                    DIETA_ID + " INTEGER NOT NULL, " +
                    HORARIO + " TEXT NOT NULL, " +
                    OBSERVACAO + " TEXT );";

    public long id;
    public long tipoRefeicaoId;
    public long dietaId;
    public String horario;
    public String observacao;

    public Refeicao() {}

    public Refeicao( HashMap<String, String> refeicaoHashMap )
    {

        this.id = Long.parseLong( refeicaoHashMap.get( ID ) );
        this.tipoRefeicaoId = Long.parseLong( refeicaoHashMap.get( TIPO_REFEICAO_ID ) );
        this.dietaId = Long.parseLong( refeicaoHashMap.get( DIETA_ID ) );
        this.horario = refeicaoHashMap.get( HORARIO );
        this.observacao = refeicaoHashMap.get( OBSERVACAO );

    }

    public Refeicao( JSONObject jsonObject )
    {

        this.id = Integer.parseInt( JSONHandler.getStringFromJSONObject( jsonObject, ID ) );
        this.tipoRefeicaoId = Integer.parseInt( JSONHandler.getStringFromJSONObject( jsonObject, TIPO_REFEICAO_ID ) );
        this.dietaId = Integer.parseInt( JSONHandler.getStringFromJSONObject( jsonObject, DIETA_ID ) );
        this.horario = JSONHandler.getStringFromJSONObject( jsonObject, HORARIO );
        this.observacao = JSONHandler.getStringFromJSONObject( jsonObject, OBSERVACAO );

    }

    public ContentValues toContentValues( boolean withId )
    {

        ContentValues contentValues = new ContentValues();

        if( withId )
            contentValues.put( ID, id );

        contentValues.put( TIPO_REFEICAO_ID, tipoRefeicaoId );
        contentValues.put( DIETA_ID, dietaId );
        contentValues.put( HORARIO, horario );
        contentValues.put( OBSERVACAO, observacao );

        return contentValues;

    }

}