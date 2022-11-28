package com.example.appdlwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.appdlwr.des.MyDesUtil;
import com.example.appdlwr.json.MyData;
import com.example.appdlwr.json.MyInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Registro extends AppCompatActivity {

    private Button registrate, inicio;
    private EditText usuario, apellidos, pass, correo, tel;
    private RadioButton rb1, rb2, rb3;
    private CheckBox c1, c2;
    private Switch switch1, switch2;
    private static final String TAG = "MainActivity";
    public static final String archivo = "Json";
    String json = null;
    public static String usr,ape,password,email,numtel;
    public static List<MyInfo> list =new ArrayList<MyInfo>();
    public static List<MyData> lista;
    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    public MyDesUtil myDesUtil= new MyDesUtil().addStringKeyBase64(KEY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        lista= new ArrayList<>();
        MyData myData=null;
        registrate = findViewById(R.id.registrateId);
        inicio = findViewById(R.id.InicioId);
        usuario = findViewById(R.id.nombreId);
        apellidos = findViewById(R.id.apellidosId);
        pass = findViewById(R.id.passwordId);
        correo = findViewById(R.id.emailId);
        tel = findViewById(R.id.phoneId);
        rb1 = findViewById(R.id.radBtn1);
        rb2 = findViewById(R.id.radBtn2);
        rb3 = findViewById(R.id.radBtn3);
        c1 = findViewById(R.id.check1);
        c2 = findViewById(R.id.check2);
        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        Read();
        json2List(json);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro.this, Login.class);
                startActivity(intent);
            }
        });
        registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyInfo info = new MyInfo();

                usr= String.valueOf(usuario.getText());
                password = String.valueOf(pass.getText());
                email= String.valueOf(correo.getText());
                ape = String.valueOf(apellidos.getText());
                numtel = String.valueOf(tel.getText());

                if(usr.equals("")||password.equals("")||email.equals("")){
                    Log.d(TAG,"vacio");
                    Log.d(TAG,usr);
                    Log.d(TAG,password);
                    Log.d(TAG,email);
                    Toast.makeText(getApplicationContext(), "Faltan campos por llenar", Toast.LENGTH_LONG).show();
                }else{
                    if(Nanai.validarEmail(email)){
                        if(list.isEmpty()){
                            Log.d(TAG,"lleno");
                            Nanai.fillInfo(info);
                            List2Json(info,list);
                        }else{
                            if(Nanai.usuarios(list,usr, email)){
                                Log.d(TAG,"Este usuario ya existe");
                                Toast.makeText(getApplicationContext(), "Nombre ocupado", Toast.LENGTH_LONG).show();
                            }else{
                                Nanai.fillInfo(info);
                                info.setContras(lista);
                                List2Json(info,list);
                            }
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Introduzca un correo válido", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    public void List2Json(MyInfo info,List<MyInfo> list){
        Gson gson =null;
        String json= null;
        gson =new Gson();
        list.add(info);
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
        Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_LONG).show();
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
    private File getFile(){
        return new File(getDataDir(),archivo);
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
        return true;
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
    public void json2List( String json) {
        Gson gson = null;
        String mensaje = null;
        if (json == null || json.length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Error json null or empty", Toast.LENGTH_LONG).show();
            return;
        }
        gson = new Gson();
        Type listType = new TypeToken<ArrayList<MyInfo>>(){}.getType();
        list = gson.fromJson(json, listType);
        if (list == null || list.size() == 0 )
        {
            Toast.makeText(getApplicationContext(), "Error list is null or empty", Toast.LENGTH_LONG).show();
            return;
        }
    }
}