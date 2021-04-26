package com.msampietro.springmultipleconnectionpools.module.base.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class IdDTO<I extends Serializable> extends BaseDTO {

    @NotNull
    private I id;

}
