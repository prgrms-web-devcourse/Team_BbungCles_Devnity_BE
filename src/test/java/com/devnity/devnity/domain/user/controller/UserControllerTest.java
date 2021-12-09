package com.devnity.devnity.domain.user.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
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

import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.domain.user.dto.request.SaveIntroductionRequest;
import com.devnity.devnity.domain.user.dto.request.SignUpRequest;
import com.devnity.devnity.domain.user.entity.Mbti;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.test.annotation.WithJwtAuthUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private DataSource dataSource;

  @Autowired private UserRepository userRepository;

  @Autowired private IntroductionRepository introductionRepository;

  private User user;

  @BeforeAll
  void init() throws Exception {
    try (Connection conn = dataSource.getConnection()) {
      ScriptUtils.executeSqlScript(conn, new ClassPathResource("data.sql"));
    }

    User base = userRepository.findUserByEmail("user@gmail.com").get();

    user = User.builder()
            .email("email@gmail.com")
            .course(base.getCourse())
            .generation(base.getGeneration())
            .password("password")
            .name("seunghun")
            .role(UserRole.STUDENT)
            .group(base.getGroup())
            .build();

    userRepository.save(user);
  }

  @AfterAll
  void clean() throws Exception {
    introductionRepository.deleteAll();

    try (Connection conn = dataSource.getConnection()) {
      ScriptUtils.executeSqlScript(conn, new ClassPathResource("truncate-data.sql"));
    }
  }

  @WithAnonymousUser
  @DisplayName("회원가입 할 수 있다")
  @Test
  public void testSignUp() throws Exception {
    // given
    SignUpRequest request = SignUpRequest.builder()
        .generation(1)
        .role(UserRole.STUDENT)
        .name("seunghun")
        .password("password123")
        .email("email123123@gmail.com")
        .course("FE")
        .build();

    //when
    ResultActions actions = mockMvc.perform(
        post("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));
    //then
    actions.andExpect(status().isOk())
        .andDo(document("users/signUp", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("email").type(STRING).description("이메일"),
                fieldWithPath("name").type(STRING).description("이름"),
                fieldWithPath("password").type(STRING).description("비밀번호"),
                fieldWithPath("course").type(STRING).description("코스"),
                fieldWithPath("role").type(STRING).description("역할"),
                fieldWithPath("generation").type(NUMBER).description("기수")
              )));
  }

  @WithAnonymousUser
  @DisplayName("이메일 중복 확인 할 수 있다")
  @Test
  public void testCheckEmail() throws Exception {
    // given
    String email = user.getEmail();

    //when
    ResultActions actions = mockMvc.perform(
        get("/api/v1/users/check?email="+email));

    //then
    actions.andExpect(status().isOk())
        .andDo(print())
        .andDo(document("users/email-check", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
            responseFields(
                fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
                fieldWithPath("data").type(OBJECT).description("응답 데이터"),
                fieldWithPath("data.isDuplicated").type(BOOLEAN).description("중복 확인"),
                fieldWithPath("serverDatetime").type(STRING).description("서버 시간")
            )));
  }

  @WithJwtAuthUser(email = "email@gmail.com", roles = "USER")
  @DisplayName("자기소개가 저장된다")
  @Test
  public void testSaveIntroduction() throws Exception {
    // given

    SaveIntroductionRequest request =
        SaveIntroductionRequest.builder()
            .blogUrl("blog")
            .content("content")
            .githubUrl("github")
            .latitude(123.123)
            .longitude(445.455)
            .mbti(Mbti.ENFA)
            .profileImgUrl("profile")
            .summary("summary")
            .build();

    // when
    ResultActions actions =
        mockMvc.perform(
            put("/api/v1/users/me/introduction/{introductionId}", user.getIntroduction().getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

    // then
    actions
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document(
                "users/introduction/save",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("introductionId").description("자기소개 id")),
                requestFields(
                    fieldWithPath("profileImgUrl").type(STRING).description("프로필 이미지 URL"),
                    fieldWithPath("mbti").type(STRING).description("MBTI"),
                    fieldWithPath("blogUrl").type(STRING).description("블로그 URL"),
                    fieldWithPath("githubUrl").type(STRING).description("깃허브 URL"),
                    fieldWithPath("summary").type(STRING).description("한 줄 소개"),
                    fieldWithPath("latitude").type(NUMBER).description("위도"),
                    fieldWithPath("longitude").type(NUMBER).description("경도"),
                    fieldWithPath("content").type(STRING).description("자기소개 본문")),
                responseFields(
                    fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
                    fieldWithPath("data").type(STRING).description("응답 데이터"),
                    fieldWithPath("serverDatetime").type(STRING).description("서버 시간"))));
  }
}
