package com.devnity.devnity.domain.user.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
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

import com.devnity.devnity.domain.user.dto.request.SignUpRequest;
import com.devnity.devnity.domain.user.entity.UserRole;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private DataSource dataSource;

  @BeforeAll
  void init() throws Exception {
    try (Connection conn = dataSource.getConnection()) {
      ScriptUtils.executeSqlScript(conn, new ClassPathResource("data.sql"));
    }
  }

  @AfterAll
  void clean() throws Exception {
    try (Connection conn = dataSource.getConnection()) {
      ScriptUtils.executeSqlScript(conn, new ClassPathResource("truncate-data.sql"));
    }
  }

  @DisplayName("회원가입 할 수 있다")
  @Test
  public void testSignUp() throws Exception {
    // given
    SignUpRequest request = SignUpRequest.builder()
        .generation(1)
        .role(UserRole.STUDENT)
        .name("seunghun")
        .password("password123")
        .email("email@gmail.com")
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

  @DisplayName("이메일 중복 확인 할 수 있다")
  @Test
  public void testCheckEmail() throws Exception {
    // given
    String email = "user@gmail.com";


    //when
    ResultActions actions = mockMvc.perform(
        get("/api/v1/users/{email}/check", email));

    //then
    actions.andExpect(status().isOk())
        .andDo(print())
        .andDo(document("users/email-check", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("email").description("이메일")
            ),
            responseFields(
                fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
                fieldWithPath("data").type(OBJECT).description("응답 데이터"),
                fieldWithPath("data.isDuplicated").type(BOOLEAN).description("중복 확인"),
                fieldWithPath("serverDatetime").type(STRING).description("서버 시간")
            )));
  }
}