package org.example.repository;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InputDto {

    private UUID uuid;
    private String organizationId;
    private String organizationName;
    private String invoiceDate;
    private String invoiceTotal;
    private String invoiceNumber;
    private String customerName;
    private String customerEmail;
    private String paymentMethod;

}