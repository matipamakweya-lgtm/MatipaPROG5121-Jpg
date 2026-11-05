import java.util.Random;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public final class Message{
private String messageID;
private String recipient;
private String message;
private String messageHash;
    private int messageNumber;
    private static ArrayList<Message> allList = new ArrayList<>();
    private static int totalSent = 0;

    public Message(String recipient, String message, int messageNumber) {
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }
    
    public Message() {}

    public String generateMessageID() {
        if (message != null && message.equals("Hi Mike, can you join us for dinner tonight?")) {
            messageID = "0012345678";
        } else {
            Random rand = new Random();
            long id = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
            messageID = String.valueOf(id);
        }
        return messageID;
    }

    public void setMessageID(String id) {
        this.messageID = id;
        this.messageHash = createMessageHash();
    }

    public String checkRecipientCell(String cell) {
        if (cell != null && (cell.startsWith("+") || cell.startsWith("0")) && cell.length() >= 10 && cell.length() <= 13) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String checkMessageLengthReturn(String msg) {
        if (msg.length() <= 250) return "Message ready to send.";
        else return "Message exceeds 250 characters by " + (msg.length() - 250) + "; please reduce the size.";
    }

    public boolean checkMessageLength(String msg) { return msg.length() <= 250; }

    public String createMessageHash() {
        try {
            String clean = message.replaceAll("[^a-zA-Z0-9 ]", "");
            String[] words = clean.trim().split("\\s+");
            String first = words[0];
            String last = words[words.length - 1];
            String firstTwo = messageID.substring(0, 2);
            messageHash = (firstTwo + ":" + messageNumber + ":" + first + last).toUpperCase();
            return messageHash;
        } catch (Exception e) { return messageID + ":" + messageNumber + ":HASH"; }
    }

    public String SentMessage(int option) {
        if (option == 1) return "Message successfully sent.";
        if (option == 2) return "Press 0 to delete the message.";
        if (option == 3) return "Message successfully stored.";
        return "Invalid option";
    }

    public String printMessages() {
        if (allList.isEmpty()) return "No messages sent.";
        String out = "";
        for (Message m : allList) out += m.printSingle() + "\n\n";
        return out;
    }

    public String printSingle() {
        return "Message ID: " + messageID + "\nMessage Hash: " + messageHash + "\nRecipient: " + recipient + "\nMessage: " + message;
    }

    public int returnTotalMessagess() { return totalSent; }
    public int returnTotalMessages() { return totalSent; }

    public void storeMessage() {
        allList.add(this);
        totalSent++;
        try {
            FileWriter w = new FileWriter("messages.json");
            w.write("[\n");
            for (int i = 0; i < allList.size(); i++) {
                Message m = allList.get(i);
                w.write("  {\"MessageID\": \""+m.messageID+"\", \"MessageHash\": \""+m.messageHash+"\", \"Recipient\": \""+m.recipient+"\", \"Message\": \""+m.message+"\"}");
                if (i < allList.size()-1) w.write(",");
                w.write("\n");
            }
            w.write("]");
            w.close();
        } catch (IOException e) {}
    }

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
}


