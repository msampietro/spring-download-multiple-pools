package com.msampietro.springmultipleconnectionpools.module.actor.dto;

import com.msampietro.springmultipleconnectionpools.module.base.dto.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ActorDTO extends BaseDTO {

    @NotNull
    @Size(max = 50)
    private String lastName;

    @NotNull
    @Size(max = 50)
    private String name;

}
