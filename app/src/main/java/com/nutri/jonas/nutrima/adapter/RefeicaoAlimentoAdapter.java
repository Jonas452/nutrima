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
import com.nutri.jonas.nutrima.controller.AlimentoController;
import com.nutri.jonas.nutrima.controller.RefeicaoController;
import com.nutri.jonas.nutrima.controller.TipoQuantidadeController;
import com.nutri.jonas.nutrima.model.Alimento;
import com.nutri.jonas.nutrima.model.Refeicao;
import com.nutri.jonas.nutrima.model.RefeicaoAlimento;
import com.nutri.jonas.nutrima.model.TipoQuantidade;

import java.util.ArrayList;

/**
 * Created by JONAS on 26/05/2016.
 */
public class RefeicaoAlimentoAdapter extends ArrayAdapter<RefeicaoAlimento>
{

    Context context;
    int resource;
    ArrayList<RefeicaoAlimento> refeicaoAlimentoArrayList;

    AlimentoController alimentoController;
    TipoQuantidadeController tipoQuantidadeController;

    public RefeicaoAlimentoAdapter( Context context, int resource, ArrayList<RefeicaoAlimento> refeicaoAlimentoArrayList )
    {

        super( context, resource, refeicaoAlimentoArrayList );

        this.context = context;
        this.resource = resource;
        this.refeicaoAlimentoArrayList = refeicaoAlimentoArrayList;

        alimentoController = new AlimentoController( context );
        tipoQuantidadeController = new TipoQuantidadeController( context );

    }

    private class ViewHolder
    {

        TextView idRefeicaoAlimentoAdapterTextView,
                alimentoRefeicaoAlimentoAdapterTextView,
                observacaoRefeicaoAlimentoAdapterTextView,
                quantidadeRefeicaoAlimentoAdapterTextView;

        LinearLayout refeicaoAlimentoAdapterLinearLayout;

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

        holder.refeicaoAlimentoAdapterLinearLayout = (LinearLayout) row.findViewById( R.id.refeicaoAlimentoAdapterLinearLayout );

        holder.idRefeicaoAlimentoAdapterTextView = (TextView) row.findViewById( R.id.idRefeicaoAlimentoAdapterTextView );
        holder.alimentoRefeicaoAlimentoAdapterTextView = (TextView) row.findViewById( R.id.alimentoRefeicaoAlimentoAdapterTextView );
        holder.observacaoRefeicaoAlimentoAdapterTextView = (TextView) row.findViewById( R.id.observacaoRefeicaoAlimentoAdapterTextView );
        holder.quantidadeRefeicaoAlimentoAdapterTextView = (TextView) row.findViewById( R.id.quantidadeRefeicaoAlimentoAdapterTextView );

        return holder;

    }

    private void setViewHolderFieldsValues( ViewHolder holder, int position )
    {

        RefeicaoAlimento tempRefeicaoAlimento = refeicaoAlimentoArrayList.get( position );

        Alimento alimento = alimentoController.getAlimentoById( tempRefeicaoAlimento.alimentoId );
        TipoQuantidade tipoQuantidade = tipoQuantidadeController.getTipoQuantidadeById( tempRefeicaoAlimento.tipoQuantidadeId );

        holder.idRefeicaoAlimentoAdapterTextView.setText( String.valueOf( tempRefeicaoAlimento.id ) );
        holder.alimentoRefeicaoAlimentoAdapterTextView.setText( alimento.nome  );
        holder.quantidadeRefeicaoAlimentoAdapterTextView.setText( tempRefeicaoAlimento.quantidade + " " + tipoQuantidade.tipo );
        holder.observacaoRefeicaoAlimentoAdapterTextView.setText( tempRefeicaoAlimento.observacao );

    }

}
