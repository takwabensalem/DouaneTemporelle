
package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // 1. إنشاء المفاتيح باستعمال رقم بطاقة الطالب
    public static String cleExtra(String matricule, String nomChamp) {
        return matricule + "_" + nomChamp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. ربط عناصر الواجهة بالكود
        EditText edtNom = findViewById(R.id.edt_nom_tb);
        EditText edtNaissance = findViewById(R.id.edt_naissance_tb);
        EditText edtDestination = findViewById(R.id.edt_destination_tb);
        RadioGroup rgMotif = findViewById(R.id.rg_motif_tb);
        Button btnInspecter = findViewById(R.id.btn_inspecter_tb);

        // 3. تنفيذ العملية عند الضغط على الزر
        btnInspecter.setOnClickListener(v -> {

            String nom = edtNom.getText().toString().trim();
            String naissanceTexte = edtNaissance.getText().toString().trim();
            String destinationTexte = edtDestination.getText().toString().trim();

            // 4. التحقق من إدخال جميع المعلومات
            if (nom.isEmpty() || naissanceTexte.isEmpty()
                    || destinationTexte.isEmpty()
                    || rgMotif.getCheckedRadioButtonId() == -1) {

                Toast.makeText(this,
                        "Veuillez remplir tous les champs",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // 5. تحويل السنوات إلى أعداد صحيحة
            int anneeNaissance;
            int anneeDestination;

            try {
                anneeNaissance = Integer.parseInt(naissanceTexte);
                anneeDestination = Integer.parseInt(destinationTexte);
            } catch (NumberFormatException e) {
                Toast.makeText(this,
                        "Veuillez entrer des années valides",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // 6. استرجاع سبب السفر
            int idChoisi = rgMotif.getCheckedRadioButtonId();
            RadioButton boutonChoisi = findViewById(idChoisi);
            String motif = boutonChoisi.getText().toString();

            // 7. إنشاء Intent صريح لإرسال المعلومات
            Intent intent = new Intent(MainActivity.this,
                    VerificationActivity.class);

            String matricule = "1234";

            intent.putExtra(cleExtra(matricule, "NOM_VOYAGEUR"), nom);
            intent.putExtra(cleExtra(matricule, "ANNEE_NAISSANCE"), anneeNaissance);
            intent.putExtra(cleExtra(matricule, "ANNEE_DESTINATION"), anneeDestination);
            intent.putExtra(cleExtra(matricule, "MOTIF_VOYAGE"), motif);

            startActivity(intent);
        });
    }
}
