package com.example.appdlwr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MyInfo {

    public String nombreId;
    public String usuarioId;
    public String contraseñaId;
    public String emailId;

    public String getNombreId() {
        return nombreId;
    }
    public void setNombreId(String nombre) { nombreId= nombre;}

    public String getUsuarioId() {return usuarioId;}
    public void setUsuarioId(String usuario) { usuarioId= usuario;}

    public String getContraseñaId() {return contraseñaId;}
    public void setContraseñaId(String contraseña) { contraseñaId= contraseña;}

    public String getEmailId() {return emailId;}
    public void setEmailId(String email) { emailId= email;}
}