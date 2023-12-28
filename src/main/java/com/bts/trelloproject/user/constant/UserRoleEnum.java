package com.bts.trelloproject.user.constant;

import lombok.Getter;

@Getter
public enum UserRoleEnum {

    USER( Authority.USER, "사용자" ),
    ADMIN( Authority.ADMIN, "관리자" );

    private final String authority;
    private final String value;

    UserRoleEnum( String authority, String value) {
        if( authority == null ) {
            authority = Authority.USER;
        }
        if( value == null ){
           value = "사용자";
        }
        this.authority = authority;
        this.value = value;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}
