package com.nutri.jonas.nutrima.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.nutri.jonas.nutrima.R;
import com.nutri.jonas.nutrima.adapter.RefeicaoAlimentoAdapter;
import com.nutri.jonas.nutrima.controller.RefeicaoAlimentoController;
import com.nutri.jonas.nutrima.controller.RefeicaoController;
import com.nutri.jonas.nutrima.controller.TipoRefeicaoController;
import com.nutri.jonas.nutrima.model.RefeicaoAlimento;

import java.util.ArrayList;

public class RefeicaoAlimentoListActivity extends AppCompatActivity
{

    private TextView refeicaoAlimentoTextView, semDadosRefeicaoAlimentoListTextView;
    private ListView refeicoesAlimentoListView;

    long refeicaoId;
    private Bundle extras;

    private RefeicaoAlimentoController refeicaoAlimentoController;
    private RefeicaoController refeicaoController;
    private TipoRefeicaoController tipoRefeicaoController;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeicao_alimento_list);

        refeicaoAlimentoController = new RefeicaoAlimentoController( getApplicationContext() );
        refeicaoController = new RefeicaoController( getApplicationContext() );
        tipoRefeicaoController = new TipoRefeicaoController( getApplicationContext() );

        refeicaoAlimentoTextView = (TextView) findViewById( R.id.refeicaoAlimentoTextView);
        semDadosRefeicaoAlimentoListTextView = (TextView) findViewById( R.id.semDadosRefeicaoAlimentoListTextView );
        refeicoesAlimentoListView = (ListView) findViewById( R.id.refeicoesAlimentoListView );

        if( getIntent().getExtras() != null )
        {

            extras = getIntent().getExtras();

            refeicaoId = extras.getLong( "refeicaoId" );

            refeicaoAlimentoTextView.setText( ( tipoRefeicaoController.getTipoRefeicaoById( ( refeicaoController.getRefeicaoById( refeicaoId ) ).tipoRefeicaoId ) ).tipo );

        }

    }

    @Override
    protected void onResume()
    {

        super.onResume();

        new CarregarRefeicaoAlimento().execute();

    }

    private class CarregarRefeicaoAlimento extends AsyncTask< Long, Void, ArrayList<RefeicaoAlimento> >
    {

        @Override
        protected ArrayList<RefeicaoAlimento> doInBackground(Long... params )
        {

            return refeicaoAlimentoController.getAllRefeicaoAlimentoByRefeicaoId( refeicaoId );

        }

        @Override
        protected void onPostExecute( ArrayList<RefeicaoAlimento> result )
        {

            super.onPostExecute( result );

            if( result != null && result.size() > 0 )
            {

                //Não existe dados.
                semDadosRefeicaoAlimentoListTextView.setVisibility( View.GONE );

                //Instancia o adapter.
                RefeicaoAlimentoAdapter refeicaoAdapter = new RefeicaoAlimentoAdapter( RefeicaoAlimentoListActivity.this, R.layout.refeicao_alimento_adapter, result );

                //Set o adapter.
                refeicoesAlimentoListView.setAdapter( refeicaoAdapter );

            }else
            {

                //Não existe dados.
                semDadosRefeicaoAlimentoListTextView.setVisibility( View.VISIBLE );

                //Set o adapter.
                refeicoesAlimentoListView.setAdapter( null );

            }

        }

    }

}
