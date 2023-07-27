package com.decode.msapp.fraud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Entity
@Table(name = "fraudchecks")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FraudCheck {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "user_id")
    int userId;

    @Column(name = "date_checked")
    LocalDateTime dateChecked;

    @Column(name = "is_fraudster")
    boolean isFraudster;
}
