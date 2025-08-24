package com.example.water;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.List;

public class TwilioService {

    // Replace with your Twilio Account SID and Auth Token
    private static final String ACCOUNT_SID = "";
    private static final String AUTH_TOKEN = "";

    // Replace with your Twilio phone number (the sender)
    private static final String FROM_PHONE = "";

    static {
        // Initialize Twilio once when the class is loaded
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    /**
     * Send an SMS message to a list of phone numbers
     */
    public void sendBulkSms(List<String> phoneNumbers, String message) {
        for (String toPhone : phoneNumbers) {
            try {
                Message sms = Message.creator(
                        new PhoneNumber(toPhone),   // recipient
                        new PhoneNumber(FROM_PHONE), // sender
                        message
                ).create();

                System.out.println(" SMS sent to " + toPhone + " (SID: " + sms.getSid() + ")");
            } catch (Exception e) {
                System.out.println(" Failed to send SMS to " + toPhone);
                e.printStackTrace();
            }
        }
    }
}

