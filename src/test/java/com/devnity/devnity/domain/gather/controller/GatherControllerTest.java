package com.devnity.devnity.domain.gather.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devnity.devnity.domain.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.setting.annotation.WithJwtAuthUser;
import com.devnity.devnity.setting.provider.TestHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class GatherControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  TestHelper testHelper;

  @AfterEach
  void tearDown() {
    testHelper.clean();
  }

  @WithJwtAuthUser(email = "me@mail.com", role = UserRole.STUDENT)
  @Test
  void 모집_게시글_등록() throws Exception {
    // Given
    String request = objectMapper.writeValueAsString(
      CreateGatherRequest.builder()
        .title("제목 : 코테 스터디 모집해용!!!")
        .applicantLimit(5)
        .deadline(LocalDateTime.now())
        .content("### 게시글 내용 (마크다운)")
        .category(GatherCategory.STUDY)
        .build()
    );

    // When
    ResultActions result = mockMvc.perform(
      post("/api/v1/gathers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(request)
    );

    // Then
    result
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "gathers/create", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          requestFields(
            fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
            fieldWithPath("applicantLimit").type(JsonFieldType.NUMBER).description("마감 시각"),
            fieldWithPath("deadline").type(JsonFieldType.STRING).description("마감 일자"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용(마크다운)"),
            fieldWithPath("category").type(JsonFieldType.STRING).description("모집 유형")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버시간"),
            fieldWithPath("data.status").type(JsonFieldType.STRING).description("게시물 상태")
          )
        )
      );

  }


}