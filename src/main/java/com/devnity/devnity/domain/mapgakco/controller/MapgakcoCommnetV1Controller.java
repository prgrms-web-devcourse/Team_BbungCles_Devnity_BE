package com.devnity.devnity.domain.mapgakco.controller;

import com.devnity.devnity.domain.mapgakco.service.mapgakcocomment.MapgakcoCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class MapgakcoCommnetV1Controller {

    private final MapgakcoCommentService mapgakcoCommentService;

}
