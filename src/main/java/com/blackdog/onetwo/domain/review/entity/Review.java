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
import java.util.Set;

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

    @Column(name = "title", length = 40)
    private String title;

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
    private Set<ReviewTag> tags;

    @ElementCollection(targetClass = String.class, fetch = LAZY)
    private Set<String> images;

    public static Review of(Long id, String title, String content, Store store, Users users, Set<ReviewTag> tags, Set<String> images) {
        return new Review(id, title, content, store, users, tags, images);
    }

    public static Review of(String title, String content, Store store, Users users, Set<ReviewTag> tags, Set<String> images) {
        return of(null, title, content, store, users, tags, images);
    }

    public void delete() {
        this.tags = Collections.emptySet();
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isMine(Long seq) {
        return Objects.equals(seq, users.getId());
    }

    public void editReview(String title,
                           String content,
                           Store store,
                           Set<String> images,
                           Set<ReviewTag> tags) {
        this.title = title;
        this.content = content;
        this.store = store;
        this.images = images;
        this.tags = tags;
        this.updatedAt = LocalDateTime.now();
    }
}
