package com.devnity.devnity.domain.auth.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devnity.devnity.domain.admin.entity.Invitation;
import com.devnity.devnity.domain.admin.repository.InvitationRepository;
import com.devnity.devnity.domain.auth.dto.request.LoginRequest;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
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
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class AuthControllerTest {

  @Autowired
  private InvitationRepository invitationRepository;

  @Autowired
  private IntroductionRepository introductionRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private GenerationRepository generationRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;
  private Generation generation;
  private Course course;
  private User user;

  @BeforeAll
  void init() throws Exception {
    generation = new Generation(1);
    course = new Course("FE");
    user =
      User.builder()
        .email("user@gmail.com")
        .role(UserRole.STUDENT)
        .name("함승훈")
        .password(passwordEncoder.encode("Password123!@#"))
        .course(course)
        .generation(generation)
        .build();
  }

  @AfterAll
  void clean() {
    introductionRepository.deleteAll();
    userRepository.deleteAll();
    generationRepository.deleteAll();
    courseRepository.deleteAll();
  }

  @DisplayName("로그인 할 수 있다")
  @Test
  public void testLogin() throws Exception {
    // given

    generationRepository.save(generation);
    courseRepository.save(course);
    userRepository.save(user);

    LoginRequest request = new LoginRequest(user.getEmail(), "Password123!@#");

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
          fieldWithPath("data.authority").type(STRING).description("사용자 권한"),
          fieldWithPath("serverDatetime").type(STRING).description("서버 시간")
        )));
  }

  //  @WithJwtAuthUser(email = "me_admin@mail.com", role = UserRole.MANAGER)
  @Test
  @DisplayName("초대링크 단일 조회 테스트")
  void testGetLink() throws Exception {
    // Given
    Invitation invitation = Invitation.builder()
      .course("BE")
      .generation(1)
      .role(UserRole.STUDENT)
      .deadline(LocalDate.now())
      .build();
    invitationRepository.save(invitation);

    // When + Then
    mockMvc.perform(
        get("/api/v1/auth/links/{uuid}", invitation.getUuid())
          .header("Authorization", "JSON WEB TOKEN")
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "auth/links", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          pathParameters(
            parameterWithName("uuid").description("링크 UUID")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버응답시간"),

            fieldWithPath("data.course").type(JsonFieldType.STRING).description("코스"),
            fieldWithPath("data.generation").type(JsonFieldType.NUMBER).description("기수"),
            fieldWithPath("data.role").type(JsonFieldType.STRING).description("역할")
          )
        )
      );
  }
}