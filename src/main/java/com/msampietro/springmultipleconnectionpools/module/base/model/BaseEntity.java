package com.msampietro.springmultipleconnectionpools.module.base.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity<I extends Serializable> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "base_seq_gen")
    @Column(updatable = false, nullable = false)
    private I id;

}
