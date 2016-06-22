package com.nutri.jonas.nutrima.controller;

import android.content.Context;

import com.nutri.jonas.nutrima.model.Paciente;

import java.util.ArrayList;
import java.util.HashMap;

/*
Author = Jonas Jordão de Macêdo;
Creation Date = 02/10/2016 (m/d/y);
*/
public class PacienteController extends Controller
{

    public PacienteController( Context context )
    {

        super( context, Paciente.TABLE_NAME, Paciente.ID );

    }

    public boolean insertPaciente( Paciente paciente )
    {

        return insert( paciente.toContentValues( false ) );

    }

    public boolean updatePaciente( Paciente paciente )
    {

        return update( paciente.toContentValues( true ) );

    }

    public Paciente getPacienteById( long id )
    {

        HashMap<String, String> tempPaciente = selectById( id );

        if( tempPaciente != null )
            return new Paciente( tempPaciente );

        return  null;

    }

    public ArrayList< HashMap<String, String> > getPacienteByTelefone( String telefone )
    {

        String whereClause = Paciente.TELEFONE + "=" + telefone;
        return new ArrayList< HashMap<String, String> >( selectAll( whereClause, null ) );

    }

}