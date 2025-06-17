package com.g_29.projectManagementSystem.Service;

import com.g_29.projectManagementSystem.Enums.PlanType;
import com.g_29.projectManagementSystem.Repository.SubscriptionRepo;
import com.g_29.projectManagementSystem.model.Subscription;
import com.g_29.projectManagementSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @Override
    public Subscription createSubscription(User user) {

        Subscription subscription=new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));

        subscription.setValid(true);
        subscription.setPlaneType(PlanType.FREE);

        return subscriptionRepo.save(subscription);

    }

    @Override
    public Subscription getUsersSubscription(Long userId) throws Exception {
        Subscription subscription= subscriptionRepo.findByUserId(userId);
        if(!isValid(subscription)){
            subscription.setPlaneType(PlanType.FREE);
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
            subscription.setSubscriptionStartDate(LocalDate.now());
        }

        return subscriptionRepo.save(subscription);
    }

    @Override
    public Subscription upgradeSubscription(Long userId, PlanType planType) {
        Subscription subscription=subscriptionRepo.findByUserId(userId);
        subscription.setPlaneType(planType);
        subscription.setSubscriptionStartDate(LocalDate.now());
        if(planType.equals(PlanType.ANNUALLY)){
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        }else if(planType.equals(PlanType.MONTHLY)){
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
        }

        return subscriptionRepo.save(subscription);
    }

    @Override
    public boolean isValid(Subscription subscription) {
        if(subscription.getPlaneType().equals(PlanType.FREE)){
            return true;
        }
        LocalDate endDate=subscription.getSubscriptionEndDate();
        LocalDate currentDate=LocalDate.now();
        return endDate.isAfter(currentDate)||endDate.isEqual(currentDate);
    }
}
