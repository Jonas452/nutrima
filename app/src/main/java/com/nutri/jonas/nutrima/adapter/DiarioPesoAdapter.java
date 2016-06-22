package com.nutri.jonas.nutrima.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nutri.jonas.nutrima.R;
import com.nutri.jonas.nutrima.model.DiarioPeso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DiarioPesoAdapter extends ArrayAdapter<DiarioPeso>
{

    Context context;
    int resource;
    ArrayList<DiarioPeso> diarioPesoArrayList;

    public DiarioPesoAdapter(Context context, int resource, ArrayList<DiarioPeso> objects)
    {

        super( context, resource, objects );

        this.context = context;
        this.resource = resource;
        this.diarioPesoArrayList = objects;

    }

    private class ViewHolder
    {

        TextView idPesoAdaptertextView,
                pesoPesoAdaptertextView,
                dataPesoAdapterTextView,
                imcPesoAdapterTextView;

        LinearLayout pesoAdapterLinearLayout;

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

        holder.pesoAdapterLinearLayout = (LinearLayout) row.findViewById( R.id.pesoAdapterLinearLayout );

        holder.idPesoAdaptertextView = (TextView) row.findViewById( R.id.idPesoAdaptertextView );
        holder.pesoPesoAdaptertextView = (TextView) row.findViewById( R.id.pesoPesoAdaptertextView );
        holder.dataPesoAdapterTextView = (TextView) row.findViewById( R.id.dataPesoAdapterTextView );
        holder.imcPesoAdapterTextView = (TextView) row.findViewById( R.id.imcPesoAdapterTextView );

        return holder;

    }

    private void setViewHolderFieldsValues( ViewHolder holder, int position )
    {

        DiarioPeso tempDiarioPeso = diarioPesoArrayList.get( position );

        DecimalFormat df = new DecimalFormat("0.##");
        tempDiarioPeso.imc = Double.valueOf( df.format( tempDiarioPeso.imc ) );
        Log.e( "Teste", df.format( tempDiarioPeso.imc ) );

        holder.idPesoAdaptertextView.setText( String.valueOf( tempDiarioPeso.id ) );
        holder.pesoPesoAdaptertextView.setText( String.valueOf( tempDiarioPeso.peso ) + "kg" );
        holder.dataPesoAdapterTextView.setText( tempDiarioPeso.dataPesagem );
        holder.imcPesoAdapterTextView.setText( String.valueOf(tempDiarioPeso.imc + " imc" ) );

    }

    private void setLayoutBackGroundColor( ViewHolder holder, int position )
    {

        if( position != 0 && position % 2 != 0 )
        {

            holder.pesoAdapterLinearLayout.setBackgroundColor( Color.parseColor( "#7070db" ) );

        }

    }

}