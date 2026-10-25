package ht.fds.mbds.elanjery.tp0rev.service;

import jakarta.enterprise.context.Dependent;

import java.io.Serializable;

@Dependent
public class GenerateurReponse implements Serializable {

    private static final String VOYELLES = "aeiouyàâäéèêëîïôöùûüÿœæ";

    /**
     * Traitement personnel : analyseur de texte.
     * Renvoie le nombre de mots, de voyelles et de consonnes, le mot le plus
     * long et le texte avec l'ordre des mots inversé.
     * Si roleSysteme n'est pas null (première question du chat), le rôle
     * choisi est affiché en début de réponse.
     */
    public String genererReponse(String question, String roleSysteme) {
        String texte = question.trim();
        String[] mots = texte.split("\\s+");

        int voyelles = 0;
        int consonnes = 0;
        for (char c : texte.toLowerCase().toCharArray()) {
            if (Character.isLetter(c)) {
                if (VOYELLES.indexOf(c) >= 0) {
                    voyelles++;
                } else {
                    consonnes++;
                }
            }
        }

        String plusLong = "";
        for (String mot : mots) {
            if (mot.length() > plusLong.length()) {
                plusLong = mot;
            }
        }

        StringBuilder inverse = new StringBuilder();
        for (int i = mots.length - 1; i >= 0; i--) {
            inverse.append(mots[i]);
            if (i > 0) {
                inverse.append(" ");
            }
        }

        StringBuilder reponse = new StringBuilder();
        if (roleSysteme != null) {
            reponse.append("Rôle choisi : ").append(roleSysteme).append("\n\n");
        }
        reponse.append("Analyse de votre texte :\n")
                .append("- Mots : ").append(mots.length).append("\n")
                .append("- Voyelles : ").append(voyelles)
                .append(", consonnes : ").append(consonnes).append("\n")
                .append("- Mot le plus long : ").append(plusLong).append("\n")
                .append("- Texte inversé : ").append(inverse);
        return reponse.toString();
    }
}