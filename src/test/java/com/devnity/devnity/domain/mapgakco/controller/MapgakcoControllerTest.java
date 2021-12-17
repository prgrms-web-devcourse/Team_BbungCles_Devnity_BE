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
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.setting.annotation.WithJwtAuthUser;
import com.devnity.devnity.setting.provider.MapgakcoProvider;
import com.devnity.devnity.setting.provider.TestHelper;
import com.devnity.devnity.setting.provider.UserProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
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
class MapgakcoControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  MapgakcoProvider mapgakcoProvider;
  @Autowired
  UserProvider userProvider;
  @Autowired
  TestHelper testHelper;

  private User user;
  private Mapgakco mapgakco;

  @BeforeEach
  void setUp() throws Exception {
    user = userProvider.createUser();
    mapgakco = mapgakcoProvider.createMapgakco(user);
  }

  @AfterEach
  void tearDown() {
    testHelper.clean();
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("맵각코를 등록할 수 있다.")
  void createMapgakcoTest() throws Exception {
    // given
    MapgakcoCreateRequest request = MapgakcoCreateRequest.builder()
      .title("맵각코 제목")
      .applicantLimit(5)
      .content("맵각코 내용")
      .location("맵각코 위치")
      .latitude(33.450701)
      .longitude(126.570667)
      .meetingAt(LocalDateTime.now())
      .build();

    // when
    ResultActions actions = mockMvc.perform(
      post("/api/v1/mapgakcos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
        .header("Authorization", "JSON WEB TOKEN"));

    // then
    actions.andExpect(status().isOk())
      .andDo(print())
      .andDo(document("mapgakcos/mapgakco/createMapgakco",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        requestFields(
          fieldWithPath("title").type(STRING).description("맵각코 제목"),
          fieldWithPath("applicantLimit").type(NUMBER).description("맵각코 신청자 제한수"),
          fieldWithPath("content").type(STRING).description("맵각코 내용"),
          fieldWithPath("location").type(STRING).description("맵각코 위치"),
          fieldWithPath("latitude").type(NUMBER).description("맵각코 위도"),
          fieldWithPath("longitude").type(NUMBER).description("맵각코 경도"),
          fieldWithPath("meetingAt").type(STRING).description("맵각코 날짜")
        ),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
          fieldWithPath("serverDatetime").type(STRING).description("서버 시간"),
          fieldWithPath("data.status").type(STRING).description("맵각코 status")
        )
      ));
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("맵각코를 삭제할 수 있다.")
  void deleteMapgakcoTest() throws Exception {
    // given
    Long mapgakcoId = mapgakco.getId();

    // when
    ResultActions actions = mockMvc.perform(
      delete("/api/v1/mapgakcos/{mapgakcoId}", mapgakcoId)
        .contentType(MediaType.APPLICATION_JSON));

    // then
    actions.andExpect(status().isOk())
      .andDo(print())
      .andDo(document("mapgakcos/mapgakco/deleteMapgakco",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        pathParameters(
          parameterWithName("mapgakcoId").description(JsonFieldType.NUMBER).description("맵각코 ID")
        ),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
          fieldWithPath("serverDatetime").type(STRING).description("서버 시간"),
          fieldWithPath("data").type(STRING).description("응답 데이터")
        )
      ));
  }

}