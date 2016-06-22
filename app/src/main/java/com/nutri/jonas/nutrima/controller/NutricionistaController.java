package com.nutri.jonas.nutrima.controller;

import android.content.Context;

import com.nutri.jonas.nutrima.model.Nutricionista;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class NutricionistaController extends Controller
{

    public NutricionistaController( Context context )
    {

        super( context, Nutricionista.TABLE_NAME, Nutricionista.ID );

    }

    public boolean insertNutricionista( Nutricionista nutricionista )
    {

        return insert( nutricionista.toContentValues() );

    }

    public boolean updateNutricionista( Nutricionista nutricionista )
    {

        return insert( nutricionista.toContentValues() );

    }

    public Nutricionista getNutrionistaById( long id )
    {

        return new Nutricionista( selectById( id ) );

    }

}