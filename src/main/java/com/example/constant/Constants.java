package com.example.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String ENTITY_NOT_FOUND_EXCEPTION_MSG = "User not found by email '%s'";

    public static final String USER_BY_EMAIL_ALREADY_EXISTS_EXCEPTION_MSG =
            "User by email '%s' is already exists";
}
