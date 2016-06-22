package com.nutri.jonas.nutrima.controller;

import android.content.Context;

import com.nutri.jonas.nutrima.model.Refeicao;

import java.util.ArrayList;
import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class RefeicaoController extends Controller
{

    public RefeicaoController( Context context )
    {

        super( context, Refeicao.TABLE_NAME, Refeicao.ID );

    }

    public boolean insertRefeicao( Refeicao refeicao )
    {

        return insert( refeicao.toContentValues( false ) );

    }

    public boolean updateRefeicao( Refeicao refeicao )
    {

        return update( refeicao.toContentValues( true ) );

    }

    public boolean insertRefeicaoWithId( Refeicao refeicao )
    {

        return insert( refeicao.toContentValues( true ) );

    }

    public Refeicao getRefeicaoById( long id )
    {

        HashMap<String, String> tempRefeicao = selectById( id );

        if( tempRefeicao != null )
            return new Refeicao( tempRefeicao );

        return  null;

    }

    public ArrayList<Refeicao> getAllRefeicaoByDietaId( long dietaId )
    {

        String whereClause =  Refeicao.DIETA_ID + "=" + dietaId;
        String orderClause = Refeicao.HORARIO;

        return fromArrayListHashMapToArrayListRefeicao( selectAll( whereClause, orderClause ) );

    }

    private ArrayList<Refeicao> fromArrayListHashMapToArrayListRefeicao(ArrayList<HashMap<String,String>> arrayListHashMap )
    {
        ArrayList<Refeicao> arrayList = null;

        if( arrayListHashMap != null ) {

            arrayList = new ArrayList<Refeicao>();

            for (int i = 0; i < arrayListHashMap.size(); i++) {

                arrayList.add(new Refeicao(arrayListHashMap.get(i)));

            }

        }

        return arrayList;

    }

}
