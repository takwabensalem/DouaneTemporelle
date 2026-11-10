package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // 1 - تعريف عناصر الواجهة
    private EditText etNomNj, etNaissanceNj, etDestinationNj;
    private RadioGroup rgMotifTk;
    private Button btnInspecterNj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2 - ربط العناصر بالمعرفات في ملف XML
        etNomNj = findViewById(R.id.et_nom_tk);
        etNaissanceNj = findViewById(R.id.et_naissance_tk);
        etDestinationNj = findViewById(R.id.et_destination_tk);
        rgMotifTk = findViewById(R.id.rg_motif_tk);
        btnInspecterNj = findViewById(R.id.btn_inspecter_tk);

        // 3 - عند الضغط على الزر
        btnInspecterNj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = etNomNj.getText().toString().trim();
                String naissStr = etNaissanceNj.getText().toString().trim();
                String destStr = etDestinationNj.getText().toString().trim();

                // 4 - التحقق من أن كل الحقول مملوءة
                if (nom.isEmpty() || naissStr.isEmpty() || destStr.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Remplissez tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                int naissance = Integer.parseInt(naissStr);
                int destination = Integer.parseInt(destStr);
                // 5 - قراءة سبب السفر من أزرار الراديو
                int idChoisi = rgMotifTk.getCheckedRadioButtonId();
                RadioButton rbChoisi = findViewById(idChoisi);
                String motif = rbChoisi.getText().toString();

                // 6 - إنشاء Intent صريح وإرسال المعطيات بمفاتيح مبنية بالدالة الثابتة
                Intent intent = new Intent(MainActivity.this, VerificationActivity.class);
                intent.putExtra(Cles.construire(Cles.MATRICULE, Cles.NOM), nom);
                intent.putExtra(Cles.construire(Cles.MATRICULE, Cles.NAISSANCE), naissance);
                intent.putExtra(Cles.construire(Cles.MATRICULE, Cles.DESTINATION), destination);
                intent.putExtra(Cles.construire(Cles.MATRICULE, Cles.MOTIF), motif);
                startActivity(intent);
            }
        });
    }
}
