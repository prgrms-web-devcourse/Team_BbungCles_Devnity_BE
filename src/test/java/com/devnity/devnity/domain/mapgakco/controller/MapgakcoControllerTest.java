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

import com.devnity.devnity.web.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
import com.devnity.devnity.web.mapgakco.dto.mapgakco.request.MapgakcoPageRequest;
import com.devnity.devnity.web.mapgakco.dto.mapgakco.request.MapgakcoRequest;
import com.devnity.devnity.web.mapgakco.dto.mapgakco.request.MapgakcoUpdateRequest;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.repository.mapgakco.MapgakcoRepository;
import com.devnity.devnity.web.mapgakco.service.MapService;
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
    // ?????? -> nex: 37.57736394041695, ney: 127.03009029300624, swx: 37.55659510685803, swy: 126.9430729297755
    mapgakco = mapgakcoProvider.createMapgakco(user, 37.566653033875774, 126.97876549797886); // ??????????????????
    mapgakcoProvider.createMapgakco(user, 37.56503610058175, 126.96217261676021); // ????????? ?????????
    mapgakcoProvider.createMapgakco(user, 37.571484004598325, 126.9919403294101); // ??????3??????

    // ?????? -> nex: 37.5870833561458, ney: 127.0598034558777, swx: 37.54192700434515, swy: 126.89175793437813
    mapgakcoProvider.createMapgakco(user, 37.549143911582256, 126.91349757858292); // ?????????
    mapgakcoProvider.createMapgakco(user, 37.58041326711556, 127.04578258300938); // ????????????
    mapgakcoProvider.createMapgakco(user, 37.58009056466645, 126.9228016641275); // ????????? ?????????
  }

  @AfterEach
  void tearDown() {
    testHelper.clean();
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("???????????? ????????? ??? ??????.")
  void createMapgakcoTest() throws Exception {
    // given
    MapgakcoCreateRequest request = MapgakcoCreateRequest.builder()
      .title("????????? ??????")
      .applicantLimit(5)
      .content("????????? ??????")
      .location("????????? ??????")
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
          fieldWithPath("title").type(STRING).description("????????? ??????"),
          fieldWithPath("applicantLimit").type(NUMBER).description("????????? ????????? ?????????"),
          fieldWithPath("content").type(STRING).description("????????? ??????"),
          fieldWithPath("location").type(STRING).description("????????? ??????"),
          fieldWithPath("latitude").type(NUMBER).description("????????? ??????"),
          fieldWithPath("longitude").type(NUMBER).description("????????? ??????"),
          fieldWithPath("meetingAt").type(STRING).description("????????? ??????")
        ),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
          fieldWithPath("serverDatetime").type(STRING).description("?????? ??????"),
          fieldWithPath("data.mapgakcoId").type(NUMBER).description("????????? ID"),
          fieldWithPath("data.status").type(STRING).description("????????? status")
        )
      ));
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("????????? ?????? ????????? ???????????? ????????? ???????????? ????????? ??? ??? ??????.")
  void getFirstMapgakcosTest() throws Exception {
    // given
    MapgakcoPageRequest request = MapgakcoPageRequest.builder()
      .lastDistance(0.0)
      .centerY(37.566653033875774)
      .centerX(126.97876549797886)
      .currentNEY(37.57736394041695)
      .currentNEX(127.03009029300624)
      .currentSWY(37.55659510685803)
      .currentSWX(126.9430729297755)
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
          fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
          fieldWithPath("serverDatetime").type(STRING).description("?????? ??????"),
          fieldWithPath("data.lastDistance").type(NUMBER).description("????????? ????????????"),
          fieldWithPath("data.hasNext").type(BOOLEAN).description("?????? ????????? ????????? ???????????? ??????"),
          fieldWithPath("data.mapgakcos").type(ARRAY).description("????????? ?????????"),
          fieldWithPath("data.mapgakcos.[].mapgakcoId").type(NUMBER).description("????????? ID"),
          fieldWithPath("data.mapgakcos.[].status").type(STRING).description("????????? ??????"),
          fieldWithPath("data.mapgakcos.[].title").type(STRING).description("????????? ??????"),
          fieldWithPath("data.mapgakcos.[].location").type(STRING).description("????????? ??????"),
          fieldWithPath("data.mapgakcos.[].meetingAt").type(STRING).description("????????? ????????????"),
          fieldWithPath("data.mapgakcos.[].latitude").type(NUMBER).description("????????? ??????"),
          fieldWithPath("data.mapgakcos.[].longitude").type(NUMBER).description("????????? ??????"),
          fieldWithPath("data.mapgakcos.[].applicantLimit").type(NUMBER).description("????????? ?????? ?????? ??????"),
          fieldWithPath("data.mapgakcos.[].applicantCount").type(NUMBER).description("????????? ????????? ???"),
          fieldWithPath("data.mapgakcos.[].createdAt").type(STRING).description("????????? ????????????"),
          fieldWithPath("data.mapgakcos.[].author").type(OBJECT).description("????????? ?????????"),
          fieldWithPath("data.mapgakcos.[].author.userId").type(NUMBER).description("????????? ????????? ID"),
          fieldWithPath("data.mapgakcos.[].author.name").type(STRING).description("????????? ????????? ??????"),
          fieldWithPath("data.mapgakcos.[].author.course").type(STRING).description("????????? ????????? ??????"),
          fieldWithPath("data.mapgakcos.[].author.generation").type(NUMBER).description("????????? ????????? ??????"),
          fieldWithPath("data.mapgakcos.[].author.profileImgUrl").type(NULL).description("????????? ????????? ????????? URL"),
          fieldWithPath("data.mapgakcos.[].author.role").type(STRING).description("????????? ????????? ??????")
        )
      ));
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("???????????? ????????? ????????? ??? ??????.")
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
      .centerY(37.566653033875774)
      .centerX(126.97876549797886)
      .currentNEY(37.5870833561458)
      .currentNEX(127.0598034558777)
      .currentSWY(37.54192700434515)
      .currentSWX(126.89175793437813)
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
          fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
          fieldWithPath("serverDatetime").type(STRING).description("?????? ??????"),
          fieldWithPath("data.lastDistance").type(NUMBER).description("????????? ????????????"),
          fieldWithPath("data.hasNext").type(BOOLEAN).description("?????? ????????? ????????? ???????????? ??????"),
          fieldWithPath("data.mapgakcos").type(ARRAY).description("????????? ?????????"),
          fieldWithPath("data.mapgakcos.[].mapgakcoId").type(NUMBER).description("????????? ID"),
          fieldWithPath("data.mapgakcos.[].status").type(STRING).description("????????? ??????"),
          fieldWithPath("data.mapgakcos.[].title").type(STRING).description("????????? ??????"),
          fieldWithPath("data.mapgakcos.[].location").type(STRING).description("????????? ??????"),
          fieldWithPath("data.mapgakcos.[].meetingAt").type(STRING).description("????????? ????????????"),
          fieldWithPath("data.mapgakcos.[].latitude").type(NUMBER).description("????????? ??????"),
          fieldWithPath("data.mapgakcos.[].longitude").type(NUMBER).description("????????? ??????"),
          fieldWithPath("data.mapgakcos.[].applicantLimit").type(NUMBER).description("????????? ?????? ?????? ??????"),
          fieldWithPath("data.mapgakcos.[].applicantCount").type(NUMBER).description("????????? ????????? ???"),
          fieldWithPath("data.mapgakcos.[].createdAt").type(STRING).description("????????? ????????????"),
          fieldWithPath("data.mapgakcos.[].author").type(OBJECT).description("????????? ?????????"),
          fieldWithPath("data.mapgakcos.[].author.userId").type(NUMBER).description("????????? ????????? ID"),
          fieldWithPath("data.mapgakcos.[].author.name").type(STRING).description("????????? ????????? ??????"),
          fieldWithPath("data.mapgakcos.[].author.course").type(STRING).description("????????? ????????? ??????"),
          fieldWithPath("data.mapgakcos.[].author.generation").type(NUMBER).description("????????? ????????? ??????"),
          fieldWithPath("data.mapgakcos.[].author.profileImgUrl").type(NULL).description("????????? ????????? ????????? URL"),
          fieldWithPath("data.mapgakcos.[].author.role").type(STRING).description("????????? ????????? ??????")
        )
      ));
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("NE, SW ???????????? ???????????? ????????? ??? ??????.")
  void getMapgakcosWithinRangeTest() throws Exception {
    // given
    MapgakcoRequest request = MapgakcoRequest.builder()
      .currentNEY(37.57736394041695)
      .currentNEX(127.03009029300624)
      .currentSWY(37.55659510685803)
      .currentSWX(126.9430729297755)
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
          fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
          fieldWithPath("serverDatetime").type(STRING).description("?????? ??????"),
          fieldWithPath("data.[].mapgakcoId").type(NUMBER).description("????????? ID"),
          fieldWithPath("data.[].status").type(STRING).description("????????? ??????"),
          fieldWithPath("data.[].title").type(STRING).description("????????? ??????"),
          fieldWithPath("data.[].location").type(STRING).description("????????? ??????"),
          fieldWithPath("data.[].meetingAt").type(STRING).description("????????? ????????????"),
          fieldWithPath("data.[].latitude").type(NUMBER).description("????????? ??????"),
          fieldWithPath("data.[].longitude").type(NUMBER).description("????????? ??????"),
          fieldWithPath("data.[].applicantLimit").type(NUMBER).description("????????? ?????? ?????? ??????"),
          fieldWithPath("data.[].applicantCount").type(NUMBER).description("????????? ????????? ???"),
          fieldWithPath("data.[].createdAt").type(STRING).description("????????? ????????????"),
          fieldWithPath("data.[].author").type(OBJECT).description("????????? ?????????"),
          fieldWithPath("data.[].author.userId").type(NUMBER).description("????????? ????????? ID"),
          fieldWithPath("data.[].author.name").type(STRING).description("????????? ????????? ??????"),
          fieldWithPath("data.[].author.course").type(STRING).description("????????? ????????? ??????"),
          fieldWithPath("data.[].author.generation").type(NUMBER).description("????????? ????????? ??????"),
          fieldWithPath("data.[].author.profileImgUrl").type(NULL).description("????????? ????????? ????????? URL"),
          fieldWithPath("data.[].author.role").type(STRING).description("????????? ????????? ??????")
        )
      ));
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("???????????? ????????? ??? ??????.")
  void updateMapgakcoTest() throws Exception {
    // given
    Long mapgakcoId = mapgakco.getId();
    MapgakcoUpdateRequest request = MapgakcoUpdateRequest.builder()
      .title("????????? ??????")
      .content("????????? ??????")
      .location("????????? ??????")
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
          parameterWithName("mapgakcoId").description(JsonFieldType.NUMBER).description("????????? ID")
        ),
        requestFields(
          fieldWithPath("title").type(STRING).description("????????? ??????"),
          fieldWithPath("content").type(STRING).description("????????? ??????"),
          fieldWithPath("location").type(STRING).description("????????? ??????"),
          fieldWithPath("latitude").type(NUMBER).description("????????? ??????"),
          fieldWithPath("longitude").type(NUMBER).description("????????? ??????"),
          fieldWithPath("meetingAt").type(STRING).description("????????? ??????")
        ),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
          fieldWithPath("serverDatetime").type(STRING).description("?????? ??????"),
          fieldWithPath("data.status").type(STRING).description("????????? status")
        )
      ));
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("???????????? ????????? ??? ??????.")
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
          parameterWithName("mapgakcoId").description(JsonFieldType.NUMBER).description("????????? ID")
        ),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("?????? ??????"),
          fieldWithPath("serverDatetime").type(STRING).description("?????? ??????"),
          fieldWithPath("data").type(STRING).description("?????? ?????????")
        )
      ));
  }

}