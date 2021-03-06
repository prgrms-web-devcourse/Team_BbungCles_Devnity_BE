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
import com.devnity.devnity.web.introduction.service.IntroductionService;
import com.devnity.devnity.web.user.dto.request.SaveIntroductionRequest;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.Mbti;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.setting.annotation.WithJwtAuthUser;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
  @DisplayName("??????????????? ???????????? ??????")
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
              .mbti(Mbti.ENFJ)
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
          fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
          fieldWithPath("data").type(ARRAY).description("?????? ?????????"),
          fieldWithPath("data[].user").type(OBJECT).description("????????? ??????"),
          fieldWithPath("data[].user.userId").type(NUMBER).description("????????? ID"),
          fieldWithPath("data[].user.email").type(STRING).description("?????????"),
          fieldWithPath("data[].user.name").type(STRING).description("??????"),
          fieldWithPath("data[].user.course").type(STRING).description("??????"),
          fieldWithPath("data[].user.generation").type(NUMBER).description("??????"),
          fieldWithPath("data[].user.role").type(STRING).description("??????"),
          fieldWithPath("data[].user.createdAt").type(STRING).description("????????????"),
          fieldWithPath("data[].introduction").type(OBJECT).description("???????????? ??????"),
          fieldWithPath("data[].introduction.introductionId").type(NUMBER).description("???????????? ID"),
          fieldWithPath("data[].introduction.profileImgUrl").type(STRING).description("????????? ????????? URL"),
          fieldWithPath("data[].introduction.mbti").type(STRING).description("mbti"),
          fieldWithPath("data[].introduction.blogUrl").type(STRING).description("????????? URL"),
          fieldWithPath("data[].introduction.githubUrl").type(STRING).description("????????? URL"),
          fieldWithPath("data[].introduction.summary").type(STRING).description("??? ??? ??????"),
          fieldWithPath("data[].introduction.latitude").type(NUMBER).description("??????"),
          fieldWithPath("data[].introduction.longitude").type(NUMBER).description("??????"),
          fieldWithPath("data[].introduction.description").type(NULL).description("???????????? ??????"),
          fieldWithPath("data[].introduction.likeCount").type(NUMBER).description("????????? ???"),
          fieldWithPath("data[].introduction.commentCount").type(NUMBER).description("?????? ???"),
          fieldWithPath("data[].introduction.createdAt").type(STRING).description("?????? ??????"),
          fieldWithPath("data[].introduction.updatedAt").type(STRING).description("?????? ??????"),
          fieldWithPath("serverDatetime").type(STRING).description("?????? ??????")
        )));

  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("????????? ?????? ?????????")
  public void testUserInfos() throws Exception {
    // given
    Course course =
        courseRepository.findByName("FE").orElseGet(() -> courseRepository.save(new Course("FE")));

    Generation generation =
        generationRepository
            .findBySequence(1)
            .orElseGet(() -> generationRepository.save(new Generation(1)));
    String name = "?????????";
    List<User> users = new ArrayList<>();
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

    Long lastId = users.get(users.size() - 1).getId();

    for (int i = 0; i < 30; i++) {
      User user = userRepository.findUserByEmail(users.get(i).getEmail()).get();

      introductionService.save(
          user.getId(),
          user.getIntroduction().getId(),
          SaveIntroductionRequest.builder()
              .summary("summary")
              .profileImgUrl("profile")
              .mbti(Mbti.ENFJ)
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
          fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
          fieldWithPath("data").type(OBJECT).description("?????? ?????????"),
          fieldWithPath("data.values").type(ARRAY).description("???????????? ?????????"),
          fieldWithPath("data.values[].user").type(OBJECT).description("????????? ??????"),
          fieldWithPath("data.values[].user.userId").type(NUMBER).description("????????? ID"),
          fieldWithPath("data.values[].user.email").type(STRING).description("?????????"),
          fieldWithPath("data.values[].user.name").type(STRING).description("??????"),
          fieldWithPath("data.values[].user.course").type(STRING).description("??????"),
          fieldWithPath("data.values[].user.generation").type(NUMBER).description("??????"),
          fieldWithPath("data.values[].user.role").type(STRING).description("??????"),
          fieldWithPath("data.values[].user.createdAt").type(STRING).description("????????????"),
          fieldWithPath("data.values[].introduction").type(OBJECT).description("????????????"),
          fieldWithPath("data.values[].introduction.introductionId").type(NUMBER).description("???????????? ID"),
          fieldWithPath("data.values[].introduction.profileImgUrl").type(STRING).description("????????? URL"),
          fieldWithPath("data.values[].introduction.mbti").type(STRING).description("mbti"),
          fieldWithPath("data.values[].introduction.blogUrl").type(STRING).description("????????? URL"),
          fieldWithPath("data.values[].introduction.githubUrl").type(STRING).description("????????? URL"),
          fieldWithPath("data.values[].introduction.summary").type(STRING).description("??? ??? ??????"),
          fieldWithPath("data.values[].introduction.description").type(NULL).description("??? ??? ??????"),
          fieldWithPath("data.values[].introduction.latitude").type(NUMBER).description("??????"),
          fieldWithPath("data.values[].introduction.longitude").type(NUMBER).description("??????"),
          fieldWithPath("data.values[].introduction.likeCount").type(NUMBER).description("????????? ???"),
          fieldWithPath("data.values[].introduction.commentCount").type(NUMBER).description("?????? ???"),
          fieldWithPath("data.values[].introduction.createdAt").type(STRING).description("????????????"),
          fieldWithPath("data.values[].introduction.updatedAt").type(STRING).description("????????????"),
          fieldWithPath("data.nextLastId").type(NUMBER).description("????????? ???????????? ID"),
          fieldWithPath("serverDatetime").type(STRING).description("?????? ??????")
        )));

  } 

  @Transactional
  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("????????? ?????? ?????? ?????????")
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
            .mbti(Mbti.ENFJ)
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
            parameterWithName("introductionId").description("???????????? ID")
          ),
          responseFields(
            fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
            fieldWithPath("data").type(OBJECT).description("?????? ?????????"),
            fieldWithPath("data.user").type(OBJECT).description("????????? ??????"),
            fieldWithPath("data.user.userId").type(NUMBER).description("????????? ID"),
            fieldWithPath("data.user.email").type(STRING).description("?????????"),
            fieldWithPath("data.user.name").type(STRING).description("??????"),
            fieldWithPath("data.user.course").type(STRING).description("??????"),
            fieldWithPath("data.user.generation").type(NUMBER).description("??????"),
            fieldWithPath("data.user.role").type(STRING).description("??????"),
            fieldWithPath("data.user.createdAt").type(STRING).description("????????????"),
            fieldWithPath("data.introduction").type(OBJECT).description("????????????"),
            fieldWithPath("data.introduction.introductionId").type(NUMBER).description("???????????? ?????????"),
            fieldWithPath("data.introduction.profileImgUrl").type(STRING).description("????????? ????????? URL"),
            fieldWithPath("data.introduction.mbti").type(STRING).description("mbti"),
            fieldWithPath("data.introduction.blogUrl").type(STRING).description("????????? URL"),
            fieldWithPath("data.introduction.githubUrl").type(STRING).description("????????? URL"),
            fieldWithPath("data.introduction.summary").type(STRING).description("??? ??? ??????"),
            fieldWithPath("data.introduction.latitude").type(NUMBER).description("??????"),
            fieldWithPath("data.introduction.longitude").type(NUMBER).description("??????"),
            fieldWithPath("data.introduction.likeCount").type(NUMBER).description("????????? ???"),
            fieldWithPath("data.introduction.commentCount").type(NUMBER).description("?????? ???"),
            fieldWithPath("data.introduction.description").type(STRING).description("?????? ??????"),
            fieldWithPath("data.introduction.createdAt").type(STRING).description("????????????"),
            fieldWithPath("data.introduction.updatedAt").type(STRING).description("????????????"),
            fieldWithPath("data.comments").type(ARRAY).description("???????????? ??????"),
            fieldWithPath("data.comments[].commentId").type(NUMBER).description("?????? ID"),
            fieldWithPath("data.comments[].content").type(STRING).description("?????? ??????"),
            fieldWithPath("data.comments[].createdAt").type(STRING).description("????????????"),
            fieldWithPath("data.comments[].updatedAt").type(STRING).description("????????????"),
            fieldWithPath("data.comments[].status").type(STRING).description("?????? ??????"),
            fieldWithPath("data.comments[].writer").type(OBJECT).description("?????????"),
            fieldWithPath("data.comments[].writer.userId").type(NUMBER).description("????????? ID"),
            fieldWithPath("data.comments[].writer.name").type(STRING).description("??????"),
            fieldWithPath("data.comments[].writer.course").type(STRING).description("??????"),
            fieldWithPath("data.comments[].writer.generation").type(NUMBER).description("??????"),
            fieldWithPath("data.comments[].writer.profileImgUrl").type(STRING).description("????????? ????????? URL"),
            fieldWithPath("data.comments[].writer.role").type(STRING).description("??????"),
            fieldWithPath("data.comments[].children").type(ARRAY).description("?????? ??????"),
            fieldWithPath("data.comments[].children[].commentId").type(NUMBER).description("?????? ID"),
            fieldWithPath("data.comments[].children[].content").type(STRING).description("?????? ??????"),
            fieldWithPath("data.comments[].children[].createdAt").type(STRING).description("????????????"),
            fieldWithPath("data.comments[].children[].updatedAt").type(STRING).description("????????????"),
            fieldWithPath("data.comments[].children[].status").type(STRING).description("?????? ??????"),
            fieldWithPath("data.comments[].children[].writer").type(OBJECT).description("?????????"),
            fieldWithPath("data.comments[].children[].writer.userId").type(NUMBER).description("????????? ID"),
            fieldWithPath("data.comments[].children[].writer.name").type(STRING).description("??????"),
            fieldWithPath("data.comments[].children[].writer.course").type(STRING).description("??????"),
            fieldWithPath("data.comments[].children[].writer.generation").type(NUMBER).description("??????"),
            fieldWithPath("data.comments[].children[].writer.profileImgUrl").type(STRING).description("????????? ????????? URL"),
            fieldWithPath("data.comments[].children[].writer.role").type(STRING).description("??????"),
            fieldWithPath("data.comments[].children[].children").type(NULL).description("?????? ??????"),
            fieldWithPath("data.liked").type(BOOLEAN).description("????????? ??????"),
            fieldWithPath("serverDatetime").description(STRING).description("????????????"))
        ));

  }
}




























