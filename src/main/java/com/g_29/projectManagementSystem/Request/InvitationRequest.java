package com.g_29.projectManagementSystem.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvitationRequest {
    private Long projectId;
    private String email;
}
