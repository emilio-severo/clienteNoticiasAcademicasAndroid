package br.edu.ifsul.bage.tasks;

import android.os.AsyncTask;
import br.edu.ifsul.bage.client.RestClient;
import br.edu.ifsul.bage.client.RestClientInterface;

/**
 * Created by emilio on 10/03/16.
 */


public class RESTClientAndroidTask_ANT extends AsyncTask<Object, Void, String> {

    private static final int POST = 0;
    private static final int GET = 1;
    private static final int PUT = 2;
    private static final int DELETE = 3;

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected String doInBackground(Object... params) {
        if(params.length == 4)
            try {
                int resource = (int) params[1];
                String url = (String) params[2];
                String contentType = (String) params[3];

                RestClientInterface<Object> cliente = new RestClient<>(url, contentType);;

                switch (resource){
                    case POST:
                        cliente.post(params[0]);
                        break;
                    case PUT:
                        cliente.put(params[0]);
                    case DELETE:
                        cliente.delete(params[0]);
                }
                cliente.close();
                return "Requisição realizada com sucesso.";
            }
            catch (Exception e) {
                throw new RuntimeException("Parâmetros inválidos");
            }
        return "Falha na realização da requisição";
    }

}



