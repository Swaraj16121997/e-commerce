package com.example.ecommerce.payment.controllers;

import com.stripe.model.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static spark.Spark.post;
import static spark.Spark.port;
import com.google.gson.JsonSyntaxException;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.*;
import com.stripe.net.Webhook;

@RestController
@RequestMapping("/stripeWebhook")   // this is the webhook endpoint which is exposed to the payment gateway, for payment service to get notified about the payments which happened using the payment link it had created and sent to the client
public class StripeWebhookController {
    @PostMapping("/receiveEvents")
    public void receiveWebhookEvents(Event event) {
        System.out.println("Webhook is listening to Stripe Events");
        // Read events such as payment_intent.succeeded, payout.failed, etc. (stripe's webhook page in the dashboard has the required code) and take the required actions accordingly
        // eg., if(event.getType() == payment_intent.succeeded) { code to update the payment table in the database }
    }
}