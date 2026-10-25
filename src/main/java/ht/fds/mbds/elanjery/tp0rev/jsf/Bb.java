package ht.fds.mbds.elanjery.tp0rev.jsf;

import ht.fds.mbds.elanjery.tp0rev.service.GenerateurReponse;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class Bb implements Serializable {

    private String roleSysteme;
    private boolean roleSystemeChangeable = true;
    private List<SelectItem> listeRolesSysteme;
    private String question;
    private String reponse;
    private StringBuilder conversation = new StringBuilder();

    @Inject
    private GenerateurReponse generateurReponse;

    @Inject
    private FacesContext facesContext;

    public Bb() {
    }

    public String getRoleSysteme() {
        return roleSysteme;
    }

    public void setRoleSysteme(String roleSysteme) {
        this.roleSysteme = roleSysteme;
    }

    public boolean isRoleSystemeChangeable() {
        return roleSystemeChangeable;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getConversation() {
        return conversation.toString();
    }

    public void setConversation(String conversation) {
        this.conversation = new StringBuilder(conversation == null ? "" : conversation);
    }

    public String envoyer() {
        if (question == null || question.isBlank()) {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Texte question vide",
                    "Il manque le texte de la question");
            facesContext.addMessage(null, message);
            return null;
        }

        // Le rôle n'est transmis que pour la première question du chat
        String role = null;
        if (conversation.isEmpty()) {
            roleSystemeChangeable = false;
            role = roleSysteme;
        }

        reponse = generateurReponse.genererReponse(question, role);

        afficherConversation();
        return null;
    }

    public String effacer() {
        question = null;
        reponse = null;
        return null;
    }

    /**
     * Retourne la page courante : la vue change, donc une nouvelle instance
     * du bean (portée view) est créée et l'état est réinitialisé.
     */
    public String nouveauChat() {
        return "index";
    }

    private void afficherConversation() {
        conversation.append("== User:\n")
                .append(question)
                .append("\n== Serveur:\n")
                .append(reponse)
                .append("\n");
    }

    public List<SelectItem> getRolesSysteme() {
        if (listeRolesSysteme == null) {
            listeRolesSysteme = new ArrayList<>();
            listeRolesSysteme.add(new SelectItem("Assistant", "Assistant"));
            listeRolesSysteme.add(new SelectItem("Traducteur Anglais-Français", "Traducteur Anglais-Français"));
            listeRolesSysteme.add(new SelectItem("Guide touristique", "Guide touristique"));
        }
        return listeRolesSysteme;
    }
}