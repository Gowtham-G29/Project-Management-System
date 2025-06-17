package com.g_29.projectManagementSystem.Controller;


import com.g_29.projectManagementSystem.Enums.PlanType;
import com.g_29.projectManagementSystem.Service.SubscriptionServiceImpl;
import com.g_29.projectManagementSystem.Service.UserServiceImpl;
import com.g_29.projectManagementSystem.model.Subscription;
import com.g_29.projectManagementSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionServiceImpl subscriptionServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/getSubscription")
    public ResponseEntity<Subscription>getUserSubscription(@RequestHeader("AUTHORIZATION")String jwt) throws Exception {
        User user=userServiceImpl.findUserProfileByJwt(jwt);

        Subscription subscription=subscriptionServiceImpl.getUsersSubscription(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(subscription);
    }

    @PatchMapping("/upgradeSubscription")
    public ResponseEntity<Subscription>upgradeSubscription(@RequestHeader("AUTHORIZATION")String jwt,
                                                           @RequestParam PlanType planType) throws Exception {
        User user=userServiceImpl.findUserProfileByJwt(jwt);

        Subscription subscription=subscriptionServiceImpl.upgradeSubscription(user.getId(),planType);

        return ResponseEntity.status(HttpStatus.OK).body(subscription);
    }

}
