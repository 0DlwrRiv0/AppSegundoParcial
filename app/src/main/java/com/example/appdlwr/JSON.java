package com.example.appdlwr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class JSON extends AppCompatActivity {

    public static MyInfo leerJson(String textoJson){
        Gson gson = new Gson();
        MyInfo datos = gson.fromJson(textoJson, MyInfo.class);

        return datos;
    }

    public String leerArchivo(String Sha1Password1, boolean Cfile, int x) {
        try {

            if(Cfile) {
                BufferedReader file = new BufferedReader(new InputStreamReader(openFileInput("Archivo" + x + ".txt")));
                String lineaTexto = file.readLine();
                file.close();

                JSON json = new JSON();
                MyInfo datos = json.leerJson(lineaTexto);
                String Sha1Password2 = datos.getContraseñaId();

                if (Sha1Password1.equals(Sha1Password2)) {
                    return "Usuario Correcto";
                } else {
                    return "Continuar";
                }
            }else{
                return "No se encontró el usuario";
            }

        } catch (Exception e) {
            return "Error";
        }
    }
}