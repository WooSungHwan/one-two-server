package com.blackdog.onetwo.domain.user.entity;

import com.blackdog.onetwo.domain.user.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "users_taste")
@Entity
public class UserTaste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_TASTE_USERS_ID")
    )
    private Users users;

    @Column(name = "gender_step", length = 20)
    private GenderStep genderStep;

    @Column(name = "price_step", length = 20)
    private PriceStep priceStep;

    @Column(name = "alcohol_step", length = 20)
    private AlcoholStep alcoholStep;

    @Column(name = "fresh_food_step", length = 20)
    private FreshFoodStep freshFoodStep;

    @Column(name = "play_step", length = 20)
    private PlayStep playStep;

    @Column(name = "time_step", length = 20)
    private TimeStep timeStep;

    public static UserTaste of(Users users,
                               GenderStep genderStep,
                               PriceStep priceStep,
                               AlcoholStep alcoholStep,
                               FreshFoodStep freshFoodStep,
                               PlayStep playStep,
                               TimeStep timeStep) {
        return new UserTaste(null, users, genderStep, priceStep, alcoholStep, freshFoodStep, playStep, timeStep);
    }

}
