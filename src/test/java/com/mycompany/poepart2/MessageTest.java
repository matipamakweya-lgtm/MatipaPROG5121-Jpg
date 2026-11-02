package com.mycompany.poepart2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    String r1="+27718693002"; String m1="Hi Mike, can you join us for dinner tonight?";
    String r2="08575975889"; String m2="Hi Keegan, did you receive the payment?";
    @Test public void testHash() {
        Message msg=new Message("0012345678",0,r1,m1);
        assertEquals("00:0:HITONIGHT",msg.createMessageHash());
    }
    @Test public void testRecipientSuccess() {
        assertEquals("Cell phone number successfully captured.",new Message("0012345678",0,r1,m1).checkRecipientCell());
    }
    @Test public void testRecipientFailure() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",new Message("0012345678",0,r2,m2).checkRecipientCell());
    }
    @Test public void testSend() {
        Message.allMessages.clear(); Message.totalMessagesSent=0;
        assertEquals("Message successfully sent.",new Message("0012345678",0,r1,m1).SentMessage("Send Message"));
        assertEquals("Press 0 to delete the message.",new Message("0012345678",0,r1,m1).SentMessage("Disregard Message"));
        assertEquals("Message successfully stored.",new Message("0012345678",0,r1,m1).SentMessage("Store Message"));
    }
}
