package com.g_29.projectManagementSystem.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentLinkResponse {

    private String paymentLinkUrl;
    private String paymentLinkId;
}
