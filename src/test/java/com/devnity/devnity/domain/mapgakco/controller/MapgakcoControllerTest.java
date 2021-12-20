package com.devnity.devnity.domain.mapgakco.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
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

import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoPageRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoUpdateRequest;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.repository.mapgakco.MapgakcoRepository;
import com.devnity.devnity.domain.mapgakco.service.MapService;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.setting.annotation.WithJwtAuthUser;
import com.devnity.devnity.setting.provider.MapgakcoProvider;
import com.devnity.devnity.setting.provider.TestHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class MapgakcoControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  MapgakcoProvider mapgakcoProvider;
  @Autowired
  TestHelper testHelper;
  @Autowired
  MapgakcoRepository mapgakcoRepository;
  @Autowired
  MapService mapService;
  @Autowired
  UserRepository userRepository;

  private User user;
  private Mapgakco mapgakco;

  @BeforeEach
  @Transactional
  void setUp() throws Exception {
    user = userRepository.findUserByEmail("email@gmail.com").get();
    // 범위 -> nex: 37.57736394041695, ney: 127.03009029300624, swx: 37.55659510685803, swy: 126.9430729297755
    mapgakco = mapgakcoProvider.createMapgakco(user, 37.566653033875774, 126.97876549797886); // 서울특별시청
    mapgakcoProvider.createMapgakco(user, 37.56503610058175, 126.96217261676021); // 경기대 서울캠
    mapgakcoProvider.createMapgakco(user, 37.571484004598325, 126.9919403294101); // 종로3가역

    // 범위 -> nex: 37.5870833561458, ney: 127.0598034558777, swx: 37.54192700434515, swy: 126.89175793437813
    mapgakcoProvider.createMapgakco(user, 37.549143911582256, 126.91349757858292); // 합정역
    mapgakcoProvider.createMapgakco(user, 37.58041326711556, 127.04578258300938); // 청량리역
    mapgakcoProvider.createMapgakco(user, 37.58009056466645, 126.9228016641275); // 명지대 인문캠
  }

  @AfterEach
  void tearDown() {
    testHelper.clean();
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("맵각코를 등록할 수 있다.")
  void createMapgakcoTest() throws Exception {
    // given
    MapgakcoCreateRequest request = MapgakcoCreateRequest.builder()
      .title("맵각코 제목")
      .applicantLimit(5)
      .content("맵각코 내용")
      .location("맵각코 위치")
      .latitude(37.566653033875774)
      .longitude(126.97876549797886)
      .meetingAt(LocalDateTime.now())
      .build();

    // when
    ResultActions actions = mockMvc.perform(
      post("/api/v1/mapgakcos")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "JSON WEB TOKEN")
        .content(objectMapper.writeValueAsString(request)));

    // then
    actions.andExpect(status().isOk())
      .andDo(print())
      .andDo(document("mapgakcos/mapgakco/createMapgakco",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        requestFields(
          fieldWithPath("title").type(STRING).description("맵각코 제목"),
          fieldWithPath("applicantLimit").type(NUMBER).description("맵각코 신청자 제한수"),
          fieldWithPath("content").type(STRING).description("맵각코 내용"),
          fieldWithPath("location").type(STRING).description("맵각코 위치"),
          fieldWithPath("latitude").type(NUMBER).description("맵각코 위도"),
          fieldWithPath("longitude").type(NUMBER).description("맵각코 경도"),
          fieldWithPath("meetingAt").type(STRING).description("맵각코 날짜")
        ),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
          fieldWithPath("serverDatetime").type(STRING).description("서버 시간"),
          fieldWithPath("data.status").type(STRING).description("맵각코 status")
        )
      ));
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("맵각코 초기 페이징 조회또는 중심점 변경하여 조회를 할 수 있다.")
  void getFirstMapgakcosTest() throws Exception {
    // given
    MapgakcoPageRequest request = MapgakcoPageRequest.builder()
      .lastDistance(0.0)
      .centerX(37.566653033875774)
      .centerY(126.97876549797886)
      .currentNEX(37.57736394041695)
      .currentNEY(127.03009029300624)
      .currentSWX(37.55659510685803)
      .currentSWY(126.9430729297755)
      .build();

    // when
    ResultActions actions = mockMvc.perform(
      get("/api/v1/mapgakcos")
        .param("lastDistance", String.valueOf(request.getLastDistance()))
        .param("centerX", String.valueOf(request.getCenterX()))
        .param("centerY", String.valueOf(request.getCenterY()))
        .param("currentNEX", String.valueOf(request.getCurrentNEX()))
        .param("currentNEY", String.valueOf(request.getCurrentNEY()))
        .param("currentSWX", String.valueOf(request.getCurrentSWX()))
        .param("currentSWY", String.valueOf(request.getCurrentSWY())));

    // then
    actions.andExpect(status().isOk())
      .andDo(print())
      .andDo(document("mapgakcos/mapgakco/getFirstMapgakcos",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
          fieldWithPath("serverDatetime").type(STRING).description("서버 시간"),
          fieldWithPath("data.lastDistance").type(NUMBER).description("조회한 최대거리"),
          fieldWithPath("data.hasNext").type(BOOLEAN).description("다음 페이징 조회할 맵각코의 여부"),
          fieldWithPath("data.mapgakcos").type(ARRAY).description("맵각코 리스트"),
          fieldWithPath("data.mapgakcos.[].mapgakcoId").type(NUMBER).description("맵각코 ID"),
          fieldWithPath("data.mapgakcos.[].status").type(STRING).description("맵각코 상태"),
          fieldWithPath("data.mapgakcos.[].title").type(STRING).description("맵각코 제목"),
          fieldWithPath("data.mapgakcos.[].location").type(STRING).description("맵각코 위치"),
          fieldWithPath("data.mapgakcos.[].meetingAt").type(STRING).description("맵각코 모임일자"),
          fieldWithPath("data.mapgakcos.[].latitude").type(NUMBER).description("맵각코 위도"),
          fieldWithPath("data.mapgakcos.[].longitude").type(NUMBER).description("맵각코 경도"),
          fieldWithPath("data.mapgakcos.[].applicantLimit").type(NUMBER).description("맵각코 지원 제한 인원"),
          fieldWithPath("data.mapgakcos.[].applicantCount").type(NUMBER).description("맵각코 지원자 수"),
          fieldWithPath("data.mapgakcos.[].createdAt").type(STRING).description("맵각코 생성일자"),
          fieldWithPath("data.mapgakcos.[].author").type(OBJECT).description("맵각코 작성자"),
          fieldWithPath("data.mapgakcos.[].author.userId").type(NUMBER).description("맵각코 작성자 ID"),
          fieldWithPath("data.mapgakcos.[].author.name").type(STRING).description("맵각코 작성자 이름"),
          fieldWithPath("data.mapgakcos.[].author.course").type(STRING).description("맵각코 작성자 코스"),
          fieldWithPath("data.mapgakcos.[].author.generation").type(NUMBER).description("맵각코 작성자 기수"),
          fieldWithPath("data.mapgakcos.[].author.profileImgUrl").type(NULL).description("맵각코 작성자 이미지 URL"),
          fieldWithPath("data.mapgakcos.[].author.role").type(STRING).description("맵각코 작성자 역할")
        )
      ));
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("맵각코를 페이징 조회할 수 있다.")
  void getNextMapgakcosTest() throws Exception {
    // given
    MapgakcoPageRequest request = MapgakcoPageRequest.builder()
      .lastDistance(mapService.maxDistanceByTwoPoint(
        37.566653033875774,
        126.97876549797886,
        37.57736394041695,
        127.03009029300624,
        37.55659510685803,
        126.9430729297755,
        "meter"
      ))
      .centerX(37.566653033875774)
      .centerY(126.97876549797886)
      .currentNEX(37.5870833561458)
      .currentNEY(127.0598034558777)
      .currentSWX(37.54192700434515)
      .currentSWY(126.89175793437813)
      .build();

    // when
    ResultActions actions = mockMvc.perform(
      get("/api/v1/mapgakcos")
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
      .andDo(document("mapgakcos/mapgakco/getNextMapgakcos",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
          fieldWithPath("serverDatetime").type(STRING).description("서버 시간"),
          fieldWithPath("data.lastDistance").type(NUMBER).description("조회한 최대거리"),
          fieldWithPath("data.hasNext").type(BOOLEAN).description("다음 페이징 조회할 맵각코의 여부"),
          fieldWithPath("data.mapgakcos").type(ARRAY).description("맵각코 리스트"),
          fieldWithPath("data.mapgakcos.[].mapgakcoId").type(NUMBER).description("맵각코 ID"),
          fieldWithPath("data.mapgakcos.[].status").type(STRING).description("맵각코 상태"),
          fieldWithPath("data.mapgakcos.[].title").type(STRING).description("맵각코 제목"),
          fieldWithPath("data.mapgakcos.[].location").type(STRING).description("맵각코 위치"),
          fieldWithPath("data.mapgakcos.[].meetingAt").type(STRING).description("맵각코 모임일자"),
          fieldWithPath("data.mapgakcos.[].latitude").type(NUMBER).description("맵각코 위도"),
          fieldWithPath("data.mapgakcos.[].longitude").type(NUMBER).description("맵각코 경도"),
          fieldWithPath("data.mapgakcos.[].applicantLimit").type(NUMBER).description("맵각코 지원 제한 인원"),
          fieldWithPath("data.mapgakcos.[].applicantCount").type(NUMBER).description("맵각코 지원자 수"),
          fieldWithPath("data.mapgakcos.[].createdAt").type(STRING).description("맵각코 생성일자"),
          fieldWithPath("data.mapgakcos.[].author").type(OBJECT).description("맵각코 작성자"),
          fieldWithPath("data.mapgakcos.[].author.userId").type(NUMBER).description("맵각코 작성자 ID"),
          fieldWithPath("data.mapgakcos.[].author.name").type(STRING).description("맵각코 작성자 이름"),
          fieldWithPath("data.mapgakcos.[].author.course").type(STRING).description("맵각코 작성자 코스"),
          fieldWithPath("data.mapgakcos.[].author.generation").type(NUMBER).description("맵각코 작성자 기수"),
          fieldWithPath("data.mapgakcos.[].author.profileImgUrl").type(NULL).description("맵각코 작성자 이미지 URL"),
          fieldWithPath("data.mapgakcos.[].author.role").type(STRING).description("맵각코 작성자 역할")
        )
      ));
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("NE, SW 범위안의 맵각코를 조회할 수 있다.")
  void getMapgakcosWithinRangeTest() throws Exception {
    // given
    MapgakcoRequest request = MapgakcoRequest.builder()
      .currentNEX(37.57736394041695)
      .currentNEY(127.03009029300624)
      .currentSWX(37.55659510685803)
      .currentSWY(126.9430729297755)
      .build();

    // when
    ResultActions actions = mockMvc.perform(
      get("/api/v1/mapgakcos/range")
        .param("currentNEX", String.valueOf(request.getCurrentNEX()))
        .param("currentNEY", String.valueOf(request.getCurrentNEY()))
        .param("currentSWX", String.valueOf(request.getCurrentSWX()))
        .param("currentSWY", String.valueOf(request.getCurrentSWY()))
    );

    // then
    actions.andExpect(status().isOk())
      .andDo(print())
      .andDo(document("mapgakcos/mapgakco/getMapgakcosWithinRange",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
          fieldWithPath("serverDatetime").type(STRING).description("서버 시간"),
          fieldWithPath("data.[].mapgakcoId").type(NUMBER).description("맵각코 ID"),
          fieldWithPath("data.[].status").type(STRING).description("맵각코 상태"),
          fieldWithPath("data.[].title").type(STRING).description("맵각코 제목"),
          fieldWithPath("data.[].location").type(STRING).description("맵각코 위치"),
          fieldWithPath("data.[].meetingAt").type(STRING).description("맵각코 모임일자"),
          fieldWithPath("data.[].latitude").type(NUMBER).description("맵각코 위도"),
          fieldWithPath("data.[].longitude").type(NUMBER).description("맵각코 경도"),
          fieldWithPath("data.[].applicantLimit").type(NUMBER).description("맵각코 지원 제한 인원"),
          fieldWithPath("data.[].applicantCount").type(NUMBER).description("맵각코 지원자 수"),
          fieldWithPath("data.[].createdAt").type(STRING).description("맵각코 생성일자"),
          fieldWithPath("data.[].author").type(OBJECT).description("맵각코 작성자"),
          fieldWithPath("data.[].author.userId").type(NUMBER).description("맵각코 작성자 ID"),
          fieldWithPath("data.[].author.name").type(STRING).description("맵각코 작성자 이름"),
          fieldWithPath("data.[].author.course").type(STRING).description("맵각코 작성자 코스"),
          fieldWithPath("data.[].author.generation").type(NUMBER).description("맵각코 작성자 기수"),
          fieldWithPath("data.[].author.profileImgUrl").type(NULL).description("맵각코 작성자 이미지 URL"),
          fieldWithPath("data.[].author.role").type(STRING).description("맵각코 작성자 역할")
        )
      ));
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("맵각코를 수정할 수 있다.")
  void updateMapgakcoTest() throws Exception {
    // given
    Long mapgakcoId = mapgakco.getId();
    MapgakcoUpdateRequest request = MapgakcoUpdateRequest.builder()
      .title("맵각코 제목")
      .content("맵각코 내용")
      .location("맵각코 위치")
      .latitude(37.566752)
      .longitude(126.978935)
      .meetingAt(LocalDateTime.now().plusDays(3L))
      .build();

    // when
    ResultActions actions = mockMvc.perform(
      patch("/api/v1/mapgakcos/{mapgakcoId}", mapgakcoId)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "JSON WEB TOKEN")
        .content(objectMapper.writeValueAsString(request)));

    // then
    actions.andExpect(status().isOk())
      .andDo(print())
      .andDo(document("mapgakcos/mapgakco/updateMapgakco",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        pathParameters(
          parameterWithName("mapgakcoId").description(JsonFieldType.NUMBER).description("맵각코 ID")
        ),
        requestFields(
          fieldWithPath("title").type(STRING).description("맵각코 제목"),
          fieldWithPath("content").type(STRING).description("맵각코 내용"),
          fieldWithPath("location").type(STRING).description("맵각코 위치"),
          fieldWithPath("latitude").type(NUMBER).description("맵각코 위도"),
          fieldWithPath("longitude").type(NUMBER).description("맵각코 경도"),
          fieldWithPath("meetingAt").type(STRING).description("맵각코 날짜")
        ),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
          fieldWithPath("serverDatetime").type(STRING).description("서버 시간"),
          fieldWithPath("data.status").type(STRING).description("맵각코 status")
        )
      ));
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("맵각코를 삭제할 수 있다.")
  void deleteMapgakcoTest() throws Exception {
    // given
    Long mapgakcoId = mapgakco.getId();

    // when
    ResultActions actions = mockMvc.perform(
      delete("/api/v1/mapgakcos/{mapgakcoId}", mapgakcoId)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "JSON WEB TOKEN"));

    // then
    actions.andExpect(status().isOk())
      .andDo(print())
      .andDo(document("mapgakcos/mapgakco/deleteMapgakco",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        pathParameters(
          parameterWithName("mapgakcoId").description(JsonFieldType.NUMBER).description("맵각코 ID")
        ),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
          fieldWithPath("serverDatetime").type(STRING).description("서버 시간"),
          fieldWithPath("data").type(STRING).description("응답 데이터")
        )
      ));
  }

}