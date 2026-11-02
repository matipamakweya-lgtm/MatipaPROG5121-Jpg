package com.mycompany.poepart2;
//Textbook :Farrell, J. 2023. Java Programming 10th Ed. Cengage
// Chapter 4 Loops p.210, Chapter 5 String p.268, Chapter 7 Random p.340
import java.util.Random;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
public class Message {
    private String messageId;
    private String recipient;
    private String messageText;
    private String messageHash;
    private int messageNumber;
    public static ArrayList<Message> allMessages = new ArrayList<>();
    public static int totalMessagesSent = 0;

    public Message(int msgNum, String recipient, String message) {
        this.messageNumber = msgNum;
        this.recipient = recipient;
        this.messageText = message;
        this.messageId = generateMessageID();
        this.messageHash = createMessageHash();
    }

    public Message(String forcedId, int msgNum, String recipient, String message) {
        this.messageId = forcedId;
        this.messageNumber = msgNum;
        this.recipient = recipient;
        this.messageText = message;
        this.messageHash = createMessageHash();
    }

    public String generateMessageID() {
        Random rand = new Random();
        String id = "";
        for(int i=0;i<10;i++) id+=rand.nextInt(10);
        return id;
    }

    public Boolean checkMessageID() {
        return messageId.length() <= 10;
    }

    public String checkRecipientCell() {
        if(recipient!=null && recipient.startsWith("+") && recipient.length()>=10 && recipient.length()<=13)
            return "Cell phone number successfully captured.";
        if(recipient!=null && recipient.contains("+") && recipient.length()<=10)
            return "Cell phone number successfully captured.";
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String createMessageHash() {
        String firstTwo = messageId.substring(0,2);
        String[] w = messageText.trim().split("\\s+");
        String first = w[0].replaceAll("[^a-zA-Z0-9]","");
        String last = w[w.length-1].replaceAll("[^a-zA-Z0-9?]","").replace("?","");
        messageHash = (firstTwo+":"+messageNumber+":"+first+last).toUpperCase();
        return messageHash;
    }

    public String checkMessageLength() {
        if(messageText.length()<=250) return "Message ready to send.";
        int ex = messageText.length()-250;
        return "Message exceeds 250 characters by "+ex+"; please reduce the size.";
    }

    public String SentMessage() {
        String choice = JOptionPane.showInputDialog(printSingle()+"\n\n1) Send Message\n2) Disregard Message\n3) Store Message\nEnter 1-3:");
        if(choice==null) return "Cancelled";
        if(choice.equals("1")) { allMessages.add(this); totalMessagesSent++; storeMessage(); return "Message successfully sent."; }
        if(choice.equals("2")) return "Press 0 to delete the message.";
        if(choice.equals("3")) { storeMessage(); return "Message successfully stored."; }
        return "Invalid";
    }

    public String SentMessage(String option) {
        if(option.equalsIgnoreCase("Send")||option.equals("1")||option.equalsIgnoreCase("Send Message")) {
            allMessages.add(this); totalMessagesSent++; storeMessage();
            return "Message successfully sent.";
        } else if(option.equalsIgnoreCase("Disregard")||option.equals("2")||option.equalsIgnoreCase("Disregard Message")) {
            return "Press 0 to delete the message.";
        } else if(option.equalsIgnoreCase("Store")||option.equals("3")||option.equalsIgnoreCase("Store Message")) {
            storeMessage(); return "Message successfully stored.";
        }
        return "Invalid";
    }

    public String printMessages() {
        String r="";
        for(Message m: allMessages) r+="MessageID: "+m.messageId+"\nMessage Hash: "+m.messageHash+"\nRecipient: "+m.recipient+"\nMessage: "+m.messageText+"\n\n";
        if(r.equals("")) return "No messages sent yet.";
        return r;
    }
    public String printSingle() {
        return "MessageID: "+messageId+"\nMessage Hash: "+messageHash+"\nRecipient: "+recipient+"\nMessage: "+messageText;
    }
    public int returnTotalMessagess() { return totalMessagesSent; }
    public void storeMessage() {
        try { FileWriter w=new FileWriter("messages.json"); w.write("[\n");
            for(int i=0;i<allMessages.size();i++){ Message m=allMessages.get(i);
                w.write("  {\"MessageID\":\""+m.messageId+"\",\"MessageHash\":\""+m.messageHash+"\",\"Recipient\":\""+m.recipient+"\",\"Message\":\""+m.messageText.replace("\"","'")+"\"}");
                if(i<allMessages.size()-1) w.write(",\n");
            } w.write("\n]"); w.close();
        } catch(IOException e){ System.out.println(e.getMessage()); }
    }
    public String getMessageId(){ return messageId; }
    public String getMessageHash(){ return messageHash; }
}