package com.g_29.projectManagementSystem.model;

import com.g_29.projectManagementSystem.Enums.PlanType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionEndDate;
    private PlanType planeType;

    private boolean isValid;

    @OneToOne
    private User user;
}
