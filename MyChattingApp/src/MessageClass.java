package mychatapp;

import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageClass {
    private String recipient;
    private String messageText;
    private String messageID;

    public MessageClass(String recipient, String messageText) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
    }

    private String generateMessageID() {
        return UUID.randomUUID().toString();
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageID() {
        return messageID;
    }

    public String createMessageHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String content = recipient + messageText + messageID;
            byte[] hashBytes = digest.digest(content.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return "hash_error";
        }
    }

    public String printMessageDetails() {
        return "Message ID: " + messageID +
               "\nRecipient: " + recipient +
               "\nMessage: " + messageText +
               "\nHash: " + createMessageHash();
    }
}
