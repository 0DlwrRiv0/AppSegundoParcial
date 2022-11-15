package com.example.appdlwr;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Registro extends AppCompatActivity {
    EditText nomId, userId, contraId;
    private Button Registrar;
    byte[] byt = null;
    String ContrasenaD;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nomId = (EditText) (findViewById(R.id.nombreId));
        userId = (EditText) (findViewById(R.id.usuarioId));
        contraId = (EditText) (findViewById(R.id.contraseñaId));


    }


    public boolean validar() {
        boolean retorno = true;

        String nombre = nomId.getText().toString();
        String usuario = userId.getText().toString();
        String contraseña = contraId.getText().toString();


        if (nombre.isEmpty()) {
            nomId.setError("Este es un campo obligatorio");
            retorno = false;
        }
        if (usuario.isEmpty()) {
            userId.setError("Este es un campo obligatorio");
            retorno = false;
        }
        if (contraseña.isEmpty()) {
            contraId.setError("Este es un campo obligatorio");
            retorno = false;
        }

        return retorno;
    }

    ;

    public void leerArchivo() {
        try {
            BufferedReader aux = new BufferedReader(new InputStreamReader(openFileInput("datos.txt")));

            String texto = aux.readLine();

            Toast.makeText(this, "Inicia sesión para continuar", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Log.e("Archivo", "Error");
        }
    }

    public void Finalizar(View v) throws IOException {
        userId = findViewById(R.id.usuarioId);
        nomId = findViewById(R.id.nombreId);
        contraId = findViewById(R.id.contraseñaId);
        String cadena = userId.getText().toString() + contraId.getText().toString();
        Digest digest = new Digest();
        byt = digest.createSha1(cadena);
        ContrasenaD = digest.bytesToHex(byt);
        if (validar()) {
            Toast.makeText(this, "Inicia sesión para continuar", Toast.LENGTH_SHORT).show();
        }
        String ValUsuario = String.valueOf(userId.getText());
        String ValNombre = String.valueOf(nomId.getText());
        MyInfo myInfo = null;
        Gson gson = null;
        String json = null;
        String mensaje = null;
        myInfo = new MyInfo();
        myInfo.setUsuarioId(ValUsuario);
        myInfo.setNombreId(ValNombre);
        myInfo.setContraseñaId(ContrasenaD);
        Log.d(TAG, "TEST");
        gson = new Gson();
        json = gson.toJson(myInfo);
        if (json != null) {
            Log.d(TAG, json);
            mensaje = "OK";
        } else {
            mensaje = "ERROR";
        }
        try {
            JSON json2 = new JSON();
            boolean BucleArchivo = true;
            int x = 1;
            while (BucleArchivo) {
                File Cfile = new File(getApplicationContext().getFilesDir() + "/" + "Archivo" + x + ".txt");
                if (Cfile.exists()) {
                    BufferedReader file = new BufferedReader(new InputStreamReader(openFileInput("Archivo" + x + ".txt")));
                    String lineaTexto = file.readLine();
                    file.close();

                    MyInfo datos = json2.leerJson(lineaTexto);
                    String ValorUsr2 = datos.getUsuarioId();

                    if (ValUsuario.equals(ValorUsr2)) {
                        mensaje = "Este usuario ya existe, ingrese uno nuevo";
                        BucleArchivo = false;
                    } else {
                        x = x + 1;
                    }
                } else {
                    BufferedWriter file = new BufferedWriter(new OutputStreamWriter(openFileOutput("Archivo" + x + ".txt", Context.MODE_PRIVATE)));
                    file.write(json);
                    file.close();
                    mensaje = "Inicia sesión para continuar";
                    BucleArchivo = false;
                }
            }
        } finally {

        }
    }
}