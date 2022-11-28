package com.example.appdlwr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdlwr.des.MyDesUtil;
import com.example.appdlwr.json.MyInfo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class OlvideContra extends AppCompatActivity {

    public static List<MyInfo> list;
    public static String json = null;
    public static String TAG = "mensaje";
    public static String cadena= null;
    public MyDesUtil myDesUtil= new MyDesUtil().addStringKeyBase64(Registro.KEY);
    public String usr=null;
    public String correo,mensaje;
    EditText usuario,email;
    Button button;
    public static String TOG = "error";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide_contra);
        usuario= findViewById(R.id.user);
        email = findViewById(R.id.emailId);
        button = findViewById(R.id.recuperar);
        list=Login.list;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usr = String.valueOf(usuario.getText());
                correo = String.valueOf(email.getText());
                if(usr.equals("")&&email.equals("")) {
                    Toast.makeText(getApplicationContext(), "Llena el campo de Usuario", Toast.LENGTH_LONG).show();
                }else{
                    int i=0;
                    int j=0;
                    for(MyInfo inf : list){
                        if(inf.getUser().equals(usr) || inf.getCorreo().equals(correo)){
                            correo=inf.getCorreo();
                            String contra=inf.getPassword();
                            String nueva = String.format("%d",(int)(Math.random()*1000));
                            mensaje="<html><h1>Registro para una app????</h1></html>";
                            correo=myDesUtil.cifrar(correo);
                            mensaje=myDesUtil.cifrar(mensaje);
                            list.get(j).setPassword(nueva);
                            Log.i(TAG,nueva);
                            Log.i(TAG,list.get(j).getPassword());
                            List2Json(list);
                            i=1;
                        }
                        j++;
                    }
                    if(i==1){
                        Log.i(TAG,usr);
                        Log.i(TAG,correo);
                        Log.i(TAG,mensaje);
                        if( sendInfo( correo,mensaje ) )
                        {
                            Toast.makeText(getBaseContext() , "El texto ha sido enviado correctamente" , Toast.LENGTH_LONG );
                            return;
                        }
                        Toast.makeText(getBaseContext() , "Error en el envío" , Toast.LENGTH_LONG );
                    }else{
                        if(i==0){
                            Log.i(TAG,"no hay usuarios");
                            Toast.makeText(getBaseContext() , "No hay usuarios en el sistema" , Toast.LENGTH_LONG );
                            return;
                        }
                    }
                }
            }
        });
    }
    public boolean sendInfo( String correo ,String mensaje)
    {
        JsonObjectRequest jsonObjectRequest = null;
        JSONObject jsonObject = null;
        String url = "https://us-central1-nemidesarrollo.cloudfunctions.net/envio_correo";
        RequestQueue requestQueue = null;
        if( correo == null || correo.length() == 0 )
        {
            return false;
        }
        jsonObject = new JSONObject( );
        try
        {
            jsonObject.put("correo" , correo );
            jsonObject.put("mensaje", mensaje);
            String hola = jsonObject.toString();
            Log.i(TAG,hola);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, response.toString());
            }
        } , new  Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e  (TOG, error.toString());
            }
        } );
        requestQueue = Volley.newRequestQueue( getBaseContext() );
        requestQueue.add(jsonObjectRequest);

        return true;
    }
    public boolean Read(){
        if(!isFileExits()){
            return false;
        }
        File file = getFile();
        FileInputStream fileInputStream = null;
        byte[] bytes = null;
        bytes = new byte[(int)file.length()];
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            json=new String(bytes);
            json= myDesUtil.desCifrar(json);
            Log.d(TAG,json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    private File getFile( )
    {
        return new File( getDataDir() , Registro.archivo );
    }
    private boolean isFileExits( )
    {
        File file = getFile( );
        if( file == null )
        {
            return false;
        }
        return file.isFile() && file.exists();
    }
    public void List2Json(List<MyInfo> list){
        Gson gson =null;
        String json= null;
        gson =new Gson();
        json =gson.toJson(list, ArrayList.class);
        if (json == null)
        {
            Log.d(TAG, "Error json");
        }
        else
        {
            Log.d(TAG, json);
            json=myDesUtil.cifrar(json);
            Log.d(TAG, json);
            writeFile(json);
        }
    }
    private boolean writeFile(String text){
        File file =null;
        FileOutputStream fileOutputStream =null;
        try{
            file=getFile();
            fileOutputStream = new FileOutputStream( file );
            fileOutputStream.write( text.getBytes(StandardCharsets.UTF_8) );
            fileOutputStream.close();
            Log.d(TAG, "Hola");
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}