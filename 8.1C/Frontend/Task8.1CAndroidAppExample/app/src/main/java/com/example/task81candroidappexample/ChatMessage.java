package com.example.task81candroidappexample;

public class ChatMessage {
    public enum Sender { USER, BOT }

    private final Sender sender;
    private final String text;

    public ChatMessage(Sender sender, String text) {
        this.sender = sender;
        this.text   = text;
    }
    public Sender getSender() { return sender; }
    public String getText()   { return text;   }
}
