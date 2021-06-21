package com.msampietro.springdownloadmultiplepools.module.actor.projection;

import com.msampietro.springdownloadmultiplepools.module.base.projection.BaseProjection;

public interface ActorProjection extends BaseProjection<Long> {

    String getName();

    String getLastName();

}
