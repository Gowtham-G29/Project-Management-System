package com.g_29.projectManagementSystem.Controller;

import com.g_29.projectManagementSystem.Enums.PlanType;
import com.g_29.projectManagementSystem.Response.PaymentLinkResponse;
import com.g_29.projectManagementSystem.Service.UserServiceImpl;
import com.g_29.projectManagementSystem.model.User;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/{planType}")
    public ResponseEntity<PaymentLinkResponse>createPayLink(@PathVariable PlanType planType,
                                                            @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userServiceImpl.findUserProfileByJwt(jwt);

        //paisa to ruppee
        int amount=1*100;
        if(planType.equals(PlanType.ANNUALLY)){
            amount=amount*12;
            amount=(int)(amount*0.7);
        }

            RazorpayClient razorpayClient=new RazorpayClient(apiKey,apiSecret);

            JSONObject paymentLinkRequest =new JSONObject();
            paymentLinkRequest.put("amount",amount);
            paymentLinkRequest.put("currency","INR");

            JSONObject customer=new JSONObject();
            customer.put("name",user.getFullName());
            customer.put("email",user.getEmail());

            paymentLinkRequest.put("customer",customer);

            JSONObject notify=new JSONObject();
            notify.put("email",true);

            paymentLinkRequest.put("notify",notify);

            paymentLinkRequest.put("callback_url","http://localhost:5173/upgrade_plan/success?planType="+planType);

            PaymentLink payment= razorpayClient.paymentLink.create(paymentLinkRequest);

            String paymentLinkId=payment.get("id");
            String paymentLinkUrl=payment.get("short_url");

            PaymentLinkResponse response=new PaymentLinkResponse();
            response.setPaymentLinkUrl(paymentLinkUrl);
            response.setPaymentLinkId(paymentLinkId);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
