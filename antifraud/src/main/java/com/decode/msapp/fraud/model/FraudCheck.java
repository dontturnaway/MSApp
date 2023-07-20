package com.decode.msapp.fraud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

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
    Timestamp dateChecked;

    @Column(name = "is_fraudster")
    boolean isFraudster;
}
