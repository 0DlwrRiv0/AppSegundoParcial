package com.example.appdlwr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appdlwr.MyAdapter.MyAdapter;
import com.example.appdlwr.des.MyDesUtil;
import com.example.appdlwr.json.MyData;
import com.example.appdlwr.json.MyInfo;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    private List<MyInfo> list;
    public MyDesUtil myDesUtil= new MyDesUtil().addStringKeyBase64(Registro.KEY);
    public static String TAG = "mensaje";
    public static String json = null;
    private ListView listView;
    private List<MyData> listo;
    String aux;
    public boolean bandera = false;
    public int pos=0;
    public static MyInfo myInfo= null;
    EditText editText,editText1;
    Button buttonEliminar,buttonGuardar,buttonModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Object object= null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intent = getIntent();
        if(intent != null){
            if(intent.getExtras() !=null){
                object = intent.getExtras().get("Objeto");
                if (object != null) {
                    if (object instanceof MyInfo) {
                        myInfo = (MyInfo) object;

                    }
                }
            }
        }
        list= new ArrayList<>();
        list = Login.list;
        editText=findViewById(R.id.editText1);
        editText1=findViewById(R.id.editText2);
        buttonEliminar=findViewById(R.id.buttonE);
        buttonGuardar=findViewById(R.id.buttonG);
        buttonModificar=findViewById(R.id.buttonM);
        listView = (ListView) findViewById(R.id.listViewId);
        listo = new ArrayList<MyData>();
        listo = myInfo.getContras();
        MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
        listView.setAdapter(myAdapter);
        buttonEliminar.setEnabled(false);
        buttonModificar.setEnabled(false);
        if(listo.isEmpty()){
            Toast.makeText(getApplicationContext(), "Agrega", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(getApplicationContext(), "Click en eliminar o modficar", Toast.LENGTH_LONG).show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editText.setText(listo.get(i).getUsuario());
                editText1.setText(listo.get(i).getContra());
                pos=i;
                buttonEliminar.setEnabled(true);
                buttonModificar.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Asegurate de guardar los cambios", Toast.LENGTH_LONG).show();
            }
        });

        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listo.remove(pos);
                myInfo.setContras(listo);
                MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
                listView.setAdapter(myAdapter);
                editText.setText("");
                editText1.setText("");
                Toast.makeText(getApplicationContext(), "Contraseña eliminada", Toast.LENGTH_LONG).show();
                buttonEliminar.setEnabled(false);
                buttonModificar.setEnabled(false);
            }
        });
        buttonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usr= String.valueOf(editText.getText());
                String contra = String.valueOf(editText1.getText());
                if(usr.equals("")||contra.equals("")){
                    Toast.makeText(getApplicationContext(), "Llene los campos", Toast.LENGTH_LONG).show();
                }else{
                    listo.get(pos).setUsuario(usr);
                    listo.get(pos).setContra(contra);
                    myInfo.setContras(listo);
                    MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
                    listView.setAdapter(myAdapter);
                    editText.setText("");
                    editText1.setText("");
                    Toast.makeText(getApplicationContext(), "Contraseña modificada", Toast.LENGTH_LONG).show();
                    buttonEliminar.setEnabled(false);
                    buttonModificar.setEnabled(false);
                }
            }
        });
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usr= String.valueOf(editText.getText());
                String contra = String.valueOf(editText1.getText());
                if(usr.equals("")||contra.equals("")){
                    Toast.makeText(getApplicationContext(), "Llena los campos", Toast.LENGTH_LONG).show();
                }else{
                    MyData myData = new MyData();
                    myData.setContra(contra);
                    myData.setUsuario(usr);
                    listo.add(myData);
                    MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
                    listView.setAdapter(myAdapter);
                    editText.setText("");
                    editText1.setText("");
                    Toast.makeText(getApplicationContext(), "Se agregó la contraseña", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu)
    {
        boolean flag = false;
        flag = super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu ,  menu);
        return flag;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        if(id==R.id.item1){
            String usr= String.valueOf(editText.getText());
            String contra = String.valueOf(editText1.getText());
            if(usr.equals("")||contra.equals("")){
                Toast.makeText(getApplicationContext(), "Llena los campos", Toast.LENGTH_LONG).show();
            }else{
                MyData myData = new MyData();
                myData.setContra(contra);
                myData.setUsuario(usr);
                listo.add(myData);
                MyAdapter myAdapter = new MyAdapter(listo, getBaseContext());
                listView.setAdapter(myAdapter);
                editText.setText("");
                editText1.setText("");
                Toast.makeText(getApplicationContext(), "Se agregó la contraseña", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if(id==R.id.item2){
            int i =0;
            for(MyInfo inf : list){
                if(myInfo.getUser().equals(inf.getUser())){
                    list.get(i).setContras(listo);
                }
                i++;
            }
            List2Json(myInfo,list);
            return true;
        }
        if(id==R.id.item3){
            Intent intent= new Intent(Menu.this,Login.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void List2Json(MyInfo info,List<MyInfo> list){
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
            json = myDesUtil.cifrar(json);
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
        return new File(getDataDir(),Registro.archivo);
    }

}