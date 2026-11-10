
package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VerificationActivity extends AppCompatActivity {

    // 1. تعريف رقم بطاقة الطالب
    private final String matricule = "1234";

    // 2. التحقق إذا كان العدد أوليا
    public static boolean estPremier(int nombre) {
        if (nombre < 2) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(nombre); i++) {
            if (nombre % i == 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        // 3. استرجاع المعلومات المرسلة من النشاط الأول
        String nom = getIntent().getStringExtra(
                MainActivity.cleExtra(matricule, "NOM_VOYAGEUR"));

        int naissance = getIntent().getIntExtra(
                MainActivity.cleExtra(matricule, "ANNEE_NAISSANCE"), 0);

        int destination = getIntent().getIntExtra(
                MainActivity.cleExtra(matricule, "ANNEE_DESTINATION"), 0);

        String motif = getIntent().getStringExtra(
                MainActivity.cleExtra(matricule, "MOTIF_VOYAGE"));

        // 4. حساب الفرق بين السنوات
        int difference = Math.abs(destination - naissance);

        // 5. التحقق من العدد الأولي
        boolean premier = estPremier(difference);

        // 6. ربط عناصر الواجهة
        TextView txtNom = findViewById(R.id.txt_nom_verif_tb);
        TextView txtDifference = findViewById(R.id.txt_difference_tb);
        TextView txtPremier = findViewById(R.id.txt_premier_tb);
        TextView txtMotif = findViewById(R.id.txt_motif_verif_tb);
        TextView txtDecision = findViewById(R.id.txt_decision_tb);

        Button btnApprouver = findViewById(R.id.btn_approuver_tb);
        Button btnRefuser = findViewById(R.id.btn_refuser_tb);

        // 7. عرض معلومات المسافر ونتيجة التحليل
        txtNom.setText("Nom : " + nom);
        txtDifference.setText("Âge temporel : " + difference + " ans");
        txtPremier.setText("Nombre premier : " + (premier ? "Oui" : "Non"));
        txtMotif.setText("Motif : " + motif);

        // 8. عرض القرار التلقائي
        boolean interdiction = "Modification du passé".equals(motif) && premier;

        if (interdiction) {
            txtDecision.setText("Approbation interdite");
            btnApprouver.setEnabled(false);
        } else {
            txtDecision.setText("Aucune interdiction");
        }

        // 9. إرسال قرار الموافقة
        btnApprouver.setOnClickListener(v -> {
            envoyerVerdict(true, nom);
        });

        // 10. إرسال قرار الرفض
        btnRefuser.setOnClickListener(v -> {
            envoyerVerdict(false, nom);
        });
    }

    // 11. إرسال النتيجة إلى النشاط الثالث
    private void envoyerVerdict(boolean verdict, String nom) {
        Intent intent = new Intent(VerificationActivity.this,
                ResultActivity.class);

        intent.putExtra(
                MainActivity.cleExtra(matricule, "VERDICT_FINAL"), verdict);

        intent.putExtra(
                MainActivity.cleExtra(matricule, "NOM_VOYAGEUR"), nom);

        startActivity(intent);
    }
}
