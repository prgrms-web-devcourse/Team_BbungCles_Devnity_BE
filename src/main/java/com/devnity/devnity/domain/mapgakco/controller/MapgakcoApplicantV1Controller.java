package com.devnity.devnity.domain.mapgakco.controller;

import com.devnity.devnity.domain.mapgakco.service.MapgakcoApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class MapgakcoApplicantV1Controller {

    private final MapgakcoApplicantService mapgakcoApplicantService;

}
