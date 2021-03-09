package com.example.quipuxprueba.Services;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.quipuxprueba.Utils.ObjectHttp;
import com.example.quipuxprueba.interfaces.ProgresoInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;

import static com.example.quipuxprueba.Utils.Const.URLLOGIN;

public class AsynkTaskLogin extends AsyncTask<Void, Void, Boolean> {

    private Activity activity;
    private String parametros;
    Boolean estado;
    ProgresoInterface progresoInterface;

    public AsynkTaskLogin(Activity activity,String parametros,ProgresoInterface progresoInterface) {
        this.activity = activity;
        this.parametros = parametros;
        this.progresoInterface = progresoInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progresoInterface.showProgressDialog();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        progresoInterface.dismissProgressDialog();

        if (aBoolean) {
            progresoInterface.login();
        }else{
            Toast.makeText(activity,"Usuario o contraseña incorrectos",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        try {

            ObjectHttp httResponse = new ObjectHttp(activity);
            String responseStr = httResponse.obtenerHttpConDatos(URLLOGIN,parametros);

            JSONObject jsonObject = new JSONObject(responseStr);

            estado = jsonObject.getBoolean("authenticated");

            return estado;

        } catch (ProtocolException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
