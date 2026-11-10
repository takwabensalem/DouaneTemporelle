package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
// نشاط التحقق: يعرض بيانات المسافر ويرسل القرار
public class VerificationActivity extends AppCompatActivity {

    // ١ - تعريف عناصر الواجهة
    private TextView tvNomNj, tvAgeNj, tvMotifNj, tvPremierNj, tvAlerteNj;
    private Button btnApprouverNj, btnRefuserNj;
    private String nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        // ٢ - ربط العناصر بالمعرفات
        tvNomNj = findViewById(R.id.tv_nom_tk);
        tvAgeNj = findViewById(R.id.tv_age_tk);
        tvMotifNj = findViewById(R.id.tv_motif_tk);
        tvPremierNj = findViewById(R.id.tv_premier_tk);
        tvAlerteNj = findViewById(R.id.tv_alerte_tk);
        btnApprouverNj = findViewById(R.id.btn_approuver_tk);
        btnRefuserNj = findViewById(R.id.btn_refuser_tk);

        // ٣ - استقبال المعطيات القادمة من النشاط الأول
        Intent recu = getIntent();
        nom = recu.getStringExtra(Cles.construire(Cles.MATRICULE, Cles.NOM));
        int naissance = recu.getIntExtra(Cles.construire(Cles.MATRICULE, Cles.NAISSANCE), 0);
        int destination = recu.getIntExtra(Cles.construire(Cles.MATRICULE, Cles.DESTINATION), 0);
        String motif = recu.getStringExtra(Cles.construire(Cles.MATRICULE, Cles.MOTIF));

        // ٤ - حساب العمر الزمني (الفرق بين سنة الوجهة وسنة الميلاد)
        int difference = destination - naissance;

        tvNomNj.setText("Nom : " + nom);
        tvAgeNj.setText("Âge temporel : " + difference + " ans");
        tvMotifNj.setText("Motif : " + motif);

        // ٥ - عرض هل الفرق عدد أولي
        boolean premier = estPremier(difference);
        tvPremierNj.setText("Nombre premier : " + (premier ? "Oui" : "Non"));

        // ٦ - تعطيل زر الموافقة وإظهار رسالة المنع
        if (motif.equals(getString(R.string.motif_modification)) && premier) {
            btnApprouverNj.setEnabled(false);
            tvAlerteNj.setVisibility(View.VISIBLE);
        }

        // ٧ - زر الموافقة
        btnApprouverNj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                envoyerVerdict(true);
            }
        });

        // ٨ - زر الرفض
        btnRefuserNj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                envoyerVerdict(false);
            }
        });
    }

    // ٩ - إرسال القرار واسم المسافر إلى النشاط الثالث
    private void envoyerVerdict(boolean approuve) {
        Intent intent = new Intent(VerificationActivity.this, ResultActivity.class);
        intent.putExtra(Cles.construire(Cles.MATRICULE, Cles.VERDICT), approuve);
        intent.putExtra(Cles.construire(Cles.MATRICULE, Cles.NOM), nom);
        startActivity(intent);
    }

    // ١٠ - دالة اختبار العدد الأولي
    private boolean estPremier(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}