package br.edu.ifsul.bage.restclient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.edu.ifsul.bage.clientrest.RestClient;
import br.edu.ifsul.bage.clientrest.RestClientInterface;
import br.edu.ifsul.bage.modelo.Noticia;

public class MainActivity extends Activity {

    private EditText texto;
    private Button botaosalvar;
    private Button botaolistar;
    private Button botaoexcluir;
    private Noticia noticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = (EditText) findViewById(R.id.edNoticia);
        botaosalvar = (Button) findViewById(R.id.btSalvar);
        botaoexcluir = (Button) findViewById(R.id.btExcluir);
        botaolistar = (Button) findViewById(R.id.btListar);

        Intent intent = getIntent();
        noticia = (Noticia) intent.getSerializableExtra("noticia");
        if(noticia != null)
            texto.setText(noticia.getNoticia());

        botaosalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noticia == null) {
                    noticia = new Noticia(texto.getText().toString());
                    noticia.setHora(new SimpleDateFormat("hh:mm").format(noticia.getData()));
                    new Task().execute("POST");
                }
                else{
                    noticia.setNoticia(texto.getText().toString());
                    new Task().execute("PUT");
                }
                novaNoticia();
                Toast.makeText(getApplicationContext(), "Notícia salva.", Toast.LENGTH_SHORT).show();
            }
        });

        botaoexcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noticia != null){
                    new Task().execute("DELETE");
                    novaNoticia();
                    Toast.makeText(getApplicationContext(), "Notícia excluída.", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Selecione uma notícia.", Toast.LENGTH_SHORT).show();

            }
        });

        botaolistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ListaNoticias.class);
                startActivity(intent);
            }
        });

    }

    private void novaNoticia(){
        texto.setText("");
        noticia = null;
    }

    private class Task extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... params) {
            String url = "http://192.168.1.105:8080/noticiasAcademicas/servicos/noticias/";
            if(params[0].equals("POST")) {
                url += "postNoticia";
                RestClientInterface<Noticia> cliente = new RestClient<>(url, RestClientInterface.TYPE_JSON);
                cliente.post(noticia);
            }
            else if(params[0].equals("PUT")) {
                url += "putNoticia";
                RestClientInterface<Noticia> cliente = new RestClient<>(url, RestClientInterface.TYPE_JSON);
                cliente.put(noticia);
            }
            else if(params[0].equals("DELETE")) {
                url += "deleteNoticia/" + noticia.getId();
                RestClientInterface<Noticia> cliente = new RestClient<>(url, RestClientInterface.TYPE_TEXT);
                cliente.delete();
            }
            return null;
        }

    }
}


