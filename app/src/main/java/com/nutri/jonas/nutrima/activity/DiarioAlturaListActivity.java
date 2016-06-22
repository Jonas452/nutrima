package com.nutri.jonas.nutrima.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nutri.jonas.nutrima.R;
import com.nutri.jonas.nutrima.adapter.DiarioAlturaAdapter;
import com.nutri.jonas.nutrima.controller.DiarioAlturaController;
import com.nutri.jonas.nutrima.model.DiarioAltura;

import java.util.ArrayList;

public class DiarioAlturaListActivity extends AppCompatActivity
{

    private ListView medicoesListView;
    private TextView semDadosAlturaListTextView;

    private DiarioAlturaController diarioAlturaController;

    private long alturaId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario_altura_list);

        diarioAlturaController = new DiarioAlturaController( getApplicationContext() );

        medicoesListView = (ListView) findViewById( R.id.medicoesListView );
        semDadosAlturaListTextView = (TextView) findViewById( R.id.semDadosAlturaListTextView );

        medicoesListView.setOnItemClickListener( medicoesListViewOnItemClickListener );
        medicoesListView.setOnItemLongClickListener( medicoesListOnItemLongClickListener );

    }

    @Override
    protected void onResume()
    {

        super.onResume();

        new CarregarDiarioAltura().execute();

    }

    AdapterView.OnItemClickListener medicoesListViewOnItemClickListener = new AdapterView.OnItemClickListener()
    {

        @Override
        public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3)
        {

            Intent intent = new Intent( DiarioAlturaListActivity.this, DiarioAlturaDetalheActivity.class );

            intent.putExtra( "alturaId", Long.parseLong( ( (TextView) v.findViewById( R.id.idAlturaAdapterTextView ) ).getText().toString() ) );
            intent.putExtra( "isEditingScreen", true );

            startActivity( intent );

        }

    };

    AdapterView.OnItemLongClickListener medicoesListOnItemLongClickListener = new AdapterView.OnItemLongClickListener()
    {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
        {

            alturaId = Long.parseLong( ( (TextView) view.findViewById( R.id.idAlturaAdapterTextView ) ).getText().toString() );

            AlertDialog.Builder builder = new AlertDialog.Builder( DiarioAlturaListActivity.this );

            builder.setTitle( "AVISO" );
            builder.setMessage( "Tem certeza que deseja EXCLUIR esse dado?" );
            builder.setPositiveButton("Sim", dialogClickListener);
            builder.setNegativeButton("Não", dialogClickListener);

            builder.show();

            return false;
        }


    };

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
    {

        @Override
        public void onClick(DialogInterface dialog, int which)
        {

            switch( which )
            {
                case DialogInterface.BUTTON_POSITIVE:

                    boolean deleted = diarioAlturaController.deleteDiarioAltura(alturaId );

                    if( deleted )
                    {

                        Toast.makeText(DiarioAlturaListActivity.this, "Dados excluídos com sucesso.", Toast.LENGTH_LONG).show();

                        new CarregarDiarioAltura().execute();

                    }else
                    {

                        Toast.makeText( DiarioAlturaListActivity.this, "Ocorreu um error ao excluír os dados.", Toast.LENGTH_LONG).show();

                    }

                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }

        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch( item.getItemId() )
        {

            case R.id.action_add:

                Intent intent = new Intent( DiarioAlturaListActivity.this, DiarioAlturaDetalheActivity.class );

                startActivity( intent );

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private class CarregarDiarioAltura extends AsyncTask< Long, Void, ArrayList<DiarioAltura> >
    {

        @Override
        protected ArrayList<DiarioAltura> doInBackground( Long... params )
        {

            return diarioAlturaController.getAllDiarioAltura();

        }

        @Override
        protected void onPostExecute( ArrayList<DiarioAltura> result )
        {

            super.onPostExecute( result );

            if( result != null && result.size() > 0 )
            {

                //Não existe dados.
                semDadosAlturaListTextView.setVisibility( View.GONE );

                //Instancia o adapter.
                DiarioAlturaAdapter alturaAdapter = new DiarioAlturaAdapter( DiarioAlturaListActivity.this, R.layout.altura_adapter, result );

                //Set o adapter.
                medicoesListView.setAdapter( alturaAdapter );

            }else
            {

                //Não existe dados.
                semDadosAlturaListTextView.setVisibility( View.VISIBLE );

                //Set o adapter.
                medicoesListView.setAdapter( null );

            }

        }

    }


}