package com.msampietro.springmultipleconnectionpools.module.base.projection;

import java.io.Serializable;

public interface BaseProjection<I extends Serializable> {

    I getId();

}
