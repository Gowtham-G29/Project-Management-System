package com.g_29.projectManagementSystem.Service;

import com.g_29.projectManagementSystem.Enums.PlanType;
import com.g_29.projectManagementSystem.model.Subscription;
import com.g_29.projectManagementSystem.model.User;

public interface SubscriptionService {

    Subscription createSubscription(User user);

    Subscription getUsersSubscription(Long userId)throws Exception;

    Subscription upgradeSubscription(Long userId, PlanType planType);

    boolean isValid(Subscription subscription);
}
