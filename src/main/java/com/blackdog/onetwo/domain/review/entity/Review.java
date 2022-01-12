package com.blackdog.onetwo.domain.review.entity;

import com.blackdog.onetwo.domain.common.BaseEntity;
import com.blackdog.onetwo.domain.store.entity.Store;
import com.blackdog.onetwo.domain.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "reviews")
@Entity
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stores_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_REVIEWS_STORE_ID"))
    private Store store;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_REVIEWS_USERS_ID")
    )
    private Users users;

    public static Review of(Long id, String content, Store store, Users users) {
        return new Review(id, content, store, users);
    }

    public static Review of(String content, Store store, Users users) {
        return of(null, content, store, users);
    }

}
