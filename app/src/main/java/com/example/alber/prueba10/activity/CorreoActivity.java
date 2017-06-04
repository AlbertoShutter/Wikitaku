package com.example.alber.prueba10.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alber.prueba10.R;

public class CorreoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correo);

        Button btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // obtenemos los datos para el envío del correo
                EditText etSubject = (EditText) findViewById(R.id.etSubject);
                EditText etBody = (EditText) findViewById(R.id.etBody);

                // es necesario un intent que levante la actividad deseada
                Intent itSend = new Intent(android.content.Intent.ACTION_SEND);
                // vamos a enviar texto plano a menos que el checkbox esté marcado
                itSend.setType("plain/text");

                // colocamos los datos para el envío
                itSend.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"ealbertopost@gmail.com", "otakudiary@outlook.com"});
                itSend.putExtra(android.content.Intent.EXTRA_SUBJECT, etSubject.getText().toString());
                itSend.putExtra(android.content.Intent.EXTRA_TEXT, etBody.getText());

                //iniciamos la actividad
                startActivity(itSend);
            }
        });
    }
}

