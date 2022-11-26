package com.example.appdlwr.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyInfo implements Serializable {
    private String user;
    private String apellidos;
    private String correo;
    private String password;
    private Boolean sexo;
    private String num;

    public List<MyData> getContras() {
        return contras;
    }

    public void setContras(List<MyData> contras) {
        this.contras = contras;
    }

    private List<MyData> contras= new ArrayList<>();

    public MyInfo() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getApellidos(){ return apellidos; }

    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCorreo(){ return correo; }

    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword(){ return password; }

    public void setPassword(String password) { this.password = password; }

    public Boolean getSexo() {
        return sexo;
    }

    public void setSexo(Boolean sexo) {
        this.sexo = sexo;
    }

    public String getNum(){ return num; }

    public void setNum(String num) { this.num = num; }
}