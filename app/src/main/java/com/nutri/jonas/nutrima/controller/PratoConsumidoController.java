package com.nutri.jonas.nutrima.model;

import android.content.Context;

import com.nutri.jonas.nutrima.controller.Controller;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class PratoConsumidoController extends Controller
{

    public PratoConsumidoController( Context context )
    {

        super( context, PratoConsumido.TABLE_NAME, PratoConsumido.ID );

    }

    public boolean insertPratoConsumido( PratoConsumido pratoConsumido )
    {

        return insert( pratoConsumido.toContentValues() );

    }

}