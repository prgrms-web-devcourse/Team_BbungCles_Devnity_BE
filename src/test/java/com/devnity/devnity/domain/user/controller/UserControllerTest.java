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

import com.devnity.devnity.web.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherApplicant;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.repository.GatherRepository;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import com.devnity.devnity.domain.mapgakco.repository.mapgakco.MapgakcoRepository;
import com.devnity.devnity.domain.mapgakco.repository.mapgakcoapplicant.MapgakcoApplicantRepository;
import com.devnity.devnity.web.mapgakco.service.MapService;
import com.devnity.devnity.web.user.dto.request.SaveIntroductionRequest;
import com.devnity.devnity.web.user.dto.request.SignUpRequest;
import com.devnity.devnity.web.user.dto.request.UserMapPageRequest;
import com.devnity.devnity.web.user.dto.request.UserMapRequest;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.Mbti;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.setting.annotation.WithJwtAuthUser;
import com.devnity.devnity.setting.provider.TestHelper;
import com.devnity.devnity.setting.provider.UserProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
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

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private GatherRepository gatherRepository;

  @Autowired
  private MapgakcoRepository mapgakcoRepository;

  @Autowired
  private MapgakcoApplicantRepository mapgakcoApplicantRepository;

  @Autowired
  private UserProvider userProvider;

  @Autowired
  private TestHelper testHelper;

  @Autowired
  private MapService mapService;

  @AfterEach
  void clear() throws Exception {
    testHelper.clean();
  }

  @WithAnonymousUser
  @DisplayName("???????????? ??? ??? ??????")
  @Test
  public void testSignUp() throws Exception {
    // given

    SignUpRequest request = SignUpRequest.builder()
        .generation(1)
        .role(UserRole.STUDENT)
        .name("?????????")
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
                fieldWithPath("email").type(STRING).description("?????????"),
                fieldWithPath("name").type(STRING).description("??????"),
                fieldWithPath("password").type(STRING).description("????????????"),
                fieldWithPath("course").type(STRING).description("??????"),
                fieldWithPath("role").type(STRING).description("??????"),
                fieldWithPath("generation").type(NUMBER).description("??????")
              )));
  }

  @WithAnonymousUser
  @DisplayName("????????? ?????? ?????? ??? ??? ??????")
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
                fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
                fieldWithPath("data").type(OBJECT).description("?????? ?????????"),
                fieldWithPath("data.isDuplicated").type(BOOLEAN).description("?????? ??????"),
                fieldWithPath("serverDatetime").type(STRING).description("?????? ??????")
            )));
  }

  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("??????????????? ????????????")
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
            .mbti(Mbti.ENFJ)
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
                pathParameters(parameterWithName("introductionId").description("???????????? id")),
                requestFields(
                    fieldWithPath("profileImgUrl").type(STRING).description("????????? ????????? URL"),
                    fieldWithPath("mbti").type(STRING).description("MBTI"),
                    fieldWithPath("blogUrl").type(STRING).description("????????? URL"),
                    fieldWithPath("githubUrl").type(STRING).description("????????? URL"),
                    fieldWithPath("summary").type(STRING).description("??? ??? ??????"),
                    fieldWithPath("description").type(STRING).description("?????? ??????"),
                    fieldWithPath("latitude").type(NUMBER).description("??????"),
                    fieldWithPath("longitude").type(NUMBER).description("??????")),
                responseFields(
                    fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
                    fieldWithPath("data").type(STRING).description("?????? ?????????"),
                    fieldWithPath("serverDatetime").type(STRING).description("?????? ??????"))));
  }

  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("??? ????????? ????????? ??? ??????")
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
            .mbti(Mbti.ENFJ)
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
                    fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
                    fieldWithPath("data").type(OBJECT).description("?????? ?????????"),
                    fieldWithPath("data.user").type(OBJECT).description("??? ??????"),
                    fieldWithPath("data.user.userId").type(NUMBER).description("????????? ID"),
                    fieldWithPath("data.user.email").type(STRING).description("?????????"),
                    fieldWithPath("data.user.name").type(STRING).description("??????"),
                    fieldWithPath("data.user.course").type(STRING).description("??????"),
                    fieldWithPath("data.user.generation").type(NUMBER).description("??????"),
                    fieldWithPath("data.user.role").type(STRING).description("??????"),
                    fieldWithPath("data.user.createdAt").type(STRING).description("?????????"),
                    fieldWithPath("data.introduction").type(OBJECT).description("????????????"),
                    fieldWithPath("data.introduction.introductionId").type(NUMBER).description("???????????? ID"),
                    fieldWithPath("data.introduction.profileImgUrl").type(STRING).description("????????? ?????????"),
                    fieldWithPath("data.introduction.mbti").type(STRING).description("MBTI"),
                    fieldWithPath("data.introduction.blogUrl").type(STRING).description("?????????"),
                    fieldWithPath("data.introduction.githubUrl").type(STRING).description("?????????"),
                    fieldWithPath("data.introduction.summary").type(STRING).description("??? ??? ??????"),
                    fieldWithPath("data.introduction.description").type(STRING).description("?????? ??????"),
                    fieldWithPath("data.introduction.latitude").type(NUMBER).description("??????"),
                    fieldWithPath("data.introduction.longitude").type(NUMBER).description("??????"),
                    fieldWithPath("data.introduction.likeCount").type(NULL).description("??????"),
                    fieldWithPath("data.introduction.commentCount").type(NULL).description("??????"),
                    fieldWithPath("data.introduction.createdAt").type(STRING).description("?????????"),
                    fieldWithPath("data.introduction.updatedAt").type(STRING).description("?????????"),
                    fieldWithPath("serverDatetime").type(STRING).description("????????????"))));

  }

  @Transactional
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("?????? ????????? ????????? ????????? ??? ??????")
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
          .deadline(LocalDate.now().plusDays(10))
          .build();
      Gather gather = Gather.of(user, request);
      gathers.add(gather);
      Mapgakco mapgakco = Mapgakco.builder()
        .applicantLimit(10)
        .user(user)
        .title("title")
        .content("content")
        .location("location")
        .latitude(33.450701)
        .longitude(126.570667)
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
            fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
            fieldWithPath("data").type(OBJECT).description("?????? ?????????"),
            fieldWithPath("data.gathers").type(ARRAY).description("?????? ?????????"),
            fieldWithPath("data.gathers[].gatherId").type(NUMBER).description("?????? ID"),
            fieldWithPath("data.gathers[].status").type(STRING).description("?????? ??????"),
            fieldWithPath("data.gathers[].title").type(STRING).description("??????"),
            fieldWithPath("data.gathers[].category").type(STRING).description("????????????"),
            fieldWithPath("data.gathers[].deadline").type(STRING).description("????????????"),
            fieldWithPath("data.gathers[].createdAt").type(STRING).description("????????????"),
            fieldWithPath("data.gathers[].applicantLimit").type(NUMBER).description("?????? ?????? ??????"),
            fieldWithPath("data.gathers[].view").type(NUMBER).description("?????????"),
            fieldWithPath("data.gathers[].applicantCount").type(NUMBER).description("????????? ???"),
            fieldWithPath("data.gathers[].commentCount").type(NUMBER).description("?????? ???"),
            fieldWithPath("data.gathers[].author").type(OBJECT).description("?????????"),
            fieldWithPath("data.gathers[].author.userId").type(NUMBER).description("????????? ID"),
            fieldWithPath("data.gathers[].author.name").type(STRING).description("??????"),
            fieldWithPath("data.gathers[].author.course").type(STRING).description("??????"),
            fieldWithPath("data.gathers[].author.generation").type(NUMBER).description("??????"),
            fieldWithPath("data.gathers[].author.profileImgUrl").type(STRING).description("????????? ????????? URL"),
            fieldWithPath("data.gathers[].author.role").type(STRING).description("??????"),
            fieldWithPath("data.mapgakcos").type(ARRAY).description("????????? ?????????"),
            fieldWithPath("data.mapgakcos[].mapgakcoId").type(NUMBER).description("????????? ID"),
            fieldWithPath("data.mapgakcos[].status").type(STRING).description("????????? ??????"),
            fieldWithPath("data.mapgakcos[].title").type(STRING).description("??????"),
            fieldWithPath("data.mapgakcos[].location").type(STRING).description("??????"),
            fieldWithPath("data.mapgakcos[].meetingAt").type(STRING).description("????????????"),
            fieldWithPath("data.mapgakcos[].latitude").type(NUMBER).description("????????? ??????"),
            fieldWithPath("data.mapgakcos[].longitude").type(NUMBER).description("????????? ??????"),
            fieldWithPath("data.mapgakcos[].applicantLimit").type(NUMBER).description("?????? ?????? ??????"),
            fieldWithPath("data.mapgakcos[].applicantCount").type(NUMBER).description("????????? ???"),
            fieldWithPath("data.mapgakcos[].createdAt").type(STRING).description("????????????"),
            fieldWithPath("data.mapgakcos[].author").type(OBJECT).description("?????????"),
            fieldWithPath("data.mapgakcos[].author.userId").type(NUMBER).description("????????? ID"),
            fieldWithPath("data.mapgakcos[].author.name").type(STRING).description("??????"),
            fieldWithPath("data.mapgakcos[].author.course").type(STRING).description("??????"),
            fieldWithPath("data.mapgakcos[].author.generation").type(NUMBER).description("??????"),
            fieldWithPath("data.mapgakcos[].author.profileImgUrl").type(STRING).description("????????? ????????? URL"),
            fieldWithPath("data.mapgakcos[].author.role").type(STRING).description("??????"),
            fieldWithPath("serverDatetime").type(STRING).description("????????????"))));

  }

  @Transactional
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("?????? ????????? ????????? ????????? ??? ??????")
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
          .deadline(LocalDate.now().plusDays(10))
          .build();
      Gather gather = Gather.of(user, request);
      gathers.add(gather);

      GatherApplicant ga = GatherApplicant.of(applicant, gather);
      gather.addApplicant(ga);

      Mapgakco mapgakco = Mapgakco.builder()
        .applicantLimit(10)
        .user(user)
        .title("title")
        .content("content")
        .location("location")
        .latitude(33.450701)
        .longitude(126.570667)
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
            fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
            fieldWithPath("data").type(OBJECT).description("?????? ?????????"),
            fieldWithPath("data.gathers").type(ARRAY).description("?????? ?????????"),
            fieldWithPath("data.gathers[].gatherId").type(NUMBER).description("?????? ID"),
            fieldWithPath("data.gathers[].status").type(STRING).description("?????? ??????"),
            fieldWithPath("data.gathers[].title").type(STRING).description("??????"),
            fieldWithPath("data.gathers[].category").type(STRING).description("????????????"),
            fieldWithPath("data.gathers[].deadline").type(STRING).description("????????????"),
            fieldWithPath("data.gathers[].createdAt").type(STRING).description("????????????"),
            fieldWithPath("data.gathers[].applicantLimit").type(NUMBER).description("?????? ?????? ??????"),
            fieldWithPath("data.gathers[].view").type(NUMBER).description("?????????"),
            fieldWithPath("data.gathers[].applicantCount").type(NUMBER).description("????????? ???"),
            fieldWithPath("data.gathers[].commentCount").type(NUMBER).description("?????? ???"),
            fieldWithPath("data.gathers[].author").type(OBJECT).description("?????????"),
            fieldWithPath("data.gathers[].author.userId").type(NUMBER).description("????????? ID"),
            fieldWithPath("data.gathers[].author.name").type(STRING).description("??????"),
            fieldWithPath("data.gathers[].author.course").type(STRING).description("??????"),
            fieldWithPath("data.gathers[].author.generation").type(NUMBER).description("??????"),
            fieldWithPath("data.gathers[].author.profileImgUrl").type(STRING).description("????????? ????????? URL"),
            fieldWithPath("data.gathers[].author.role").type(STRING).description("??????"),
            fieldWithPath("data.mapgakcos").type(ARRAY).description("????????? ?????????"),
            fieldWithPath("data.mapgakcos[].mapgakcoId").type(NUMBER).description("????????? ID"),
            fieldWithPath("data.mapgakcos[].status").type(STRING).description("????????? ??????"),
            fieldWithPath("data.mapgakcos[].title").type(STRING).description("??????"),
            fieldWithPath("data.mapgakcos[].location").type(STRING).description("??????"),
            fieldWithPath("data.mapgakcos[].meetingAt").type(STRING).description("????????????"),
            fieldWithPath("data.mapgakcos[].latitude").type(NUMBER).description("????????? ??????"),
            fieldWithPath("data.mapgakcos[].longitude").type(NUMBER).description("????????? ??????"),
            fieldWithPath("data.mapgakcos[].applicantLimit").type(NUMBER).description("?????? ?????? ??????"),
            fieldWithPath("data.mapgakcos[].applicantCount").type(NUMBER).description("????????? ???"),
            fieldWithPath("data.mapgakcos[].createdAt").type(STRING).description("????????????"),
            fieldWithPath("data.mapgakcos[].author").type(OBJECT).description("?????????"),
            fieldWithPath("data.mapgakcos[].author.userId").type(NUMBER).description("????????? ID"),
            fieldWithPath("data.mapgakcos[].author.name").type(STRING).description("??????"),
            fieldWithPath("data.mapgakcos[].author.course").type(STRING).description("??????"),
            fieldWithPath("data.mapgakcos[].author.generation").type(NUMBER).description("??????"),
            fieldWithPath("data.mapgakcos[].author.profileImgUrl").type(STRING).description("????????? ????????? URL"),
            fieldWithPath("data.mapgakcos[].author.role").type(STRING).description("??????"),
            fieldWithPath("serverDatetime").type(STRING).description("????????????"))));

  }

  @Test
  @Transactional
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("?????? ????????? ?????? ?????? ???????????? ???????????? ????????? ??? ??? ??????.")
  void getFirstUserMapTest() throws Exception {
    // given
    // ?????? -> nex: 37.57736394041695, ney: 127.03009029300624, swx: 37.55659510685803, swy: 126.9430729297755
    userProvider.createUser("FE", 1, 37.566653033875774, 126.97876549797886, "email1@naver.com"); // ??????????????????
    userProvider.createUser("FE", 1, 37.56503610058175, 126.96217261676021, "email2@naver.com"); // ????????? ?????????
    userProvider.createUser("BE", 2, 37.571484004598325, 126.9919403294101, "email3@naver.com"); // ??????3??????

    // ?????? -> nex: 37.5870833561458, ney: 127.0598034558777, swx: 37.54192700434515, swy: 126.89175793437813
    userProvider.createUser("FE", 1, 37.549143911582256, 126.91349757858292, "email4@naver.com"); // ?????????
    userProvider.createUser("BE", 1, 37.58041326711556, 127.04578258300938, "email5@naver.com"); // ????????????
    userProvider.createUser("BE", 2, 37.58009056466645, 126.9228016641275, "email6@naver.com"); // ????????? ?????????

    UserMapPageRequest request = UserMapPageRequest.builder()
      .lastDistance(0.0)
      .centerY(37.566653033875774)
      .centerX(126.97876549797886)
      .currentNEY(37.57736394041695)
      .currentNEX(127.03009029300624)
      .currentSWY(37.55659510685803)
      .currentSWX(126.9430729297755)
      .build();

    // when UserRole.STUDENT
    ResultActions actions = mockMvc.perform(
      get("/api/v1/users/locations")
        .contentType(MediaType.APPLICATION_JSON)
        .param("course", "FE")
        .param("generation", String.valueOf(1))
        .param("role", String.valueOf(UserRole.STUDENT))
        .param("name", (String) null)
        .param("lastDistance", String.valueOf(request.getLastDistance()))
        .param("centerX", String.valueOf(request.getCenterX()))
        .param("centerY", String.valueOf(request.getCenterY()))
        .param("currentNEX", String.valueOf(request.getCurrentNEX()))
        .param("currentNEY", String.valueOf(request.getCurrentNEY()))
        .param("currentSWX", String.valueOf(request.getCurrentSWX()))
        .param("currentSWY", String.valueOf(request.getCurrentSWY()))
    );

    // then
    actions.andExpect(status().isOk())
      .andDo(print())
      .andDo(document("users/locations/firstPage",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
          fieldWithPath("serverDatetime").type(STRING).description("?????? ??????"),
          fieldWithPath("data.lastDistance").type(NUMBER).description("????????? ????????????"),
          fieldWithPath("data.hasNext").type(BOOLEAN).description("?????? ????????? ????????? ????????? ??????"),
          fieldWithPath("data.users").type(ARRAY).description("?????? ?????????"),
          fieldWithPath("data.users.[].userId").type(NUMBER).description("?????? ID"),
          fieldWithPath("data.users.[].name").type(STRING).description("?????? ??????"),
          fieldWithPath("data.users.[].course").type(STRING).description("?????? ??????"),
          fieldWithPath("data.users.[].generation").type(NUMBER).description("?????? ??????"),
          fieldWithPath("data.users.[].profileImgUrl").type(STRING).description("?????? ??????Url"),
          fieldWithPath("data.users.[].latitude").type(NUMBER).description("?????? ??????"),
          fieldWithPath("data.users.[].longitude").type(NUMBER).description("?????? ??????")
        )
      ));
  }

  @Test
  @Transactional
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("?????????????????? ?????????????????? ??? ??????.")
  void getNextUserMapTest() throws Exception {
    // given
    // ?????? -> nex: 37.57736394041695, ney: 127.03009029300624, swx: 37.55659510685803, swy: 126.9430729297755
    userProvider.createUser("FE", 1, 37.566653033875774, 126.97876549797886, "email1@naver.com"); // ??????????????????
    userProvider.createUser("FE", 1, 37.56503610058175, 126.96217261676021, "email2@naver.com"); // ????????? ?????????
    userProvider.createUser("BE", 2, 37.571484004598325, 126.9919403294101, "email3@naver.com"); // ??????3??????

    // ?????? -> nex: 37.5870833561458, ney: 127.0598034558777, swx: 37.54192700434515, swy: 126.89175793437813
    userProvider.createUser("FE", 1, 37.549143911582256, 126.91349757858292, "email4@naver.com"); // ?????????
    userProvider.createUser("BE", 1, 37.58041326711556, 127.04578258300938, "email5@naver.com"); // ????????????
    userProvider.createUser("BE", 2, 37.58009056466645, 126.9228016641275, "email6@naver.com"); // ????????? ?????????

    UserMapPageRequest request = UserMapPageRequest.builder()
      .lastDistance(mapService.maxDistanceByTwoPoint(
        37.566653033875774,
        126.97876549797886,
        37.57736394041695,
        127.03009029300624,
        37.55659510685803,
        126.9430729297755,
        "meter"
      ))
      .centerY(37.566653033875774)
      .centerX(126.97876549797886)
      .currentNEY(37.5870833561458)
      .currentNEX(127.0598034558777)
      .currentSWY(37.54192700434515)
      .currentSWX(126.89175793437813)
      .build();

    // when
    ResultActions actions = mockMvc.perform(
      get("/api/v1/users/locations")
        .contentType(MediaType.APPLICATION_JSON)
        .param("course", (String) null)
        .param("generation", (String) null)
        .param("role", String.valueOf(UserRole.STUDENT))
        .param("name", (String) null)
        .param("lastDistance", String.valueOf(request.getLastDistance()))
        .param("centerX", String.valueOf(request.getCenterX()))
        .param("centerY", String.valueOf(request.getCenterY()))
        .param("currentNEX", String.valueOf(request.getCurrentNEX()))
        .param("currentNEY", String.valueOf(request.getCurrentNEY()))
        .param("currentSWX", String.valueOf(request.getCurrentSWX()))
        .param("currentSWY", String.valueOf(request.getCurrentSWY()))
    );

    // then
    actions.andExpect(status().isOk())
      .andDo(print())
      .andDo(document("users/locations/nextPage",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
          fieldWithPath("serverDatetime").type(STRING).description("?????? ??????"),
          fieldWithPath("data.lastDistance").type(NUMBER).description("????????? ????????????"),
          fieldWithPath("data.hasNext").type(BOOLEAN).description("?????? ????????? ????????? ????????? ??????"),
          fieldWithPath("data.users").type(ARRAY).description("?????? ?????????"),
          fieldWithPath("data.users.[].userId").type(NUMBER).description("?????? ID"),
          fieldWithPath("data.users.[].name").type(STRING).description("?????? ??????"),
          fieldWithPath("data.users.[].course").type(STRING).description("?????? ??????"),
          fieldWithPath("data.users.[].generation").type(NUMBER).description("?????? ??????"),
          fieldWithPath("data.users.[].profileImgUrl").type(STRING).description("?????? ??????Url"),
          fieldWithPath("data.users.[].latitude").type(NUMBER).description("?????? ??????"),
          fieldWithPath("data.users.[].longitude").type(NUMBER).description("?????? ??????")
        )
      ));
  }

  @Test
  @Transactional
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("NE, SW ???????????? ???????????? ????????? ??? ??????.")
  void getUserMapWithinRangeTest() throws Exception {
    // given
    // ?????? -> nex: 37.57736394041695, ney: 127.03009029300624, swx: 37.55659510685803, swy: 126.9430729297755
    userProvider.createUser("FE", 1, 37.566653033875774, 126.97876549797886, "email1@naver.com"); // ??????????????????
    userProvider.createUser("FE", 1, 37.56503610058175, 126.96217261676021, "email2@naver.com"); // ????????? ?????????
    userProvider.createUser("BE", 2, 37.571484004598325, 126.9919403294101, "email3@naver.com"); // ??????3??????

    // ?????? -> nex: 37.5870833561458, ney: 127.0598034558777, swx: 37.54192700434515, swy: 126.89175793437813
    userProvider.createUser("FE", 1, 37.549143911582256, 126.91349757858292, "email4@naver.com"); // ?????????
    userProvider.createUser("BE", 1, 37.58041326711556, 127.04578258300938, "email5@naver.com"); // ????????????
    userProvider.createUser("BE", 2, 37.58009056466645, 126.9228016641275, "email6@naver.com"); // ????????? ?????????

    UserMapRequest request = UserMapRequest.builder()
      .currentNEY(37.57736394041695)
      .currentNEX(127.03009029300624)
      .currentSWY(37.55659510685803)
      .currentSWX(126.9430729297755)
      .build();

    // when
    ResultActions actions = mockMvc.perform(
      get("/api/v1/users/locations/range")
        .contentType(MediaType.APPLICATION_JSON)
        .param("course", "BE")
        .param("generation", (String) null)
        .param("role", String.valueOf(UserRole.STUDENT))
        .param("name", (String) null)
        .param("currentNEX", String.valueOf(request.getCurrentNEX()))
        .param("currentNEY", String.valueOf(request.getCurrentNEY()))
        .param("currentSWX", String.valueOf(request.getCurrentSWX()))
        .param("currentSWY", String.valueOf(request.getCurrentSWY()))
    );

    // then
    actions.andExpect(status().isOk())
      .andDo(print())
      .andDo(document("users/locations/withinRange",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
          fieldWithPath("serverDatetime").type(STRING).description("?????? ??????"),
          fieldWithPath("data.[].userId").type(NUMBER).description("?????? ID"),
          fieldWithPath("data.[].name").type(STRING).description("?????? ??????"),
          fieldWithPath("data.[].course").type(STRING).description("?????? ??????"),
          fieldWithPath("data.[].generation").type(NUMBER).description("?????? ??????"),
          fieldWithPath("data.[].profileImgUrl").type(STRING).description("?????? ??????Url"),
          fieldWithPath("data.[].latitude").type(NUMBER).description("?????? ??????"),
          fieldWithPath("data.[].longitude").type(NUMBER).description("?????? ??????")
        )
      ));
  }

}


