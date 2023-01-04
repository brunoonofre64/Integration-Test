package io.brunoonofre64.infrastructure.config.security;

public interface ConstantVariables {

    String ADMIN = "ADMIN";

    String MANAGER = "MANAGER";

    String EMPLOYEE = "EMPLOYEE";

    String WEB_REQUEST_V1_CUSTOMER = "/api/v1/cliente/**";

    String WEB_REQUEST_V1_EMPLOYEE = "/api/v1/funcionario/**";

    String WEB_REQUEST_V1_ORDER = "/api/v1/pedido/**";

    String WEB_REQUEST_V1_PRODUCT = "/api/v1/produto/**";

    String WEB_REQUEST_V1_USER = "/api/v1/usuario/**";

}
