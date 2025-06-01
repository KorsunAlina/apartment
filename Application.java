package com.apartment.exchange.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String applicantName;

    @OneToOne(cascade = CascadeType.ALL)
    private Apartment offeredApartment;

    @OneToOne(cascade = CascadeType.ALL)
    private Apartment desiredApartment;
}
