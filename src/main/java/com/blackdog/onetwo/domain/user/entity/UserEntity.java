package com.blackdog.onetwo.domain.user.entity;

import javax.persistence.*;

@Table(name = "users")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
