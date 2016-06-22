package com.nutri.jonas.nutrima.controller;

import android.content.Context;

import com.nutri.jonas.nutrima.model.Consultorio;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class ConsultorioController extends Controller
{

    public ConsultorioController( Context context )
    {

        super( context, Consultorio.TABLE_NAME, Consultorio.ID );

    }

    public boolean insertConsultorio( Consultorio consultorio )
    {

        return insert( consultorio.toContentValues() );

    }

    public boolean updateConsultorio( Consultorio consultorio )
    {

        return update( consultorio.toContentValues() );

    }

    public Consultorio getConsultorioById( long id )
    {

        return new Consultorio( selectById( id ) );

    }

}
