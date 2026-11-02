package com.mycompany.poepart2;
import javax.swing.JOptionPane;

public class Quickchat {
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
        int max = Integer.parseInt(JOptionPane.showInputDialog("How many messages do you wish to enter?"));
        int count = 1;
        int choice = 0;
        while(choice != 3) {
            choice = Integer.parseInt(JOptionPane.showInputDialog("1) Send Messages\n2) Show recently sent messages\n3) Quit"));
            if(choice == 1) {
                if(count > max) { JOptionPane.showMessageDialog(null, "Please fill in all messages limit reached"); continue; }
                String recipient = JOptionPane.showInputDialog("Enter recipient (+27718693002):");
                String text = JOptionPane.showInputDialog("Enter message:");
                Message m = new Message(count, recipient, text);
                JOptionPane.showMessageDialog(null, m.checkMessageLength());
                JOptionPane.showMessageDialog(null, m.checkRecipientCell());
                if(!m.checkMessageLength().equals("Message ready to send.") || !m.checkRecipientCell().equals("Cell phone number successfully captured.")) continue;
                String res = m.SentMessage();
                JOptionPane.showMessageDialog(null, res);
                if(res.contains("successfully")) count++;
            } else if(choice == 2) {
                JOptionPane.showMessageDialog(null, "Coming Soon.");
            } else if(choice == 3) {
                Message d = new Message(0,"","");
                JOptionPane.showMessageDialog(null, "Total: " + d.returnTotalMessagess() + "\n" + d.printMessages());
                break;
            }
        }
    }
}