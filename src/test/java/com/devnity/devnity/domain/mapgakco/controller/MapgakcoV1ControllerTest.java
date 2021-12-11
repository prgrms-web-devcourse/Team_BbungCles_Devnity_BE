//package com.devnity.devnity.domain.mapgakco.controller;
//
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
//import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
//import static org.springframework.restdocs.payload.JsonFieldType.STRING;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
//import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
//import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
//import com.devnity.devnity.domain.mapgakco.repository.MapgakcoRepository;
//import com.devnity.devnity.domain.user.entity.Authority;
//import com.devnity.devnity.domain.user.entity.Course;
//import com.devnity.devnity.domain.user.entity.Generation;
//import com.devnity.devnity.domain.user.entity.User;
//import com.devnity.devnity.domain.user.entity.UserRole;
//import com.devnity.devnity.domain.user.repository.CourseRepository;
//import com.devnity.devnity.domain.user.repository.GenerationRepository;
//import com.devnity.devnity.domain.user.repository.UserRepository;
//import com.devnity.devnity.test.annotation.WithJwtAuthUser;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.time.LocalDateTime;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.junit.jupiter.api.TestInstance.Lifecycle;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//@TestInstance(Lifecycle.PER_CLASS)
//@AutoConfigureMockMvc
//@AutoConfigureRestDocs
//@SpringBootTest
//class MapgakcoV1ControllerTest {
//
//  @Autowired
//  private MockMvc mockMvc;
//  @Autowired
//  private ObjectMapper objectMapper;
//  @Autowired
//  private UserRepository userRepository;
//  @Autowired
//  private CourseRepository courseRepository;
//  @Autowired
//  private GenerationRepository generationRepository;
//  @Autowired
//  private MapgakcoRepository mapgakcoRepository;
//  @Autowired
//  private IntroductionRepository introductionRepository;
//
//  private User user;
//
//  @BeforeAll
//  void init() throws Exception {
//    Course course = new Course("FE");
//    Generation generation = new Generation(1);
//
//    user = User.builder()
//      .email("email@gmail.com")
//      .course(course)
//      .generation(generation)
//      .password("password")
//      .name("seunghun")
//      .role(UserRole.STUDENT)
//      .authority(Authority.USER)
//      .build();
//    courseRepository.save(course);
//    generationRepository.save(generation);
//    userRepository.save(user);
//  }
//
//  @AfterAll
//  void tearDown() {
//    introductionRepository.deleteAll();
//    mapgakcoRepository.deleteAll();
//    userRepository.deleteAll();
//    courseRepository.deleteAll();
//    generationRepository.deleteAll();
//  }
//
//  @Test
//  @WithJwtAuthUser(email = "email@gmail.com", role = "USER")
//  @DisplayName("맵각코 등록 API 테스트")
//  void createMapgakcoTest() throws Exception {
//    MapgakcoCreateRequest request = MapgakcoCreateRequest.builder()
//      .title("맵각코")
//      .applicantLimit(5)
//      .deadline(LocalDateTime.now())
//      .content("모각코 모집중")
//      .location("어대역 5번출구")
//      .latitude(12.5)
//      .longitude(12.5)
//      .meetingAt(LocalDateTime.now())
//      .build();
//
//    mockMvc.perform(post("/api/v1/mapgakcos")
//        .contentType(MediaType.APPLICATION_JSON)
//        .content(objectMapper.writeValueAsString(request)))
//      .andExpect(status().isOk())
//      .andDo(print())
//      .andDo(document("mapgakcos/mapgakco/createMapgakco",
//        preprocessRequest(prettyPrint()),
//        preprocessResponse(prettyPrint()),
//        requestFields(
//          fieldWithPath("title").type(STRING).description("맵각코 제목"),
//          fieldWithPath("applicantLimit").type(NUMBER).description("맵각코 신청자 제한수"),
//          fieldWithPath("deadline").type(STRING).description("맵각코 마감일"),
//          fieldWithPath("content").type(STRING).description("맵각코 내용"),
//          fieldWithPath("location").type(STRING).description("맵각코 위치"),
//          fieldWithPath("latitude").type(NUMBER).description("맵각코 위도"),
//          fieldWithPath("longitude").type(NUMBER).description("맵각코 경도"),
//          fieldWithPath("meetingAt").type(STRING).description("맵각코 날짜")
//        ),
//        responseFields(
//          fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
//          fieldWithPath("serverDatetime").type(STRING).description("서버 시간"),
//          fieldWithPath("data.status").type(STRING).description("맵각코 status")
//        )
//      ));
//
//  }
//}