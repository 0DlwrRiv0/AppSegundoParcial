package com.example.appdlwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Login extends AppCompatActivity {

    private Button buttonRegistro;
    private Button buttonAcceder;
    private Button buttonOlvide;
    EditText user;
    EditText contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.usuarioId);
        contra = findViewById(R.id.contraseñaId);

        Button botonA = findViewById(R.id.btn_a);
        botonA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String mensaje = "";

                if("".equals(String.valueOf(user.getText())) || "".equals(String.valueOf(contra.getText()))){
                    Toast.makeText(getApplicationContext(), "Llena todos los campos", Toast.LENGTH_LONG).show();
                } else{
                    try {

                        Digest digest = new Digest();
                        byte[] txtByte = digest.createSha1(user.getText().toString() + contra.getText().toString());
                        String Sha1Password1 = digest.bytesToHex(txtByte);

                        JSON json = new JSON();

                        boolean BucleArchivo = true;
                        int x = 1;
                        while (BucleArchivo) {
                            File Cfile = new File(getApplicationContext().getFilesDir() + "/" + "Archivo" + x + ".txt");
                            if(Cfile.exists()) {
                                BufferedReader file = new BufferedReader(new InputStreamReader(openFileInput("Archivo" + x + ".txt")));
                                String lineaTexto = file.readLine();
                                file.close();

                                MyInfo datos = json.leerJson(lineaTexto);
                                String Sha1Password2 = datos.getContraseñaId();

                                if (Sha1Password1.equals(Sha1Password2)) {
                                    mensaje = "¡Bienvenido! ";
                                    BucleArchivo = false;
                                } else {
                                    x = x + 1;
                                }
                            }else{
                                mensaje = "No se encontró el usuario";
                                BucleArchivo = false;
                            }
                        }

                        if("¡Bienvenido! ".equals(mensaje)){
                            Toast.makeText(Login.this, mensaje, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, Acceso.class);
                            startActivity(intent);
                            intent.putExtra("archivo", x);
                            startActivity(intent);
                        }

                        Toast.makeText(Login.this, mensaje, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        mensaje = "Por favor, intentelo de nuevo";
                        Toast.makeText(Login.this, mensaje, Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

        buttonRegistro = findViewById(R.id.btn_r);
        buttonRegistro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login.this, Registro.class));
            }
        });
        buttonOlvide = findViewById(R.id.btn_o);
        buttonOlvide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login.this, Olvide.class));
            }
        });
    }
}