package com.nutri.jonas.nutrima.controller;

import android.content.Context;

import com.nutri.jonas.nutrima.model.Consulta;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class ConsultaController extends Controller
{

    public ConsultaController( Context context )
    {

        super( context, Consulta.TABLE_NAME, Consulta.ID );

    }

    public boolean insertConsulta( Consulta consulta )
    {

        return insert( consulta.toContentValues() );

    }

    public boolean updateConsulta( Consulta consulta )
    {

        return update( consulta.toContentValues() );

    }

    public Consulta getConsultaById( long id )
    {

        return new Consulta( selectById( id ) );

    }

}