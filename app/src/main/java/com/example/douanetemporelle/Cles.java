package com.example.douanetemporelle;

public class Cles {

    // 1 - المطرية: آخر 4 أرقام من بطاقة الطالب
    public static final String MATRICULE = "5656";

    // 2 - أسماء المفاتيح بدون البادئة
    public static final String NOM = "NOM_VOYAGEUR";
    public static final String NAISSANCE = "ANNEE_NAISSANCE";
    public static final String DESTINATION = "ANNEE_DESTINATION";
    public static final String MOTIF = "MOTIF";
    public static final String VERDICT = "VERDICT";

    // 3 - دالة ثابتة تبني المفتاح انطلاقا من المطرية
    public static String construire(String matricule, String nom) {
        return matricule + "_" + nom;
    }
}
