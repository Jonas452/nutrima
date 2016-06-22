package com.nutri.jonas.nutrima.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nutri.jonas.nutrima.R;
import com.nutri.jonas.nutrima.controller.TipoRefeicaoController;
import com.nutri.jonas.nutrima.model.Refeicao;
import com.nutri.jonas.nutrima.model.TipoRefeicao;

import java.util.ArrayList;

public class RefeicaoAdapter extends ArrayAdapter<Refeicao>
{

    Context context;
    int resource;
    ArrayList<Refeicao> refeicaoArrayList;

    TipoRefeicaoController tipoRefeicaoController;

    public RefeicaoAdapter(Context context, int resource, ArrayList<Refeicao> objects)
    {

        super( context, resource, objects );

        this.context = context;
        this.resource = resource;
        this.refeicaoArrayList = objects;

        tipoRefeicaoController = new TipoRefeicaoController( context );

    }

    private class ViewHolder
    {

        TextView idRefeicaoAdapterTextView,
                tipoRefeicaoRefeicaoAdapterTextView,
                horarioRefeicaoAdapterTextView,
                observacaoRefeicaoAdapterTextView;

        LinearLayout refeicaoAdapterLinearLayout;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
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

        holder.refeicaoAdapterLinearLayout = (LinearLayout) row.findViewById( R.id.refeicaoAdapterLinearLayout );

        holder.idRefeicaoAdapterTextView = (TextView) row.findViewById( R.id.idRefeicaoAdapterTextView );
        holder.tipoRefeicaoRefeicaoAdapterTextView = (TextView) row.findViewById( R.id.tipoRefeicaoRefeicaoAdapterTextView );
        holder.horarioRefeicaoAdapterTextView = (TextView) row.findViewById( R.id.horarioRefeicaoAdapterTextView );
        holder.observacaoRefeicaoAdapterTextView = (TextView) row.findViewById( R.id.observacaoRefeicaoAdapterTextView );

        return holder;

    }

    private void setViewHolderFieldsValues( ViewHolder holder, int position )
    {

        Refeicao tempRefeicao = refeicaoArrayList.get( position );

        TipoRefeicao tipoRefeicao = tipoRefeicaoController.getTipoRefeicaoById( tempRefeicao.tipoRefeicaoId );

        holder.idRefeicaoAdapterTextView.setText( String.valueOf( tempRefeicao.id ) );
        holder.tipoRefeicaoRefeicaoAdapterTextView.setText( tipoRefeicao.tipo  );
        holder.horarioRefeicaoAdapterTextView.setText( tempRefeicao.horario );
        holder.observacaoRefeicaoAdapterTextView.setText( tempRefeicao.observacao );

    }

}
