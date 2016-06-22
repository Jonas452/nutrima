package com.nutri.jonas.nutrima.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nutri.jonas.nutrima.R;
import com.nutri.jonas.nutrima.controller.AtividadeFisicaController;
import com.nutri.jonas.nutrima.controller.IntensidadeController;
import com.nutri.jonas.nutrima.model.DiarioAtividade;

import java.util.ArrayList;

/**
 * Created by JONAS on 3/16/2016.
 */
public class DiarioAtividadeAdapter extends ArrayAdapter<DiarioAtividade>
{

    Context context;
    int resource;
    ArrayList<DiarioAtividade> objects;

    AtividadeFisicaController atividadeFisicaController;
    IntensidadeController intensidadeController;

    public DiarioAtividadeAdapter(Context context, int resource, ArrayList<DiarioAtividade> objects)
    {

        super( context, resource, objects );

        this.context = context;
        this.resource = resource;
        this.objects = objects;

        atividadeFisicaController = new AtividadeFisicaController( context );
        intensidadeController = new IntensidadeController( context );


    }

    private class ViewHolder
    {

        TextView idAtividadeAdapterTextView,
                atividadeAtividadeAdapterTextView,
                dataAtividadeAdapterTextView,
                duracaoAtividadeAdapterTextView;

        LinearLayout atividadeAdapterLinearLayout;

    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent)
    {

        return getCustomView( position, convertView, parent );

    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {

        return getCustomView( position, convertView, parent );

    }

    private View getCustomView( int position, View convertView, ViewGroup parent )
    {

        View row = convertView;

        ViewHolder holder = null;

        if( row == null )
        {

            LayoutInflater mInflater = ( (Activity) context ).getLayoutInflater();
            row = mInflater.inflate( resource, parent, false );

            holder =  prepareViewHolder( row );

            row.setTag( holder );

        }else
        {

            holder = ( ViewHolder ) row.getTag();

        }

        setViewHolderFieldsValues( holder, position );
        //setLayoutBackGroundColor( holder, position );

        return row;

    }

    private ViewHolder prepareViewHolder( View row )
    {

        ViewHolder holder = new ViewHolder();

        holder.atividadeAdapterLinearLayout = (LinearLayout) row.findViewById( R.id.atividadeAdapterLinearLayout );

        holder.idAtividadeAdapterTextView = (TextView) row.findViewById( R.id.idAtividadeAdapterTextView );
        holder.atividadeAtividadeAdapterTextView = (TextView) row.findViewById( R.id.atividadeAtividadeAdapterTextView );
        holder.dataAtividadeAdapterTextView = (TextView) row.findViewById( R.id.dataAtividadeAdapterTextView );
        holder.duracaoAtividadeAdapterTextView = (TextView) row.findViewById( R.id.duracaoAtividadeAdapterTextView );

        return holder;

    }

    private void setViewHolderFieldsValues( ViewHolder holder, int position )
    {

        DiarioAtividade tempDiarioAtividade = objects.get( position );

        String atividade = atividadeFisicaController.getAtividadeFisicaById( tempDiarioAtividade.atividadeFisicaId ).nome;
        String intensidade = intensidadeController.getIntensidadeById( tempDiarioAtividade.atividadeIntensidadeId ).intensidade;

        holder.idAtividadeAdapterTextView.setText( String.valueOf( tempDiarioAtividade.id ) );
        holder.atividadeAtividadeAdapterTextView.setText( atividade + " - " + intensidade );
        holder.dataAtividadeAdapterTextView.setText( tempDiarioAtividade.dataRealizada );
        holder.duracaoAtividadeAdapterTextView.setText( tempDiarioAtividade.duracao + "mins" );

    }

    private void setLayoutBackGroundColor( ViewHolder holder, int position )
    {

        if( position != 0 && position % 2 != 0 )
        {

            holder.atividadeAdapterLinearLayout.setBackgroundColor( Color.parseColor("#7070db") );

        }

    }

}