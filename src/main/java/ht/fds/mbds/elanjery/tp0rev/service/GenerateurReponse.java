package ht.fds.mbds.elanjery.tp0rev.service;

import jakarta.enterprise.context.Dependent;

import java.io.Serializable;

@Dependent
public class GenerateurReponse implements Serializable {

    /**
     * Traitement de base du TP : change la casse de la question et l'entoure de "||".
     * Si roleSysteme n'est pas null (première question du chat), son libellé
     * en majuscules précède la question.
     */
    public String genererReponse(String question, String roleSysteme) {
        StringBuilder casseChangee = new StringBuilder();
        for (char c : question.toCharArray()) {
            if (Character.isUpperCase(c)) {
                casseChangee.append(Character.toLowerCase(c));
            } else {
                casseChangee.append(Character.toUpperCase(c));
            }
        }

        String reponse = "||" + casseChangee + "||";
        if (roleSysteme != null) {
            reponse = roleSysteme.toUpperCase() + "\n" + reponse;
        }
        return reponse;
    }
}