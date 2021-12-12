package com.devnity.devnity.domain.gather.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.setting.annotation.WithJwtAuthUser;
import com.devnity.devnity.setting.provider.GatherProvider;
import com.devnity.devnity.setting.provider.TestHelper;
import com.devnity.devnity.setting.provider.UserProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
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
class GatherApplicantControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  TestHelper testHelper;
  @Autowired
  UserProvider userProvider;
  @Autowired
  GatherProvider gatherProvider;

  @AfterEach
  void tearDown(){
    testHelper.clean();
  }

  @WithJwtAuthUser(email = "me@mail.com", role = UserRole.STUDENT)
  @Test
  void 모집_신청() throws Exception {
    // Given
    User author = userProvider.createUser();
    Gather gather = gatherProvider.createGather(author);

    // When
    ResultActions result = mockMvc.perform(
      post("/api/v1/gathers/{gatherId}/apply", gather.getId())
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Then
    result
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "gathers/applicant/create", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          pathParameters(
            parameterWithName("gatherId").description("모집 게시글 ID")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버시간"),
            fieldWithPath("data").type(JsonFieldType.STRING).description("성공 여부")
          )
        )
      );
  }

  @WithJwtAuthUser(email = "me@mail.com", role = UserRole.STUDENT)
  @Test
  void 모집_신청_취소() throws Exception {
    // Given
    User author = userProvider.createUser();
    Gather gather = gatherProvider.createGather(author);
    User me = userProvider.findMe("me@mail.com");
    gatherProvider.createApplicant(me, gather);

    // When
    ResultActions result = mockMvc.perform(
      delete("/api/v1/gathers/{gatherId}/apply", gather.getId())
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Then
    result
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "gathers/applicant/delete", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          pathParameters(
            parameterWithName("gatherId").description("모집 게시글 ID")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버시간"),
            fieldWithPath("data").type(JsonFieldType.STRING).description("성공 여부")
          )
        )
      );
  }

}