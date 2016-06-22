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
import com.nutri.jonas.nutrima.model.AtividadeFisica;

import java.util.ArrayList;

/**
 * Created by JONAS on 3/16/2016.
 */
public class AtividadeFisicaSpinnerAdapter extends ArrayAdapter<AtividadeFisica>
{

    Context context;
    int resource;
    ArrayList<AtividadeFisica> atividadeFisicaArrayList;

    public AtividadeFisicaSpinnerAdapter(Context context, int resource, ArrayList<AtividadeFisica> objects)
    {

        super( context, resource, objects );

        this.context = context;
        this.resource = resource;
        this.atividadeFisicaArrayList = objects;

    }

    private class ViewHolder
    {

        TextView idTextView,
                labelTextView;

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

        return row;

    }

    private ViewHolder prepareViewHolder( View row )
    {

        ViewHolder holder = new ViewHolder();

        holder.idTextView = (TextView) row.findViewById( R.id.idTextView );
        holder.labelTextView = (TextView) row.findViewById( R.id.labelTextView );

        return holder;

    }

    private void setViewHolderFieldsValues( ViewHolder holder, int position )
    {

        AtividadeFisica tempAtividadeFisica = atividadeFisicaArrayList.get( position );

        holder.idTextView.setText( String.valueOf( tempAtividadeFisica.id ) );
        holder.labelTextView.setText( String.valueOf( tempAtividadeFisica.nome ) );

    }

    public int getPosition( String value ) {

        int position = 0;

        for (int i = 0; i < atividadeFisicaArrayList.size(); i++)
        {

            if( atividadeFisicaArrayList.get(i).nome.equals( value)) {

                position = i;
                break;

            }

        }

        return position;

    }

}