package com.msampietro.springmultipleconnectionpools.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MovieDTO extends BaseDTO {

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    private Integer ranking;

    @Valid
    @NotEmpty
    private List<IdDTO<Long>> actors;

}