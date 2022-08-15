package com.sequra.disbursements.infrastructure.inbound.adapters;

import com.sequra.disbursements.application.DisbursementAppService;
import com.sequra.disbursements.application.dto.DisbursementDTO;
import com.sequra.disbursements.application.dto.DisbursementsDTO;
import com.sequra.disbursements.application.dto.FilterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/disbursements")
public class DisbursementsController {
    private static final Logger log = LoggerFactory.getLogger(DisbursementsController.class);

    private DisbursementAppService disbursementAppService;

    @Inject
    DisbursementsController(DisbursementAppService disbursementAppService) {
        this.disbursementAppService = disbursementAppService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<DisbursementDTO>> filter(@ModelAttribute FilterDTO filterDTO) {
        log.info("GET /v1/disbursements body: {}", filterDTO);
        DisbursementsDTO disbursementsDTO = disbursementAppService.getDisbursements(filterDTO);
        return Optional.ofNullable(disbursementsDTO.getDisbursements())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/{year}/{week}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<DisbursementDTO>> calculateAndPersistDisbursements(@PathVariable(required = true) Integer year,
                                                                           @PathVariable(required = true) Integer week) {
        log.info("POST /v1/disbursements/{}/{}", year, week);
        DisbursementsDTO disbursementsDTO = disbursementAppService.calculateDisbursements(year, week);
        return Optional.ofNullable(disbursementsDTO.getDisbursements())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
