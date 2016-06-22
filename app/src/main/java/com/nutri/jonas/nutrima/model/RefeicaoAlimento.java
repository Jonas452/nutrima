package com.nutri.jonas.nutrima.model;

import android.content.ContentValues;

import com.nutri.jonas.nutrima.util.JSONHandler;

import org.json.JSONObject;

import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);

NO MODIFICATIONS WHERE MADE.
*/
public class RefeicaoAlimento
{

    public final static String TABLE_NAME = "RefeicaoAlimento";

    public final static String ID = "id";
    public final static String ALIMENTO_ID = "alimentoId";
    public final static String TIPO_QUANTIDADE_ID = "tipoQuantidadeId";
    public final static String OPCAO_REFEICAO_ID = "opcaoRefeicaoId";
    public final static String QUANTIDADE = "quantidade";
    public final static String AGRUPAMENTO = "agrupamento";
    public final static String OBSERVACAO = "observacao";

    public final static String CREATE_TABLE_SQL_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ALIMENTO_ID + " INTEGER NOT NULL, " +
                    TIPO_QUANTIDADE_ID + " INTEGER NOT NULL, " +
                    OPCAO_REFEICAO_ID + " INTEGER NOT NULL, " +
                    QUANTIDADE + " INTEGER NOT NULL, " +
                    AGRUPAMENTO + " INTEGER NOT NULL, " +
                    OBSERVACAO + " TEXT );";

    public long id;
    public long alimentoId;
    public long tipoQuantidadeId;
    public long opcaoRefeicaoId;
    public int quantidade;
    public int agrupamento;
    public String observacao;

    public RefeicaoAlimento() {}

    public RefeicaoAlimento( HashMap<String, String> refeicaoAlimentoHashMap )
    {

        this.id = Long.parseLong( refeicaoAlimentoHashMap.get( ID ) );
        this.alimentoId = Long.parseLong( refeicaoAlimentoHashMap.get( ALIMENTO_ID ) );
        this.tipoQuantidadeId = Long.parseLong( refeicaoAlimentoHashMap.get( TIPO_QUANTIDADE_ID ) );
        this.opcaoRefeicaoId = Long.parseLong( refeicaoAlimentoHashMap.get( OPCAO_REFEICAO_ID ) );
        this.quantidade = Integer.parseInt( refeicaoAlimentoHashMap.get( QUANTIDADE ) );
        this.agrupamento = Integer.parseInt( refeicaoAlimentoHashMap.get( AGRUPAMENTO ) );
        this.observacao = refeicaoAlimentoHashMap.get( OBSERVACAO );

    }

    public RefeicaoAlimento( JSONObject jsonObject )
    {

        this.id = Integer.parseInt( JSONHandler.getStringFromJSONObject( jsonObject, ID ) );
        this.alimentoId = Integer.parseInt( JSONHandler.getStringFromJSONObject( jsonObject, ALIMENTO_ID ) );
        this.tipoQuantidadeId = Integer.parseInt( JSONHandler.getStringFromJSONObject( jsonObject, TIPO_QUANTIDADE_ID ) );
        this.opcaoRefeicaoId = Integer.parseInt( JSONHandler.getStringFromJSONObject( jsonObject, OPCAO_REFEICAO_ID ) );
        this.quantidade = Integer.parseInt( JSONHandler.getStringFromJSONObject( jsonObject, QUANTIDADE ) );
        this.agrupamento = Integer.parseInt( JSONHandler.getStringFromJSONObject( jsonObject, AGRUPAMENTO ) );
        this.observacao = JSONHandler.getStringFromJSONObject( jsonObject, OBSERVACAO );

    }

    public ContentValues toContentValues( boolean withId )
    {

        ContentValues contentValues = new ContentValues();

        if( withId )
            contentValues.put( ID, id );

        contentValues.put( ALIMENTO_ID, alimentoId );
        contentValues.put( TIPO_QUANTIDADE_ID, tipoQuantidadeId );
        contentValues.put( OPCAO_REFEICAO_ID, opcaoRefeicaoId );
        contentValues.put( QUANTIDADE, quantidade );
        contentValues.put( AGRUPAMENTO, agrupamento );
        contentValues.put( OBSERVACAO, observacao );

        return contentValues;

    }

}