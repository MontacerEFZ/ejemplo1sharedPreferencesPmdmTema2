package montacer.elfazazi.ejemplo1sharedpreferencestema2pmdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import montacer.elfazazi.ejemplo1sharedpreferencestema2pmdm.modelos.ContactosMatricula;

public class JsonActivity extends AppCompatActivity {
    private TextView lbResultado;
    private Button btnGuardar;
    private Button btnCargar;

    private List<ContactosMatricula> contactos;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        contactos = new ArrayList<>();

        crearContactos();

        sp = getSharedPreferences(Constantes.CONTACTOS, MODE_PRIVATE);

        inicializarVista();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datosJson = new Gson().toJson(contactos); //convertimos a gson
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(Constantes.DATOS, datosJson);
                editor.apply();
            }
        });

        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type tipo = new TypeToken<ArrayList<ContactosMatricula>>(){}.getType();
                ArrayList<ContactosMatricula> temp =
                        new Gson().fromJson(sp.getString(Constantes.DATOS, "[]"), tipo);
                contactos.clear();
                contactos.addAll(temp);
                lbResultado.setText("Elementos: "+contactos.size());
            }
        });
    }

    private void crearContactos() {
        for (int i = 0; i < 10; i++) {
            contactos.add(new ContactosMatricula(
                    "nombre "+i,
                    "cilo "+i,
                    "email "+i,
                    "telefono "+i
            ));
        }
    }

    private void inicializarVista() {
        lbResultado = findViewById(R.id.lbResultadoJson);
        btnCargar = findViewById(R.id.btnCargarJson);
        btnGuardar = findViewById(R.id.btnGuardarJson);
    }
}