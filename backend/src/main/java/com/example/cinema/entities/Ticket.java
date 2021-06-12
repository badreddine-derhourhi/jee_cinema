package com.example.cinema.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomClient;
    private double prix;
    @Column(unique = false)
    private Long codePayement;
    private boolean reserved;
    @ManyToOne
    private Place place;
    @ManyToOne
    private Projection projection;

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nomClient='" + getNomClient() + "'" +
            ", prix='" + getPrix() + "'" +
            ", codePayement='" + getCodePayement() + "'" +
            ", reserved='" + isReserved() + "'" +
            "}";
    }
}
