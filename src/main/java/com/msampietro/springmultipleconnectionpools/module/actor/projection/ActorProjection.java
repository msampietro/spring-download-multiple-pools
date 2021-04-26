package com.msampietro.springmultipleconnectionpools.module.actor.projection;

import com.msampietro.springmultipleconnectionpools.module.base.projection.BaseProjection;

public interface ActorProjection extends BaseProjection<Long> {

    String getName();

    String getLastName();

}
