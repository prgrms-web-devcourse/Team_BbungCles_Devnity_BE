package com.devnity.devnity.domain.introduction.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NULL;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devnity.devnity.web.introduction.dto.request.SaveIntroductionCommentRequest;
import com.devnity.devnity.web.introduction.dto.request.UpdateIntroductionCommentRequest;
import com.devnity.devnity.domain.introduction.entity.IntroductionComment;
import com.devnity.devnity.domain.introduction.respository.IntroductionCommentRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.setting.annotation.WithJwtAuthUser;
import com.devnity.devnity.setting.provider.TestHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class IntroductionCommentControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private UserRepository userRepository;

  @Autowired private IntroductionCommentRepository introductionCommentRepository;

  @Autowired private TestHelper helper;

  @AfterEach
  void clean() {
    helper.clean();
  }

  @WithJwtAuthUser(email = "user@gmail.com",role = UserRole.STUDENT)
  @DisplayName("???????????? ?????? ??????")
  @Test 
  public void testSaveComment() throws Exception {
    // given
    User user = userRepository.findUserByEmail("user@gmail.com").get();

    Long introductionId = user.getIntroduction().getId();

    SaveIntroductionCommentRequest request = SaveIntroductionCommentRequest.builder()
      .parentId(null).content("this is comment").build();

    // when
    ResultActions actions = mockMvc.perform(
      post("/api/v1/introductions/{introductionId}/comments", introductionId)
        .header("Authorization", "JSON WEB TOKEN")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)));

    // then
    actions
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document(
                "introductions/comment/create-comment",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("introductionId").description("???????????? ID")),
                requestFields(
                    fieldWithPath("parentId").type(NULL).description("?????? ?????? ID"),
                    fieldWithPath("content").type(STRING).description("?????? ??????")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
                    fieldWithPath("data").type(OBJECT).description("?????? ?????????"),
                    fieldWithPath("data.commentId").description(NUMBER).description("?????? ID"),
                    fieldWithPath("data.parentId").description(NUMBER).description("?????? ?????? ID"),
                    fieldWithPath("serverDatetime").description(STRING).description("????????????"))
                ));

  }
  
  @WithJwtAuthUser(email = "user@gmail.com",role = UserRole.STUDENT)
  @DisplayName("???????????? ????????? ??????")
  @Test 
  public void testSaveSubComment() throws Exception {
    // given
    User user = userRepository.findUserByEmail("user@gmail.com").get();

    Long introductionId = user.getIntroduction().getId();

    IntroductionComment parent =
        introductionCommentRepository.save(
            IntroductionComment.of("this is parent comment", user, user.getIntroduction()));

    SaveIntroductionCommentRequest request = SaveIntroductionCommentRequest.builder()
      .parentId(parent.getId()).content("this is comment").build();

    // when
    ResultActions actions = mockMvc.perform(
      post("/api/v1/introductions/{introductionId}/comments", introductionId)
        .header("Authorization", "JSON WEB TOKEN")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)));

    // then
    actions
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document(
                "introductions/comment/create-comment",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("introductionId").description("???????????? ID")),
                requestFields(
                    fieldWithPath("parentId").type(NUMBER).description("?????? ?????? ID"),
                    fieldWithPath("content").type(STRING).description("?????? ??????")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
                    fieldWithPath("data").type(OBJECT).description("?????? ?????????"),
                    fieldWithPath("data.commentId").description(NUMBER).description("?????? ID"),
                    fieldWithPath("data.parentId").description(NUMBER).description("?????? ?????? ID"),
                    fieldWithPath("serverDatetime").description(STRING).description("????????????"))
                ));

  }

  @WithJwtAuthUser(email = "user@gmail.com",role = UserRole.STUDENT)
  @DisplayName("???????????? ?????? ??????")
  @Test
  public void testUpdateComment() throws Exception {
    // given
    User user = userRepository.findUserByEmail("user@gmail.com").get();

    Long introductionId = user.getIntroduction().getId();

    IntroductionComment comment = introductionCommentRepository.save(
      IntroductionComment.of("content", user, user.getIntroduction()));

    UpdateIntroductionCommentRequest request = new UpdateIntroductionCommentRequest(
      "update content");

    // when
    ResultActions actions =
        mockMvc.perform(
            patch(
                    "/api/v1/introductions/{introductionId}/comments/{commentId}",
                    introductionId,
                    comment.getId())
                .header("Authorization", "JSON WEB TOKEN")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

    // then
    actions
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "introductions/comment/update-comment",
          preprocessRequest(prettyPrint()),
          preprocessResponse(prettyPrint()),
          pathParameters(
            parameterWithName("introductionId").description("???????????? ID"),
            parameterWithName("commentId").description("?????? ID")
            ),
          requestFields(
            fieldWithPath("content").type(STRING).description("????????? ?????? ??????")
          ),
          responseFields(
            fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
            fieldWithPath("data").type(STRING).description("?????? ?????????"),
            fieldWithPath("serverDatetime").description(STRING).description("????????????"))
        ));
  }

  @WithJwtAuthUser(email = "user@gmail.com",role = UserRole.STUDENT)
  @DisplayName("???????????? ?????? ??????")
  @Test
  public void testDeleteComment() throws Exception {
    // given
    User user = userRepository.findUserByEmail("user@gmail.com").get();

    Long introductionId = user.getIntroduction().getId();

    IntroductionComment comment = introductionCommentRepository.save(
      IntroductionComment.of("content", user, user.getIntroduction()));

    // when
    ResultActions actions =
      mockMvc.perform(
        delete(
          "/api/v1/introductions/{introductionId}/comments/{commentId}",
          introductionId,
          comment.getId())
          .header("Authorization", "JSON WEB TOKEN"));

    // then
    actions
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "introductions/comment/delete-comment",
          preprocessRequest(prettyPrint()),
          preprocessResponse(prettyPrint()),
          pathParameters(
            parameterWithName("introductionId").description("???????????? ID"),
            parameterWithName("commentId").description("?????? ID")
          ),
          responseFields(
            fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
            fieldWithPath("data").type(OBJECT).description("?????? ?????????"),
            fieldWithPath("data.content").type(STRING).description("?????? ??????"),
            fieldWithPath("serverDatetime").description(STRING).description("????????????"))
        ));
  }
}