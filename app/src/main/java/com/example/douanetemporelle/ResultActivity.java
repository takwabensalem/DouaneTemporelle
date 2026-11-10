package com.example.douanetemporelle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    // 1. تعريف رقم بطاقة الطالب
    private static final String MATRICULE = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 2. ربط عناصر الواجهة بالكود
        LinearLayout layout = findViewById(R.id.layout_resultat_tb);
        TextView txtResultat = findViewById(R.id.txt_resultat_tb);
        Button btnNouveau = findViewById(R.id.btn_nouveau_tb);

        // 3. استرجاع النتيجة واسم المسافر
        boolean verdict = getIntent().getBooleanExtra(
                MainActivity.cleExtra(MATRICULE, "VERDICT_FINAL"), false);

        String nomExtra = getIntent().getStringExtra(
                MainActivity.cleExtra(MATRICULE, "NOM_VOYAGEUR"));
        String nom = Objects.requireNonNullElse(nomExtra, "");

        // 4. عرض النتيجة وتغيير لون الخلفية
        if (verdict) {
            layout.setBackgroundColor(Color.parseColor("#2E7D32"));
            txtResultat.setText(getString(R.string.resultat_succes, nom));
        } else {
            layout.setBackgroundColor(Color.parseColor("#B71C1C"));
            txtResultat.setText(getString(R.string.resultat_echec, nom));
        }

        // 5. العودة إلى الصفحة الأولى لبدء رحلة جديدة
        btnNouveau.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this,
                    MainActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
            finish();
        });
    }
}
