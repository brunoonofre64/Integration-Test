package io.brunoonofre64.infrastructure.config.security;

public interface ConstantVariables {

    String ADMIN = "ADMIN";

    String MANAGER = "MANAGER";

    String EMPLOYEE = "EMPLOYEE";

    interface WEB_REQUEST{
        String V1_CUSTOMER = "/api/v1/cliente/**";

        String V1_EMPLOYEE = "/api/v1/funcionario/**";

        String V1_ORDER = "/api/v1/pedido/**";

        String V1_PRODUCT = "/api/v1/produto/**";

        String V1_USER = "/api/v1/usuario/**";
    }

}
