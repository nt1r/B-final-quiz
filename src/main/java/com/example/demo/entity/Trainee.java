package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "trainee")
public class Trainee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    String name;

    @NotBlank
    String office;

    @NotBlank
    @Email
    String email;

    @NotBlank
    String github;

    @NotBlank
    String zoomId;

    @NotNull
    @Builder.Default
    boolean isGrouped = false;
}
