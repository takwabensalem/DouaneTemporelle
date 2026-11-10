package com.example.douanetemporelle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
// نشاط النتيجة: يعرض قرار الجمارك مع اسم المسافر
public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 1 - ربط العناصر بالمعرفات
        LinearLayout racine = findViewById(R.id.layout_racine_tk);
        TextView tvMessageNj = findViewById(R.id.tv_message_tk);
        Button btnNouveauNj = findViewById(R.id.btn_nouveau_tk);

        // 2 - استقبال القرار والاسم من النشاط الثاني
        Intent recu = getIntent();
        boolean approuve = recu.getBooleanExtra(Cles.construire(Cles.MATRICULE, Cles.VERDICT), false);
        String nom = recu.getStringExtra(Cles.construire(Cles.MATRICULE, Cles.NOM));

        // 3 - تغيير لون الخلفية والرسالة حسب القرار
        if (approuve) {
            racine.setBackgroundColor(Color.parseColor("#2E7D32")); // أخضر
            tvMessageNj.setText("Bon voyage " + nom + " !");
        } else {
            racine.setBackgroundColor(Color.parseColor("#C62828")); // أحمر
            tvMessageNj.setText(nom + " est en état d'arrestation temporelle !");
        }

        // 4 - الرجوع إلى النشاط الأول مع غلق كل الأنشطة السابقة
        btnNouveauNj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
