package com.devnity.devnity.domain.introduction.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NULL;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.entity.IntroductionComment;
import com.devnity.devnity.domain.introduction.respository.IntroductionCommentRepository;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.domain.introduction.service.IntroductionService;
import com.devnity.devnity.domain.user.dto.request.SaveIntroductionRequest;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.Mbti;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.setting.annotation.WithJwtAuthUser;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class IntroductionControllerTest {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Autowired private MockMvc mockMvc;

  @Autowired private IntroductionService introductionService;

  @Autowired private IntroductionRepository introductionRepository;

  @Autowired private IntroductionCommentRepository introductionCommentRepository;

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
              .description("description")
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
          fieldWithPath("data[].introduction.description").type(NULL).description("자기소개 상세"),
          fieldWithPath("data[].introduction.likeCount").type(NUMBER).description("좋아요 수"),
          fieldWithPath("data[].introduction.commentCount").type(NUMBER).description("댓글 수"),
          fieldWithPath("data[].introduction.createdAt").type(STRING).description("생성 일자"),
          fieldWithPath("data[].introduction.updatedAt").type(STRING).description("수정 일자"),
          fieldWithPath("serverDatetime").type(STRING).description("서버 시간")
        )));

  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("데둥이 소개 페이징")
  public void testUserInfos() throws Exception {
    // given
    Course course =
        courseRepository.findByName("FE").orElseGet(() -> courseRepository.save(new Course("FE")));

    Generation generation =
        generationRepository
            .findBySequence(1)
            .orElseGet(() -> generationRepository.save(new Generation(1)));
    String name = "함승훈";
    List<User> users = new ArrayList<>();
    int lastId = 20;
    for (int i = 0; i < 30; i++) {
      users.add(
          User.builder()
              .course(course)
              .generation(generation)
              .password("password" + i)
              .name(name + i)
              .email(i + "email@naver.com")
              .role(UserRole.STUDENT)
              .build());
    }

    userRepository.saveAll(users);
    for (int i = 0; i < 30; i++) {
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
              .description("description")
              .build());
    }

    // when
    String url = String.format(
      "/api/v1/introductions?name=%s&course=%s&generation=%d&size=%d&lastId=%d&role=%s",
      name, course.getName(), generation.getSequence(), 5, lastId, UserRole.STUDENT);

    System.out.println("this is url = " + url);

    ResultActions actions =
        mockMvc.perform(
            get(url)
                .header("Authorization", "JSON WEB TOKEN"));

    // then
    actions
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(document("introductions/retrieve-all", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
          fieldWithPath("data").type(OBJECT).description("응답 데이터"),
          fieldWithPath("data.values").type(ARRAY).description("자기소개 리스트"),
          fieldWithPath("data.values[].user").type(OBJECT).description("사용자 정보"),
          fieldWithPath("data.values[].user.userId").type(NUMBER).description("사용자 ID"),
          fieldWithPath("data.values[].user.email").type(STRING).description("이메일"),
          fieldWithPath("data.values[].user.name").type(STRING).description("이름"),
          fieldWithPath("data.values[].user.course").type(STRING).description("코스"),
          fieldWithPath("data.values[].user.generation").type(NUMBER).description("기수"),
          fieldWithPath("data.values[].user.role").type(STRING).description("역할"),
          fieldWithPath("data.values[].user.createdAt").type(STRING).description("가입일자"),
          fieldWithPath("data.values[].introduction").type(OBJECT).description("자기소개"),
          fieldWithPath("data.values[].introduction.introductionId").type(NUMBER).description("자기소개 ID"),
          fieldWithPath("data.values[].introduction.profileImgUrl").type(STRING).description("프로필 URL"),
          fieldWithPath("data.values[].introduction.mbti").type(STRING).description("mbti"),
          fieldWithPath("data.values[].introduction.blogUrl").type(STRING).description("블로그 URL"),
          fieldWithPath("data.values[].introduction.githubUrl").type(STRING).description("깃허브 URL"),
          fieldWithPath("data.values[].introduction.summary").type(STRING).description("한 줄 소개"),
          fieldWithPath("data.values[].introduction.latitude").type(NUMBER).description("위도"),
          fieldWithPath("data.values[].introduction.longitude").type(NUMBER).description("경도"),
          fieldWithPath("data.values[].introduction.likeCount").type(NUMBER).description("좋아요 수"),
          fieldWithPath("data.values[].introduction.commentCount").type(NUMBER).description("댓글 수"),
          fieldWithPath("data.values[].introduction.createdAt").type(STRING).description("생성일자"),
          fieldWithPath("data.values[].introduction.updatedAt").type(STRING).description("수정일자"),
          fieldWithPath("data.nextLastId").type(NUMBER).description("마지막 자기소개 ID"),
          fieldWithPath("serverDatetime").type(STRING).description("서버 시간")
        )));

  } 

  @Transactional
  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("데둥이 소개 상세 페이지")
  public void testRetrieveUserDetailIntroduction() throws Exception {
    // given
    User user = userRepository.findUserByEmail("email@gmail.com").get();
    Introduction introduction = user.getIntroduction();

    introduction.update(
        Introduction.builder()
            .description("description")
            .profileImgUrl("profile")
            .githubUrl("github")
            .blogUrl("blog")
            .latitude(123.123)
            .longitude(123.123)
            .mbti(Mbti.ENFA)
            .summary("summary")
            .build());

    introductionRepository.save(introduction);

    IntroductionComment parent = IntroductionComment.of("parent", user, introduction);

    List<IntroductionComment> children = new ArrayList<>();

    for (int i = 0; i < 3; i++) {
      children.add(IntroductionComment.of("child", user, introduction, parent));
    }

    introductionCommentRepository.save(parent);
    introductionCommentRepository.saveAll(children);

    // when
    ResultActions actions =
        mockMvc.perform(
            get("/api/v1/introductions/{introductionId}", introduction.getId())
                .header("Authorization", "JSON WEB TOKEN"));

    // then
    actions
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "introductions/retrieve-detail",
          preprocessRequest(prettyPrint()),
          preprocessResponse(prettyPrint()),
          pathParameters(
            parameterWithName("introductionId").description("자기소개 ID")
          ),
          responseFields(
            fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
            fieldWithPath("data").type(OBJECT).description("응답 데이터"),
            fieldWithPath("data.user").type(OBJECT).description("사용자 정보"),
            fieldWithPath("data.user.userId").type(NUMBER).description("사용자 ID"),
            fieldWithPath("data.user.email").type(STRING).description("이메일"),
            fieldWithPath("data.user.name").type(STRING).description("이름"),
            fieldWithPath("data.user.course").type(STRING).description("코스"),
            fieldWithPath("data.user.generation").type(NUMBER).description("기수"),
            fieldWithPath("data.user.role").type(STRING).description("역할"),
            fieldWithPath("data.user.createdAt").type(STRING).description("가입일자"),
            fieldWithPath("data.introduction").type(OBJECT).description("자기소개"),
            fieldWithPath("data.introduction.introductionId").type(NUMBER).description("자기소개 아이디"),
            fieldWithPath("data.introduction.profileImgUrl").type(STRING).description("프로필 이미지 URL"),
            fieldWithPath("data.introduction.mbti").type(STRING).description("mbti"),
            fieldWithPath("data.introduction.blogUrl").type(STRING).description("블로그 URL"),
            fieldWithPath("data.introduction.githubUrl").type(STRING).description("깃허브 URL"),
            fieldWithPath("data.introduction.summary").type(STRING).description("한 줄 소개"),
            fieldWithPath("data.introduction.latitude").type(NUMBER).description("위도"),
            fieldWithPath("data.introduction.longitude").type(NUMBER).description("경도"),
            fieldWithPath("data.introduction.likeCount").type(NUMBER).description("좋아요 수"),
            fieldWithPath("data.introduction.commentCount").type(NUMBER).description("댓글 수"),
            fieldWithPath("data.introduction.description").type(STRING).description("상세 소개"),
            fieldWithPath("data.introduction.createdAt").type(STRING).description("생성일자"),
            fieldWithPath("data.introduction.updatedAt").type(STRING).description("수정일자"),
            fieldWithPath("data.comments").type(ARRAY).description("자기소개 댓글"),
            fieldWithPath("data.comments[].commentId").type(NUMBER).description("댓글 ID"),
            fieldWithPath("data.comments[].content").type(STRING).description("댓글 내용"),
            fieldWithPath("data.comments[].createdAt").type(STRING).description("생성일자"),
            fieldWithPath("data.comments[].updatedAt").type(STRING).description("수정일자"),
            fieldWithPath("data.comments[].status").type(STRING).description("댓글 상태"),
            fieldWithPath("data.comments[].writer").type(OBJECT).description("작성자"),
            fieldWithPath("data.comments[].writer.userId").type(NUMBER).description("사용자 ID"),
            fieldWithPath("data.comments[].writer.name").type(STRING).description("이름"),
            fieldWithPath("data.comments[].writer.course").type(STRING).description("코스"),
            fieldWithPath("data.comments[].writer.generation").type(NUMBER).description("기수"),
            fieldWithPath("data.comments[].writer.profileImgUrl").type(STRING).description("프로필 이미지 URL"),
            fieldWithPath("data.comments[].writer.role").type(STRING).description("역할"),
            fieldWithPath("data.comments[].children").type(ARRAY).description("하위 댓글"),
            fieldWithPath("data.comments[].children[].commentId").type(NUMBER).description("댓글 ID"),
            fieldWithPath("data.comments[].children[].content").type(STRING).description("댓글 내용"),
            fieldWithPath("data.comments[].children[].createdAt").type(STRING).description("생성일자"),
            fieldWithPath("data.comments[].children[].updatedAt").type(STRING).description("수정일자"),
            fieldWithPath("data.comments[].children[].status").type(STRING).description("댓글 상태"),
            fieldWithPath("data.comments[].children[].writer").type(OBJECT).description("작성자"),
            fieldWithPath("data.comments[].children[].writer.userId").type(NUMBER).description("사용자 ID"),
            fieldWithPath("data.comments[].children[].writer.name").type(STRING).description("이름"),
            fieldWithPath("data.comments[].children[].writer.course").type(STRING).description("코스"),
            fieldWithPath("data.comments[].children[].writer.generation").type(NUMBER).description("기수"),
            fieldWithPath("data.comments[].children[].writer.profileImgUrl").type(STRING).description("프로필 이미지 URL"),
            fieldWithPath("data.comments[].children[].writer.role").type(STRING).description("역할"),
            fieldWithPath("data.comments[].children[].children").type(NULL).description("하위 댓글"),
            fieldWithPath("data.liked").type(BOOLEAN).description("좋아요 여부"),
            fieldWithPath("serverDatetime").description(STRING).description("서버시간"))
        ));

  }
}




























