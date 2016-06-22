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
import com.nutri.jonas.nutrima.model.DiarioAltura;
import com.nutri.jonas.nutrima.model.DiarioPeso;

import java.util.ArrayList;


public class DiarioAlturaAdapter extends ArrayAdapter<DiarioAltura>
{

    Context context;
    int resource;
    ArrayList<DiarioAltura> diarioAlturaArrayList;

    public DiarioAlturaAdapter(Context context, int resource, ArrayList<DiarioAltura> objects)
    {

        super( context, resource, objects );

        this.context = context;
        this.resource = resource;
        this.diarioAlturaArrayList = objects;

    }

    private class ViewHolder
    {

        TextView idAlturaAdapterTextView,
                 alturaAlturaAdapterTextView,
                 dataAlturaAdapterTextView;

        LinearLayout alturaAdapterLinearLayout;

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

        holder.alturaAdapterLinearLayout = (LinearLayout) row.findViewById( R.id.pesoAdapterLinearLayout );

        holder.idAlturaAdapterTextView = (TextView) row.findViewById( R.id.idAlturaAdapterTextView );
        holder.alturaAlturaAdapterTextView = (TextView) row.findViewById( R.id.alturaAlturaAdapterTextView );
        holder.dataAlturaAdapterTextView = (TextView) row.findViewById( R.id.dataAlturaAdapterTextView );

        return holder;

    }

    private void setViewHolderFieldsValues( ViewHolder holder, int position )
    {

        DiarioAltura tempDiarioAltura = diarioAlturaArrayList.get( position );

        holder.idAlturaAdapterTextView.setText( String.valueOf( tempDiarioAltura.id ) );
        holder.alturaAlturaAdapterTextView.setText( tempDiarioAltura.altura + "m" );
        holder.dataAlturaAdapterTextView.setText( tempDiarioAltura.dataMedicao );

    }

    private void setLayoutBackGroundColor( ViewHolder holder, int position )
    {

        if( position != 0 && position % 2 != 0 )
        {

            holder.alturaAdapterLinearLayout.setBackgroundColor( Color.parseColor("#7070db") );

        }

    }

}