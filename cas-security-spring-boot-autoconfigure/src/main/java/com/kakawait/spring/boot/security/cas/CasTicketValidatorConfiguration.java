package com.kakawait.spring.boot.security.cas;

import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author Thibaud Leprêtre
 */
@ConditionalOnMissingBean(TicketValidator.class)
public class CasTicketValidatorConfiguration {

    private final CasSecurityProperties casSecurityProperties;

    public CasTicketValidatorConfiguration(CasSecurityProperties casSecurityProperties) {
        this.casSecurityProperties = casSecurityProperties;
    }

    @Bean
    TicketValidator ticketValidator(List<CasSecurityConfigurer> casSecurityConfigurers) {
        String baseUrl = casSecurityProperties.getServer().getBaseUrl().toASCIIString();
        CasTicketValidatorBuilder builder = new CasTicketValidatorBuilder(baseUrl);
        casSecurityConfigurers.forEach(c -> c.configure(builder));
        return builder.build();
    }
}
