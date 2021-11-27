package com.devnity.devnity.dummy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TestService {
    private final TestRepository testRepository;

    @Transactional
    public String insertDummy() {
        TestEntity dummy = TestEntity.builder()
                .str("dummy")
                .build();
        testRepository.save(dummy);
        return "success";
    }
}
