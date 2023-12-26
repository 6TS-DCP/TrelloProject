package com.bts.trelloproject.user.entity;

import com.bts.trelloproject.global.common.BaseLastModifiedTimeEntity;
import jakarta.persistence.Id;

public class User extends BaseLastModifiedTimeEntity {

    @Id
    private Long userId;
}
