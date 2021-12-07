package com.devnity.devnity.domain.auth.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devnity.devnity.domain.auth.dto.request.LoginRequest;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class AuthControllerTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired private DataSource dataSource;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

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

  @DisplayName("로그인 할 수 있다")
  @Test
  public void testLogin() throws Exception {
    // given
    User user = userRepository.findUserByEmail("user@gmail.com").get();
    LoginRequest request = new LoginRequest(user.getEmail(), "user123");

    // when
    ResultActions actions = mockMvc.perform(
        post("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));

    // then
    actions.andExpect(status().isOk())
        .andDo(print())
        .andDo(document("auth/login", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("email").type(STRING).description("이메일"),
                fieldWithPath("password").type(STRING).description("비밀번호")
            ),
            responseFields(
                fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
                fieldWithPath("data").type(OBJECT).description("응답 데이터"),
                fieldWithPath("data.token").type(STRING).description("JWT"),
                fieldWithPath("data.groupName").type(STRING).description("권한 그룹 이름"),
                fieldWithPath("serverDatetime").type(STRING).description("서버 시간")
            )));
  }
}