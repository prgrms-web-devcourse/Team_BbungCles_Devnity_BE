package com.devnity.devnity.common.error.apitest.dto;

import com.devnity.devnity.common.error.apitest.TestEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class TestRequest {

    private TestEnum testEnum;
}
