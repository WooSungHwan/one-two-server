package com.blackdog.onetwo.domain.review.entity;

import com.blackdog.onetwo.domain.common.entity.BaseEntity;
import com.blackdog.onetwo.domain.review.enums.ReviewTag;
import com.blackdog.onetwo.domain.store.entity.Store;
import com.blackdog.onetwo.domain.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@DynamicUpdate
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

    @ElementCollection(targetClass = ReviewTag.class, fetch = LAZY)
    private List<ReviewTag> tags;

    public static Review of(Long id, String content, Store store, Users users, List<ReviewTag> tags) {
        return new Review(id, content, store, users, tags);
    }

    public static Review of(String content, Store store, Users users, List<ReviewTag> tags) {
        return of(null, content, store, users, tags);
    }

    public void delete() {
        this.tags = Collections.emptyList();
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isMine(Long seq) {
        return Objects.equals(seq, users.getId());
    }
}
