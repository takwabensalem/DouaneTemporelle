package com.example.douanetemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class VerificationActivity extends AppCompatActivity {

    // 1. تعريف رقم بطاقة الطالب
    private static final String MATRICULE = "1234";

    // 2. التحقق إذا كان العدد أوليا
    @SuppressWarnings("all")
    public static boolean estPremier(int nombre) {
        if (nombre < 2) {
            return false;
        }
        int max = (int) Math.sqrt(nombre);
        for (int i = 2; i <= max; i++) {
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
                MainActivity.cleExtra(MATRICULE, "NOM_VOYAGEUR"));

        int naissance = getIntent().getIntExtra(
                MainActivity.cleExtra(MATRICULE, "ANNEE_NAISSANCE"), 0);

        int destination = getIntent().getIntExtra(
                MainActivity.cleExtra(MATRICULE, "ANNEE_DESTINATION"), 0);

        String motif = getIntent().getStringExtra(
                MainActivity.cleExtra(MATRICULE, "MOTIF_VOYAGE"));

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
        txtNom.setText(getString(R.string.label_nom, nom));
        txtDifference.setText(getString(R.string.label_age_temporel, difference));
        txtPremier.setText(getString(R.string.label_nombre_premier, premier ? getString(R.string.oui) : getString(R.string.non)));
        txtMotif.setText(getString(R.string.label_motif, motif));

        // 8. عرض القرار التلقائي
        String motifModification = getString(R.string.motif_modification_passe);
        boolean interdiction = Objects.equals(motifModification, motif) && premier;

        if (interdiction) {
            txtDecision.setText(R.string.decision_interdite);
            btnApprouver.setEnabled(false);
        } else {
            txtDecision.setText(R.string.decision_aucune_interdiction);
        }

        // 9. إرسال قرار الموافقة
        btnApprouver.setOnClickListener(v -> envoyerVerdict(true, nom));

        // 10. إرسال قرار الرفض
        btnRefuser.setOnClickListener(v -> envoyerVerdict(false, nom));
    }

    // 11. إرسال النتيجة إلى النشاط الثالث
    private void envoyerVerdict(boolean verdict, String nom) {
        Intent intent = new Intent(VerificationActivity.this,
                ResultActivity.class);

        intent.putExtra(
                MainActivity.cleExtra(MATRICULE, "VERDICT_FINAL"), verdict);

        intent.putExtra(
                MainActivity.cleExtra(MATRICULE, "NOM_VOYAGEUR"), nom);

        startActivity(intent);
    }
}
