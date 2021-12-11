package com.devnity.devnity.domain.introduction.controller;

import static java.lang.constant.ConstantDescs.NULL;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.domain.introduction.service.IntroductionService;
import com.devnity.devnity.domain.user.dto.request.SaveIntroductionRequest;
import com.devnity.devnity.domain.user.entity.Authority;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.Mbti;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.test.annotation.WithJwtAuthUser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class IntroductionControllerTest {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Autowired private MockMvc mockMvc;

  @Autowired private IntroductionService introductionService;

  @Autowired private IntroductionRepository introductionRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private CourseRepository courseRepository;

  @Autowired private GenerationRepository generationRepository;

  @AfterEach
  void clear() {
    introductionRepository.deleteAll();
    userRepository.deleteAll();
    courseRepository.deleteAll();
    generationRepository.deleteAll();
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("메인페이지 자기소개 추천")
  public void testSuggest() throws Exception {
    // given

    Course course =
        courseRepository.findByName("FE").orElseGet(() -> courseRepository.save(new Course("FE")));

    Generation generation =
        generationRepository
            .findBySequence(1)
            .orElseGet(() -> generationRepository.save(new Generation(1)));



    List<User> users = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      users.add(
          User.builder()
              .course(course)
              .generation(generation)
              .password("password" + i)
              .name("name" + i)
              .email(i + "email@naver.com")
              .role(UserRole.STUDENT)
              .build());
    }


    userRepository.saveAll(users);
    for (int i = 0; i < 5; i++) {
      User user = userRepository.findUserByEmail(users.get(i).getEmail()).get();

      introductionService.save(
          user.getId(),
          user.getIntroduction().getId(),
          SaveIntroductionRequest.builder()
              .summary("summary")
              .profileImgUrl("profile")
              .mbti(Mbti.ENFA)
              .longitude(123.123)
              .latitude(123.123)
              .githubUrl("github")
              .blogUrl("blog")
              .content("content")
              .build());
    }

    // when
    ResultActions actions =
        mockMvc.perform(
            get("/api/v1/introductions/suggest").header("Authorization", "JSON WEB TOKEN"));

    // then
    actions
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(document("introductions/suggest", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
          fieldWithPath("data").type(ARRAY).description("응답 데이터"),
          fieldWithPath("data[].user").type(OBJECT).description("사용자 정보"),
          fieldWithPath("data[].user.userId").type(NUMBER).description("사용자 ID"),
          fieldWithPath("data[].user.email").type(STRING).description("이메일"),
          fieldWithPath("data[].user.name").type(STRING).description("이름"),
          fieldWithPath("data[].user.course").type(STRING).description("코스"),
          fieldWithPath("data[].user.generation").type(NUMBER).description("기수"),
          fieldWithPath("data[].user.role").type(STRING).description("역할"),
          fieldWithPath("data[].user.createdAt").type(STRING).description("가입일자"),
          fieldWithPath("data[].introduction").type(OBJECT).description("자기소개 정보"),
          fieldWithPath("data[].introduction.introductionId").type(NUMBER).description("자기소개 ID"),
          fieldWithPath("data[].introduction.profileImgUrl").type(STRING).description("프로필 이미지 URL"),
          fieldWithPath("data[].introduction.mbti").type(STRING).description("mbti"),
          fieldWithPath("data[].introduction.blogUrl").type(STRING).description("블로그 URL"),
          fieldWithPath("data[].introduction.githubUrl").type(STRING).description("깃허브 URL"),
          fieldWithPath("data[].introduction.summary").type(STRING).description("한 줄 소개"),
          fieldWithPath("data[].introduction.latitude").type(NUMBER).description("경도"),
          fieldWithPath("data[].introduction.longitude").type(NUMBER).description("위도"),
          fieldWithPath("data[].introduction.createdAt").type(STRING).description("생성 일자"),
          fieldWithPath("data[].introduction.updatedAt").type(STRING).description("수정 일자"),
          fieldWithPath("serverDatetime").type(STRING).description("서버 시간")
        )));

  }
}
















