package com.emretemir.laboratorymanagementsystem.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Long id;
    @Column(name = "hAdi", length = 25, nullable = false)
    private String name;
    @Column(name = "hSoyadi", length = 25, nullable = false)
    private String surName;
    @Column(name = "HTCNo", length = 11, nullable = false)
    private Long identifyNumber;
    @Column(name = "hTaniBasligi", length = 70, nullable = false)
    private String diagnosisTitle;
    @Column(name = "hTaniDetayi", length = 700, nullable = false)
    private String diagnosisInfo;
    @Column(name = "hRaporTarihi", length = 20, nullable = false)
    private String reportDate;
    @Column(name="hRaporFotografi")
    private String reportPic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;
}