package com.devnity.devnity.domain.mapgakco.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NULL;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devnity.devnity.web.mapgakco.dto.mapgakcocomment.request.MapgakcoCommentCreateRequest;
import com.devnity.devnity.web.mapgakco.dto.mapgakcocomment.request.MapgakcoCommentUpdateRequest;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoComment;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.setting.annotation.WithJwtAuthUser;
import com.devnity.devnity.setting.provider.MapgakcoProvider;
import com.devnity.devnity.setting.provider.TestHelper;
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
class MapgakcoCommentControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  MapgakcoProvider mapgakcoProvider;
  @Autowired
  TestHelper testHelper;
  @Autowired
  UserRepository userRepository;

  private User user;
  private Mapgakco mapgakco;
  private MapgakcoComment comment;

  @BeforeEach
  void setUp() throws Exception {
    user = userRepository.findUserByEmail("email@gmail.com").get();
    mapgakco = mapgakcoProvider.createMapgakco(user);
    comment = mapgakcoProvider.createComment(user, mapgakco, null);
  }

  @AfterEach
  void tearDown() {
    testHelper.clean();
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("????????? ????????? ????????? ??? ??????.")
  void addParentCommentTest() throws Exception {
    // given
    Long mapgakcoId = mapgakco.getId();
    MapgakcoCommentCreateRequest request = MapgakcoCommentCreateRequest.builder()
      .parentId(null)
      .content("????????? ?????? ??????")
      .build();

    // when
    ResultActions actions = mockMvc.perform(
      post("/api/v1/mapgakcos/{mapgakcoId}/comments", mapgakcoId)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "JSON WEB TOKEN")
        .content(objectMapper.writeValueAsString(request)));

    // then
    actions.andExpect(status().isOk())
      .andDo(print())
      .andDo(document("mapgakcos/comment/addParentComment",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        pathParameters(
          parameterWithName("mapgakcoId").description(JsonFieldType.NUMBER).description("????????? ID")
        ),
        requestFields(
          fieldWithPath("parentId").type(NULL).description("???????????? ?????? ????????? ??????ID??? NULL"),
          fieldWithPath("content").type(STRING).description("????????? ?????? ??????")
        ),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
          fieldWithPath("serverDatetime").type(STRING).description("?????? ??????"),
          fieldWithPath("data").type(STRING).description("?????? ?????????")
        )
      ));
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("????????? ???????????? ????????? ??? ??????.")
  void addChildCommentTest() throws Exception {
    // given
    Long mapgakcoId = mapgakco.getId();
    MapgakcoCommentCreateRequest request = MapgakcoCommentCreateRequest.builder()
      .parentId(comment.getId())
      .content("????????? ????????? ??????")
      .build();

    // when
    ResultActions actions = mockMvc.perform(
      post("/api/v1/mapgakcos/{mapgakcoId}/comments", mapgakcoId)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "JSON WEB TOKEN")
        .content(objectMapper.writeValueAsString(request)));

    // then
    actions.andExpect(status().isOk())
      .andDo(print())
      .andDo(document("mapgakcos/comment/addChildComment",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        pathParameters(
          parameterWithName("mapgakcoId").description(JsonFieldType.NUMBER).description("????????? ID")
        ),
        requestFields(
          fieldWithPath("parentId").type(NUMBER).description("????????? ?????? ?????? ID"),
          fieldWithPath("content").type(STRING).description("????????? ????????? ??????")
        ),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
          fieldWithPath("serverDatetime").type(STRING).description("?????? ??????"),
          fieldWithPath("data").type(STRING).description("?????? ?????????")
        )
      ));
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("????????? ????????? ????????? ??? ??????.")
  void updateCommentTest() throws Exception {
    // given
    Long mapgakcoId = mapgakco.getId();
    Long commentId = comment.getId();
    MapgakcoCommentUpdateRequest request = MapgakcoCommentUpdateRequest.builder()
      .content("????????? ?????? ?????? ??????")
      .build();

    // when
    ResultActions actions = mockMvc.perform(
      patch("/api/v1/mapgakcos/{mapgakcoId}/comments/{commentId}", mapgakcoId, commentId)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "JSON WEB TOKEN")
        .content(objectMapper.writeValueAsString(request)));

    // then
    actions.andExpect(status().isOk())
      .andDo(print())
      .andDo(document("mapgakcos/comment/updateComment",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        pathParameters(
          parameterWithName("mapgakcoId").description(JsonFieldType.NUMBER).description("????????? ID"),
          parameterWithName("commentId").description(JsonFieldType.NUMBER).description("????????? ?????? ID")
        ),
        requestFields(
          fieldWithPath("content").type(STRING).description("????????? ?????? ?????? ??????")
        ),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
          fieldWithPath("serverDatetime").type(STRING).description("?????? ??????"),
          fieldWithPath("data").type(STRING).description("?????? ?????????")
        )
      ));
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("????????? ????????? ????????? ??? ??????.")
  void deleteCommentTest() throws Exception {
    // given
    Long mapgakcoId = mapgakco.getId();
    Long commentId = comment.getId();

    // when
    ResultActions actions = mockMvc.perform(
      delete("/api/v1/mapgakcos/{mapgakcoId}/comments/{commentId}", mapgakcoId, commentId) // ???????????? ???
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "JSON WEB TOKEN"));

    // then
    actions.andExpect(status().isOk())
      .andDo(print())
      .andDo(document("mapgakcos/comment/deleteComment",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        pathParameters(
          parameterWithName("mapgakcoId").description(JsonFieldType.NUMBER).description("????????? ID"),
          parameterWithName("commentId").description(JsonFieldType.NUMBER).description("????????? ?????? ID")
        ),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
          fieldWithPath("serverDatetime").type(STRING).description("?????? ??????"),
          fieldWithPath("data").type(STRING).description("?????? ?????????")
        )
      ));
  }

}