package com.nutri.jonas.nutrima.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nutri.jonas.nutrima.model.Alimento;
import com.nutri.jonas.nutrima.model.AtividadeFisica;
import com.nutri.jonas.nutrima.model.Consulta;
import com.nutri.jonas.nutrima.model.Consultorio;
import com.nutri.jonas.nutrima.model.DiarioAltura;
import com.nutri.jonas.nutrima.model.DiarioAtividade;
import com.nutri.jonas.nutrima.model.DiarioPeso;
import com.nutri.jonas.nutrima.model.Dieta;
import com.nutri.jonas.nutrima.model.Intensidade;
import com.nutri.jonas.nutrima.model.Nutricionista;
import com.nutri.jonas.nutrima.model.Paciente;
import com.nutri.jonas.nutrima.model.Prato;
import com.nutri.jonas.nutrima.model.PratoConsumido;
import com.nutri.jonas.nutrima.model.PratoOpcao;
import com.nutri.jonas.nutrima.model.Refeicao;
import com.nutri.jonas.nutrima.model.RefeicaoAlimento;
import com.nutri.jonas.nutrima.model.TipoDieta;
import com.nutri.jonas.nutrima.model.TipoQuantidade;
import com.nutri.jonas.nutrima.model.TipoRefeicao;

public class DatabaseOpenHelper  extends SQLiteOpenHelper
{

    public DatabaseOpenHelper(Context context, String dataBaseName, SQLiteDatabase.CursorFactory factory, int version)
    {

        super(context, dataBaseName, factory, version );

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL( Alimento.CREATE_TABLE_SQL_STATEMENT );
        db.execSQL( AtividadeFisica.CREATE_TABLE_SQL_STATEMENT );
        db.execSQL( Consulta.CREATE_TABLE_SQL_STATEMENT );
        db.execSQL( Consultorio.CREATE_TABLE_SQL_STATEMENT );
        db.execSQL( DiarioAltura.CREATE_TABLE_SQL_STATEMENT );
        db.execSQL( DiarioAtividade.CREATE_TABLE_SQL_STATEMENT );
        db.execSQL( DiarioPeso.CREATE_TABLE_SQL_STATEMENT );
        db.execSQL( Dieta.CREATE_TABLE_SQL_STATEMENT );
        db.execSQL( Intensidade.CREATE_TABLE_SQL_STATEMENT );
        db.execSQL( Nutricionista.CREATE_TABLE_SQL_STATEMENT );
        db.execSQL( Paciente.CREATE_TABLE_SQL_STATEMENT );
        db.execSQL( Prato.CREATE_TABLE_SQL_STATEMENT );
        db.execSQL( PratoConsumido.CREATE_TABLE_SQL_STATEMENT );
        db.execSQL( PratoOpcao.CREATE_TABLE_SQL_STATEMENT );
        db.execSQL( Refeicao.CREATE_TABLE_SQL_STATEMENT );
        db.execSQL( RefeicaoAlimento.CREATE_TABLE_SQL_STATEMENT );
        db.execSQL( TipoDieta.CREATE_TABLE_SQL_STATEMENT );
        db.execSQL( TipoQuantidade.CREATE_TABLE_SQL_STATEMENT );
        db.execSQL( TipoRefeicao.CREATE_TABLE_SQL_STATEMENT );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

}