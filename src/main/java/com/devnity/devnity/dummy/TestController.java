package com.devnity.devnity.dummy;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping("/api/test")
@RequiredArgsConstructor
@RestController
public class TestController {

    private final TestService testService;

    @PostMapping
    public Map<String, String> insertDummy() {
        return testService.insertDummy();
    }

//    @PostMapping("/image")
//    public Map<String, String> insertImage(@RequestBody DummyImageRequest request) {
//        return testService.insertImage(request);
//    }

    @PostMapping("/image/temp")
    public Map<String, String> insertImageTemp(
            @RequestPart(value = "file", required = false) MultipartFile imageFile,
            @RequestPart(value = "json") JsonRequest request
    ) {
        return testService.insertImageTemp(imageFile, request);
    }
}
