package com.nutri.jonas.nutrima.controller;

import android.content.Context;

import com.nutri.jonas.nutrima.model.AtividadeFisica;
import com.nutri.jonas.nutrima.model.Dieta;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class DietaController extends  Controller
{

    public DietaController( Context context )
    {

        super( context, Dieta.TABLE_NAME, Dieta.ID );

    }

    public boolean insertDieta( Dieta dieta )
    {

        return insert( dieta.toContentValues( false ) );

    }

    public boolean insertDietaWith( Dieta dieta )
    {

        return insert( dieta.toContentValues( true ) );

    }

    public boolean updateDieta( Dieta dieta )
    {

        return update( dieta.toContentValues(true) );

    }

    public Dieta getDietaById( long id )
    {

        return new Dieta( selectById( id ) );

    }

}