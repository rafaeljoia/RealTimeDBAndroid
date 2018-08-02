package br.com.inatel.exemplorealtimedb;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText txtStatusLampada;
    Button btnAtualiza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        txtStatusLampada = (EditText)findViewById(R.id.txtStatusLampada);
        btnAtualiza = (Button) findViewById(R.id.btnAtualiza);

        //Referencia da variável que será alterada no Banco
        final DatabaseReference myRef = database.getReference("status_da_lampada");


        //Evento de Click do Botão
        btnAtualiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pegamos o valor que está no EditText e informamos para o objeto de referência do Banco
              myRef.setValue(txtStatusLampada.getText().toString());

            }
        });


        // Quando o Banco for alterado, será invocado este método
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Sempre que for realizado um Update, este método será Chamado
                String value = dataSnapshot.getValue(String.class);
                Log.d("FIREBASE", "VARIAVEL STATUS LAMPADA = " + value);

                txtStatusLampada.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // CASO OCORRA FALHAS NA LEITURA, ocorrerá uma exceção
                Log.w("FIREBASE", "FALHA NA LEITURA DO VALOR.", error.toException());
            }
        });
    }
}
