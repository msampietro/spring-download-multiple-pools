package com.msampietro.springmultipleconnectionpools.projection;

import java.io.Serializable;

public interface BaseProjection<I extends Serializable> {

    I getId();

}
