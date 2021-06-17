package com.example.javapaystack.paystackconfiguration;

import com.example.javapaystack.DTO.PayStackBearer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.validation.constraints.Digits;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitializeTransactionRequest {

    /**
     Amount in kobo
     */
    @Digits(integer = 9, fraction = 0)
    private Integer amount;

    private String email;

    private String plan;

    private String reference;

    private String subaccount;

    private PayStackBearer.Account bearer = PayStackBearer.account;

    private String callbackUrl;

    private Float quantity;

    private Integer invoice_limit;

    private Integer transaction_charge;

    private List<String> channel;
}
