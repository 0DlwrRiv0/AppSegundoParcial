package com.example.appdlwr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Acceso extends AppCompatActivity {

    private ListView listView;
    private List<MyInfo1> list;
    private int []imagen = { R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img4,R.drawable.img6, R.drawable.img7,R.drawable.img8,R.drawable.img9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso);

        TextView Nombre;

        Nombre = findViewById(R.id.NombreUsr);

        listView = (ListView) findViewById(R.id.listViewId1);
        list = new ArrayList<MyInfo1>();
        Intent intent = getIntent();

        for (int i = 0; i < 9; i++){
            MyInfo1 myData = new MyInfo1();
            myData.setContra(String.format("Contraseña: %d",(int)(Math.random()*10000)));

            if (i == 0){
                myData.setName(String.format( "Dela1 "));
                myData.setImage(imagen[0]);
            }
            if (i == 1){
                myData.setName(String.format( "Dela2"));
                myData.setImage(imagen[1]);
            }
            if (i == 2){
                myData.setName(String.format( "Dela3" ));
                myData.setImage(imagen[2]);
            }
            if (i == 3){
                myData.setName(String.format( "Dela4" ));
                myData.setImage(imagen[3]);
            }
            if (i == 4){
                myData.setName(String.format( "Dela5" ));
                myData.setImage(imagen[4]);
            }
            if (i == 5){
                myData.setName(String.format( "Dela6" ));
                myData.setImage(imagen[5]);
            }
            if (i == 6){
                myData.setName(String.format( "Dela7" ));
                myData.setImage(imagen[6]);
            }
            if (i == 7){
                myData.setName(String.format( "Dela8" ));
                myData.setImage(imagen[7]);
            }
            if (i == 8){
                myData.setName(String.format( "Dela9" ));
                myData.setImage(imagen[0]);
            }

            list.add(myData);
        }


        try{
            int y = getIntent().getExtras().getInt("archivo");
            JSON json = new JSON();
            BufferedReader archivito = new BufferedReader(new InputStreamReader(openFileInput("Archivo" + y + ".txt")));
            String datos = archivito.readLine();
            MyInfo Data = json.leerJson(datos);
            archivito.close();

            Nombre.setText("Nombre de Usuario: " +Data.getNombreId() );
        } catch(Exception e){
        }
        MyAdapter myAdapter = new MyAdapter(list, getBaseContext());
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                toast( i );
            }
        });



    }
    private void toast(int i){
        Toast.makeText(getBaseContext(), list.get(i).getContra(),Toast.LENGTH_SHORT).show();
    }
}