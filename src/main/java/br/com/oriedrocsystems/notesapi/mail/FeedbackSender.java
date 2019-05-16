package br.com.oriedrocsystems.notesapi.mail;

public interface FeedbackSender {
    void sendFeedback(String from, String name, String feedback);
}
