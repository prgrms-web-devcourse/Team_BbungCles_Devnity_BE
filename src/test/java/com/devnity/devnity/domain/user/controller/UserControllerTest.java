package com.devnity.devnity.domain.user.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devnity.devnity.domain.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherApplicant;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.repository.GatherRepository;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoApplicantRepository;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoRepository;
import com.devnity.devnity.domain.user.dto.request.SaveIntroductionRequest;
import com.devnity.devnity.domain.user.dto.request.SignUpRequest;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.Mbti;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.setting.annotation.WithJwtAuthUser;
import com.devnity.devnity.setting.provider.GatherProvider;
import com.devnity.devnity.setting.provider.MapgakcoProvider;
import com.devnity.devnity.setting.provider.UserProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private UserRepository userRepository;

  @Autowired private IntroductionRepository introductionRepository;

  @Autowired private GenerationRepository generationRepository;

  @Autowired private CourseRepository courseRepository;

  @Autowired private GatherRepository gatherRepository;

  @Autowired private MapgakcoRepository mapgakcoRepository;

  @Autowired private MapgakcoApplicantRepository mapgakcoApplicantRepository;

  @Autowired private UserProvider userProvider;

  @AfterEach
  void clear() throws Exception {
    introductionRepository.deleteAll();
    userRepository.deleteAll();
    courseRepository.deleteAll();
    generationRepository.deleteAll();
  }

  @WithAnonymousUser
  @DisplayName("회원가입 할 수 있다")
  @Test
  public void testSignUp() throws Exception {
    // given

    SignUpRequest request = SignUpRequest.builder()
        .generation(1)
        .role(UserRole.STUDENT)
        .name("함승훈")
        .password("pAssword123!@#")
        .email("email123123@gmail.com")
        .course("FE")
        .build();

    courseRepository.save(new Course(request.getCourse()));
    generationRepository.save(new Generation(request.getGeneration()));

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
    String email = "hello@gmail.com";

    // when
    ResultActions actions =
        mockMvc.perform(
            post("/api/v1/users/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(Collections.singletonMap("email", email))));

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

  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("자기소개가 저장된다")
  @Test
  public void testSaveIntroduction() throws Exception {
    // given
    User user = userRepository.findUserByEmail("email@gmail.com").get();

    SaveIntroductionRequest request =
        SaveIntroductionRequest.builder()
            .blogUrl("blog")
            .description("description")
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
                    fieldWithPath("description").type(STRING).description("상세 소개"),
                    fieldWithPath("latitude").type(NUMBER).description("위도"),
                    fieldWithPath("longitude").type(NUMBER).description("경도")),
                responseFields(
                    fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
                    fieldWithPath("data").type(STRING).description("응답 데이터"),
                    fieldWithPath("serverDatetime").type(STRING).description("서버 시간"))));
  }

  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("내 정보를 조회할 수 있다")
  @Test 
  public void testMe() throws Exception {
    // given
    User user = userRepository.findUserByEmail("email@gmail.com").get();
    Introduction introduction = introductionRepository.findIntroductionByIdAndUserId(
        user.getIntroduction().getId(), user.getId()).get();

    introduction.update(
        Introduction.builder()
            .githubUrl("github")
            .blogUrl("blog")
            .latitude(123.123)
            .longitude(456.456)
            .profileImgUrl("profile")
            .mbti(Mbti.ENFA)
            .summary("summary")
            .description("content")
            .build());

    introductionRepository.save(introduction);

    // when
    ResultActions actions = mockMvc.perform(get("/api/v1/users/me"));

    // then
    actions
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document(
                "users/me",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
                    fieldWithPath("data").type(OBJECT).description("응답 데이터"),
                    fieldWithPath("data.user").type(OBJECT).description("내 정보"),
                    fieldWithPath("data.user.userId").type(NUMBER).description("사용자 ID"),
                    fieldWithPath("data.user.email").type(STRING).description("이메일"),
                    fieldWithPath("data.user.name").type(STRING).description("이름"),
                    fieldWithPath("data.user.course").type(STRING).description("코스"),
                    fieldWithPath("data.user.generation").type(NUMBER).description("기수"),
                    fieldWithPath("data.user.role").type(STRING).description("역할"),
                    fieldWithPath("data.user.createdAt").type(STRING).description("가입일"),
                    fieldWithPath("data.introduction").type(OBJECT).description("자기소개"),
                    fieldWithPath("data.introduction.introductionId").type(NUMBER).description("자기소개 ID"),
                    fieldWithPath("data.introduction.profileImgUrl").type(STRING).description("프로필 이미지"),
                    fieldWithPath("data.introduction.mbti").type(STRING).description("MBTI"),
                    fieldWithPath("data.introduction.blogUrl").type(STRING).description("블로그"),
                    fieldWithPath("data.introduction.githubUrl").type(STRING).description("깃허브"),
                    fieldWithPath("data.introduction.summary").type(STRING).description("한 줄 소개"),
                    fieldWithPath("data.introduction.description").type(STRING).description("상세 소개"),
                    fieldWithPath("data.introduction.latitude").type(NUMBER).description("위도"),
                    fieldWithPath("data.introduction.longitude").type(NUMBER).description("경도"),
                    fieldWithPath("data.introduction.likeCount").type(NULL).description("경도"),
                    fieldWithPath("data.introduction.commentCount").type(NULL).description("경도"),
                    fieldWithPath("data.introduction.createdAt").type(STRING).description("생성일"),
                    fieldWithPath("data.introduction.updatedAt").type(STRING).description("수정일"),
                    fieldWithPath("serverDatetime").type(STRING).description("서버시간"))));

  }

  @Transactional
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("내가 모집한 모임을 확인할 수 있다")
  @Test 
  public void testRetrieveGathersHostedByMe() throws Exception {
    // given
    User user = userRepository.findUserByEmail("email@gmail.com").get();
    user.getIntroduction().update(Introduction.builder().profileImgUrl("profile").build());
    int size = 1;
    List<Gather> gathers = new ArrayList<>();
    List<Mapgakco> mapgakcos = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      CreateGatherRequest request =
        CreateGatherRequest.builder()
          .applicantLimit(10)
          .category(GatherCategory.STUDY)
          .content("content")
          .title("title")
          .deadline(LocalDateTime.now().plusDays(10))
          .build();
      Gather gather = Gather.of(user, request);
      gathers.add(gather);
      Mapgakco mapgakco = Mapgakco.builder()
        .applicantLimit(10)
        .deadline(LocalDateTime.now().plusDays(10L))
        .user(user)
        .title("title")
        .content("content")
        .location("location")
        .latitude(123.123)
        .longitude(123.123)
        .meetingAt(LocalDateTime.now().plusDays(5L))
        .build();
      mapgakcos.add(mapgakco);
    }

    gatherRepository.saveAll(gathers);
    mapgakcoRepository.saveAll(mapgakcos);

    // when
    ResultActions actions =
        mockMvc.perform(get("/api/v1/users/me/host")
          .header("Authorization", "JSON WEB TOKEN"));

    //then 
    actions
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "users/me/host",
          preprocessRequest(prettyPrint()),
          preprocessResponse(prettyPrint()),
          responseFields(
            fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
            fieldWithPath("data").type(OBJECT).description("응답 데이터"),
            fieldWithPath("data.gathers").type(ARRAY).description("모집 리스트"),
            fieldWithPath("data.gathers[].gatherId").type(NUMBER).description("모집 ID"),
            fieldWithPath("data.gathers[].status").type(STRING).description("모집 상태"),
            fieldWithPath("data.gathers[].title").type(STRING).description("제목"),
            fieldWithPath("data.gathers[].category").type(STRING).description("카테고리"),
            fieldWithPath("data.gathers[].deadline").type(STRING).description("마감일자"),
            fieldWithPath("data.gathers[].createdAt").type(STRING).description("생성일자"),
            fieldWithPath("data.gathers[].applicantLimit").type(NUMBER).description("지원 제한 인원"),
            fieldWithPath("data.gathers[].view").type(NUMBER).description("조회수"),
            fieldWithPath("data.gathers[].applicantCount").type(NUMBER).description("지원자 수"),
            fieldWithPath("data.gathers[].commentCount").type(NUMBER).description("댓글 수"),
            fieldWithPath("data.gathers[].author").type(OBJECT).description("작성자"),
            fieldWithPath("data.gathers[].author.userId").type(NUMBER).description("작성자 ID"),
            fieldWithPath("data.gathers[].author.name").type(STRING).description("이름"),
            fieldWithPath("data.gathers[].author.course").type(STRING).description("코스"),
            fieldWithPath("data.gathers[].author.generation").type(NUMBER).description("기수"),
            fieldWithPath("data.gathers[].author.profileImgUrl").type(STRING).description("프로필 이미지 URL"),
            fieldWithPath("data.gathers[].author.role").type(STRING).description("역할"),
            fieldWithPath("data.mapgakcos").type(ARRAY).description("맵각코 리스트"),
            fieldWithPath("data.mapgakcos[].mapgakcoId").type(NUMBER).description("맵각코 ID"),
            fieldWithPath("data.mapgakcos[].status").type(OBJECT).description("맵각코 상태"),
            fieldWithPath("data.mapgakcos[].status.status").type(STRING).description("맵각코 상태"),
            fieldWithPath("data.mapgakcos[].title").type(STRING).description("제목"),
            fieldWithPath("data.mapgakcos[].location").type(STRING).description("위치"),
            fieldWithPath("data.mapgakcos[].deadline").type(STRING).description("마감일자"),
            fieldWithPath("data.mapgakcos[].meetingAt").type(STRING).description("모임일자"),
            fieldWithPath("data.mapgakcos[].applicantLimit").type(NUMBER).description("지원 제한 인원"),
            fieldWithPath("data.mapgakcos[].applicantCount").type(NUMBER).description("지원자 수"),
            fieldWithPath("data.mapgakcos[].createdAt").type(STRING).description("생성일자"),
            fieldWithPath("data.mapgakcos[].author").type(OBJECT).description("작성자"),
            fieldWithPath("data.mapgakcos[].author.userId").type(NUMBER).description("작성자 ID"),
            fieldWithPath("data.mapgakcos[].author.name").type(STRING).description("이름"),
            fieldWithPath("data.mapgakcos[].author.course").type(STRING).description("코스"),
            fieldWithPath("data.mapgakcos[].author.generation").type(NUMBER).description("기수"),
            fieldWithPath("data.mapgakcos[].author.profileImgUrl").type(STRING).description("프로필 이미지 URL"),
            fieldWithPath("data.mapgakcos[].author.role").type(STRING).description("역할"),
            fieldWithPath("serverDatetime").type(STRING).description("서버시간"))));

  }

  @Transactional
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("내가 신청한 모임을 확인할 수 있다")
  @Test
  public void testRetrieveGathersAppliedByMe() throws Exception {
    // given

    User applicant = userRepository.findUserByEmail("email@gmail.com").get();
    User user = userProvider.createUser("applicant@gmail.com");
    user.getIntroduction().update(Introduction.builder()
        .profileImgUrl("profile")
      .build());
    applicant.getIntroduction().update(Introduction.builder().profileImgUrl("profile").build());
    int size = 1;
    List<Gather> gathers = new ArrayList<>();
    List<Mapgakco> mapgakcos = new ArrayList<>();
    List<MapgakcoApplicant> mapgakcoApplicants = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      CreateGatherRequest request =
        CreateGatherRequest.builder()
          .applicantLimit(10)
          .category(GatherCategory.STUDY)
          .content("content")
          .title("title")
          .deadline(LocalDateTime.now().plusDays(10))
          .build();
      Gather gather = Gather.of(user, request);
      gathers.add(gather);

      GatherApplicant ga = GatherApplicant.of(applicant, gather);
      gather.addApplicant(ga);

      Mapgakco mapgakco = Mapgakco.builder()
        .applicantLimit(10)
        .deadline(LocalDateTime.now().plusDays(10L))
        .user(user)
        .title("title")
        .content("content")
        .location("location")
        .latitude(123.123)
        .longitude(123.123)
        .meetingAt(LocalDateTime.now().plusDays(5L))
        .build();
      mapgakcos.add(mapgakco);

      MapgakcoApplicant ma = MapgakcoApplicant.builder()
        .user(applicant)
        .mapgakco(mapgakco)
        .build();
      mapgakcoApplicants.add(ma);
    }

    gatherRepository.saveAll(gathers);
    mapgakcoRepository.saveAll(mapgakcos);
    mapgakcoApplicantRepository.saveAll(mapgakcoApplicants);

    // when
    ResultActions actions =
        mockMvc.perform(get("/api/v1/users/me/applicant")
          .header("Authorization", "JSON WEB TOKEN"));

    //then
    actions
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "users/me/applicant",
          preprocessRequest(prettyPrint()),
          preprocessResponse(prettyPrint()),
          responseFields(
            fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
            fieldWithPath("data").type(OBJECT).description("응답 데이터"),
            fieldWithPath("data.gathers").type(ARRAY).description("모집 리스트"),
            fieldWithPath("data.gathers[].gatherId").type(NUMBER).description("모집 ID"),
            fieldWithPath("data.gathers[].status").type(STRING).description("모집 상태"),
            fieldWithPath("data.gathers[].title").type(STRING).description("제목"),
            fieldWithPath("data.gathers[].category").type(STRING).description("카테고리"),
            fieldWithPath("data.gathers[].deadline").type(STRING).description("마감일자"),
            fieldWithPath("data.gathers[].createdAt").type(STRING).description("생성일자"),
            fieldWithPath("data.gathers[].applicantLimit").type(NUMBER).description("지원 제한 인원"),
            fieldWithPath("data.gathers[].view").type(NUMBER).description("조회수"),
            fieldWithPath("data.gathers[].applicantCount").type(NUMBER).description("지원자 수"),
            fieldWithPath("data.gathers[].commentCount").type(NUMBER).description("댓글 수"),
            fieldWithPath("data.gathers[].author").type(OBJECT).description("작성자"),
            fieldWithPath("data.gathers[].author.userId").type(NUMBER).description("작성자 ID"),
            fieldWithPath("data.gathers[].author.name").type(STRING).description("이름"),
            fieldWithPath("data.gathers[].author.course").type(STRING).description("코스"),
            fieldWithPath("data.gathers[].author.generation").type(NUMBER).description("기수"),
            fieldWithPath("data.gathers[].author.profileImgUrl").type(STRING).description("프로필 이미지 URL"),
            fieldWithPath("data.gathers[].author.role").type(STRING).description("역할"),
            fieldWithPath("data.mapgakcos").type(ARRAY).description("맵각코 리스트"),
            fieldWithPath("data.mapgakcos[].mapgakcoId").type(NUMBER).description("맵각코 ID"),
            fieldWithPath("data.mapgakcos[].status").type(OBJECT).description("맵각코 상태"),
            fieldWithPath("data.mapgakcos[].status.status").type(STRING).description("맵각코 상태"),
            fieldWithPath("data.mapgakcos[].title").type(STRING).description("제목"),
            fieldWithPath("data.mapgakcos[].location").type(STRING).description("위치"),
            fieldWithPath("data.mapgakcos[].deadline").type(STRING).description("마감일자"),
            fieldWithPath("data.mapgakcos[].meetingAt").type(STRING).description("모임일자"),
            fieldWithPath("data.mapgakcos[].applicantLimit").type(NUMBER).description("지원 제한 인원"),
            fieldWithPath("data.mapgakcos[].applicantCount").type(NUMBER).description("지원자 수"),
            fieldWithPath("data.mapgakcos[].createdAt").type(STRING).description("생성일자"),
            fieldWithPath("data.mapgakcos[].author").type(OBJECT).description("작성자"),
            fieldWithPath("data.mapgakcos[].author.userId").type(NUMBER).description("작성자 ID"),
            fieldWithPath("data.mapgakcos[].author.name").type(STRING).description("이름"),
            fieldWithPath("data.mapgakcos[].author.course").type(STRING).description("코스"),
            fieldWithPath("data.mapgakcos[].author.generation").type(NUMBER).description("기수"),
            fieldWithPath("data.mapgakcos[].author.profileImgUrl").type(STRING).description("프로필 이미지 URL"),
            fieldWithPath("data.mapgakcos[].author.role").type(STRING).description("역할"),
            fieldWithPath("serverDatetime").type(STRING).description("서버시간"))));

  }
  
  
}




















