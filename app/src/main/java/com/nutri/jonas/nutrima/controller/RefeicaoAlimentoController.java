package com.nutri.jonas.nutrima.controller;

import android.content.Context;

import com.nutri.jonas.nutrima.model.RefeicaoAlimento;

import java.util.ArrayList;
import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class RefeicaoAlimentoController extends Controller
{


    public RefeicaoAlimentoController( Context context )
    {

        super( context, RefeicaoAlimento.TABLE_NAME, RefeicaoAlimento.ID );

    }

    public boolean insertRefeicaoAlimento( RefeicaoAlimento refeicaoAlimento )
    {

        return insert( refeicaoAlimento.toContentValues( false ) );

    }

    public boolean updateRefeicaoAlimento( RefeicaoAlimento refeicaoAlimento )
    {

        return update( refeicaoAlimento.toContentValues( true ) );

    }

    public boolean insertRefeicaoAlimentoWithId( RefeicaoAlimento refeicaoAlimento )
    {

        return insert( refeicaoAlimento.toContentValues( true ) );

    }

    public RefeicaoAlimento getRefeicaoAlimentoById( long id )
    {

        HashMap<String, String> tempRefeicaoAlimento = selectById( id );

        if( tempRefeicaoAlimento != null )
            return new RefeicaoAlimento( tempRefeicaoAlimento );

        return  null;

    }

    public ArrayList<RefeicaoAlimento> getAllRefeicaoAlimentoByRefeicaoId( long refeicaoId )
    {

        String whereClause =  RefeicaoAlimento.OPCAO_REFEICAO_ID + "=" + refeicaoId;

        return fromArrayListHashMapToArrayListRefeicaoAlimento( selectAll( whereClause, null ) );

    }

    private ArrayList<RefeicaoAlimento> fromArrayListHashMapToArrayListRefeicaoAlimento(ArrayList<HashMap<String,String>> arrayListHashMap )
    {
        ArrayList<RefeicaoAlimento> arrayList = null;

        if( arrayListHashMap != null ) {

            arrayList = new ArrayList<RefeicaoAlimento>();

            for (int i = 0; i < arrayListHashMap.size(); i++) {

                arrayList.add(new RefeicaoAlimento(arrayListHashMap.get(i)));

            }

        }

        return arrayList;

    }

}