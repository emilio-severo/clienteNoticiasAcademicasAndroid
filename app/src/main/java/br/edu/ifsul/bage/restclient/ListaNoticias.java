package br.edu.ifsul.bage.restclient;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.edu.ifsul.bage.clientrest.RestClient;
import br.edu.ifsul.bage.clientrest.RestClientInterface;
import br.edu.ifsul.bage.modelo.Noticia;

public class ListaNoticias extends ListActivity {

    private List<Noticia> noticias;
    private ArrayAdapter<Noticia> arrayAdapterNoticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        carregarNoticias();
        arrayAdapterNoticias = new ArrayAdapter(this, android.R.layout.simple_list_item_1, noticias);
        this.setListAdapter(arrayAdapterNoticias);
    }

    private void carregarNoticias(){
        Task task = new Task();
        try {
            noticias = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Notícias carregadas.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Noticia noticia = (Noticia) l.getItemAtPosition(position);
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("noticia", noticia);
        startActivity(intent);
    }

    private class Task extends AsyncTask<Void, Void, List<Noticia>> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ListaNoticias.this);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Por favor, aguarde.");
            progressDialog.setMessage("Estabelecendo comunicação.");
            progressDialog.show();
        }

        @Override
        protected List<Noticia> doInBackground(Void... params) {
            List<Noticia> n;
            RestClientInterface<Noticia> cliente = new RestClient<>("http://192.168.1.105:8080/noticiasAcademicas/servicos/noticias/todas", RestClientInterface.TYPE_JSON, Noticia[].class);
            n = cliente.get();
            return n;
        }

        @Override
        protected void onPostExecute(List<Noticia> noticias) {
            super.onPostExecute(noticias);
            progressDialog.dismiss();
        }

    }


}

