package com.devnity.devnity.domain.mapgakco.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.setting.annotation.WithJwtAuthUser;
import com.devnity.devnity.setting.provider.MapgakcoProvider;
import com.devnity.devnity.setting.provider.TestHelper;
import com.devnity.devnity.setting.provider.UserProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
class MapgakcoApplicantControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  MapgakcoProvider mapgakcoProvider;
  @Autowired
  UserRepository userRepository;
  @Autowired
  UserProvider userProvider;
  @Autowired
  TestHelper testHelper;

  private User user;
  private User user2;
  private Mapgakco mapgakco;

  @BeforeEach
  void setUp() throws Exception {
    user = userProvider.createUser();
    user2 = userRepository.findUserByEmail("email@gmail.com").get();
    mapgakco = mapgakcoProvider.createMapgakco(user);
  }

  @AfterEach
  void tearDown() {
    testHelper.clean();
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("???????????? ????????? ??? ??????.")
  void applyMapgakcoTest() throws Exception {
    // given
    Long mapgakcoId = mapgakco.getId();

    // when
    ResultActions actions = mockMvc.perform(
      post("/api/v1/mapgakcos/{mapgakcoId}/apply", mapgakcoId)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "JSON WEB TOKEN"));

    // then
    actions.andExpect(status().isOk())
      .andDo(print())
      .andDo(document("mapgakcos/applicant/applyMapgakco",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        pathParameters(
          parameterWithName("mapgakcoId").description(JsonFieldType.NUMBER).description("????????? ID")
        ),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
          fieldWithPath("serverDatetime").type(STRING).description("?????? ??????"),
          fieldWithPath("data.status").type(STRING).description("????????? status")
        )
      ));
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("???????????? ?????? ????????? ??? ??????.")
  void cancelMapgakcoTest() throws Exception {
    // given
    Long mapgakcoId = mapgakco.getId();
    mapgakcoProvider.createApplicant(user2, mapgakco);

    // when
    ResultActions actions = mockMvc.perform(
      delete("/api/v1/mapgakcos/{mapgakcoId}/apply", mapgakcoId)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "JSON WEB TOKEN"));

    // then
    actions.andExpect(status().isOk())
      .andDo(print())
      .andDo(document("mapgakcos/applicant/cancelMapgakco",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        pathParameters(
          parameterWithName("mapgakcoId").description(JsonFieldType.NUMBER).description("????????? ID")
        ),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
          fieldWithPath("serverDatetime").type(STRING).description("?????? ??????"),
          fieldWithPath("data").type(STRING).description("?????? ?????????")
        )
      ));
  }
}