package com.msampietro.springdownloadmultiplepools.module.actor.controller;

import com.msampietro.springdownloadmultiplepools.module.actor.dto.ActorDTO;
import com.msampietro.springdownloadmultiplepools.module.actor.model.Actor;
import com.msampietro.springdownloadmultiplepools.module.actor.service.ActorExportService;
import com.msampietro.springdownloadmultiplepools.module.actor.service.ActorService;
import com.msampietro.springdownloadmultiplepools.module.base.controller.BaseControllerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value = "/actors")
public class ActorController extends BaseControllerImpl<Actor, Long, ActorDTO> {

    private final ActorExportService actorExportService;

    @Autowired
    public ActorController(ActorService actorService,
                           ActorExportService actorExportService) {
        super(actorService);
        this.actorExportService = actorExportService;
    }

    @GetMapping(value = "/data")
    public ResponseEntity<StreamingResponseBody> getData(HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=actors_export.csv");
        StreamingResponseBody stream = actorExportService::exportStreamToCsv;
        return ResponseEntity.ok(stream);
    }

}
