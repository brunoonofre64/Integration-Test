package io.brunoonofre64.domain.enums;

import lombok.Getter;

@Getter
public enum CodeMessage {

    DTO_NULL_OR_IS_EMPTY("DTO_NULL_OR_IS_EMPTY"),

    UUID_NOT_FOUND_OR_NULL("UUID_NOT_FOUND_OR_NULL"),

    LIST_IS_EMPTY("LIST_IS_EMPTY"),

    ENTITY_ISNULL("ENTITY_ISNULL"),

    INVALID_REQUEST("INVALID_REQUEST"),

    FIELD_NULL_OR_IS_EMPTY("FIELD_NULL_OR_IS_EMPTY"),

    CPF_INVALID_FORMAT("CPF_INVALID_FORMAT"),

    CPF_REPEATED("CPF_REPEATED"),

    PRODUCT_NOT_FOUND("PRODUCT_NOT_FOUND"),

    ORDER_NOT_FOUND("ORDER_NOT_FOUND"),

    OBJECTS_ISNULL_OR_EMPTY("OBJECTS_ISNULL_OR_EMPTY"),

    LOGIN_NOT_FOUND("LOGIN_NOT_FOUND");

    final String value;

    CodeMessage(String value) {
        this.value = value;
    }
}
