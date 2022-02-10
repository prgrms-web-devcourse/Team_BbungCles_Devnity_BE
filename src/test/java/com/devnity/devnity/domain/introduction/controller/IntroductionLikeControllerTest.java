package com.devnity.devnity.domain.introduction.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devnity.devnity.domain.introduction.entity.IntroductionLike;
import com.devnity.devnity.domain.introduction.respository.IntroductionLikeRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.setting.annotation.WithJwtAuthUser;
import com.devnity.devnity.setting.provider.TestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class IntroductionLikeControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private UserRepository userRepository;

  @Autowired private IntroductionLikeRepository introductionLikeRepository;

  @Autowired private TestHelper testHelper;
  @AfterEach
  void clean() {
    testHelper.clean();
  }

  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @Test 
  public void testLike() throws Exception {
    // given
    User user = userRepository.findUserByEmail("email@gmail.com").get();

    Long userId = user.getId();
    Long introductionId = user.getIntroduction().getId();

    // when
    ResultActions actions = mockMvc.perform(
      post("/api/v1/introductions/{introductionId}/like", introductionId)
        .header("Authorization", "JSON WEB TOKEN"));

    // then
    actions
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document(
                "introductions/like/create-like",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("introductionId").description("자기소개 ID")),
                responseFields(
                    fieldWithPath("statusCode").type(NUMBER).description("상태코드"),
                    fieldWithPath("data").type(OBJECT).description("응답 데이터"),
                    fieldWithPath("data.isLiked").type(BOOLEAN).description("좋아요 상태"),
                    fieldWithPath("serverDatetime").description(STRING).description("서버시간"))));
  }

  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @Test
  public void testRemoveLike() throws Exception {
    // given
    User user = userRepository.findUserByEmail("email@gmail.com").get();

    Long userId = user.getId();
    Long introductionId = user.getIntroduction().getId();

    introductionLikeRepository.save(new IntroductionLike(userId, introductionId));

    // when
    ResultActions actions = mockMvc.perform(
      delete("/api/v1/introductions/{introductionId}/like", introductionId)
        .header("Authorization", "JSON WEB TOKEN"));

    // then
    actions
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document(
                "introductions/like/remove-like",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("introductionId").description("자기소개 ID")),
                responseFields(
                    fieldWithPath("statusCode").type(NUMBER).description("상태코드"),
                    fieldWithPath("data").type(OBJECT).description("응답 데이터"),
                    fieldWithPath("data.isLiked").type(BOOLEAN).description("좋아요 상태"),
                    fieldWithPath("serverDatetime").description(STRING).description("서버시간"))));
  }
  
  
}