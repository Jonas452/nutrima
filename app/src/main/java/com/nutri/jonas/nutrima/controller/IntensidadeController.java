package com.nutri.jonas.nutrima.controller;

import android.content.Context;

import com.nutri.jonas.nutrima.model.Intensidade;

import java.util.ArrayList;
import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class IntensidadeController extends Controller
{

    public IntensidadeController( Context context )
    {

        super( context, Intensidade.TABLE_NAME, Intensidade.ID );

    }

    public boolean insertIntensidade( Intensidade intensidade )
    {

        return insert(intensidade.toContentValues( false ) );

    }

    public boolean insertIntensidadeWithId( Intensidade intensidade )
    {

        return insert(intensidade.toContentValues( true ) );

    }

    public boolean updateIntensidade( Intensidade intensidade )
    {

        return update( intensidade.toContentValues( true ) );

    }

    public ArrayList<Intensidade> getAllIntensidade()
    {

        return fromArrayListHashMapToArrayListIntensidade(selectAll( null, null ) );

    }

    public Intensidade getIntensidadeById( long id )
    {

        return new Intensidade( selectById( id ) );

    }

    private ArrayList<Intensidade> fromArrayListHashMapToArrayListIntensidade( ArrayList<HashMap<String,String>> arrayListHashMap )
    {

        ArrayList<Intensidade> arrayList = new ArrayList<Intensidade>();

        for( int i = 0; i < arrayListHashMap.size(); i++ )
        {

            arrayList.add( new Intensidade( arrayListHashMap.get( i ) ) ) ;

        }

        return arrayList;

    }

}