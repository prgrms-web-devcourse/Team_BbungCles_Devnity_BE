package com.devnity.devnity.domain.mapgakco.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NULL;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoComment;
import com.devnity.devnity.domain.mapgakco.repository.mapgakco.MapgakcoRepository;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.setting.annotation.WithJwtAuthUser;
import com.devnity.devnity.setting.provider.MapgakcoProvider;
import com.devnity.devnity.setting.provider.TestHelper;
import com.devnity.devnity.setting.provider.UserProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class MapgakcoDomainControllerTest {

  @Autowired
  MapgakcoRepository mapgakcoRepository;
  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  MapgakcoProvider mapgakcoProvider;
  @Autowired
  UserProvider userProvider;
  @Autowired
  TestHelper testHelper;
  @Autowired
  EntityManager em;

  private Long mapgakcoId;

  @BeforeEach
  void setUp() throws Exception {
    Mapgakco mapgakco = mapgakcoProvider.createMapgakco(
      userProvider.createUser("1", 1, "1@emali.com"));
    mapgakcoProvider.createApplicant(userProvider.createUser("2", 2, "2@emali.com"), mapgakco);
    mapgakcoProvider.createApplicant(userProvider.createUser("3", 3, "3@emali.com"), mapgakco);
    mapgakcoProvider.createApplicant(userProvider.createUser("4", 4, "4@emali.com"), mapgakco);

    MapgakcoComment comment1 = mapgakcoProvider.createComment(
      userProvider.createUser("5", 5, "5@emali.com"), mapgakco, null);
    MapgakcoComment comment2 = mapgakcoProvider.createComment(
      userProvider.createUser("6", 6, "6@emali.com"), mapgakco, null);
    MapgakcoComment comment3 = mapgakcoProvider.createComment(
      userProvider.createUser("7", 7, "7@emali.com"), mapgakco, null);

    mapgakcoProvider.createComment(userProvider.createUser("8", 8, "8@emali.com"), mapgakco,
      comment1);
    mapgakcoProvider.createComment(userProvider.createUser("9", 9, "9@emali.com"), mapgakco,
      comment2);
    mapgakcoProvider.createComment(userProvider.createUser("10", 10, "10@emali.com"), mapgakco,
      comment2);
    mapgakcoProvider.createComment(userProvider.createUser("11", 11, "11@emali.com"), mapgakco,
      comment3);
    mapgakcoProvider.createComment(userProvider.createUser("12", 12, "12@emali.com"), mapgakco,
      comment3);

    mapgakcoId = mapgakco.getId();
  }

  @AfterEach
  void tearDown() {
    testHelper.clean();
  }

  @Test
  @WithJwtAuthUser(email = "email@gmail.com", role = UserRole.STUDENT)
  @DisplayName("맵각코를 상세조회할 수 있다.")
  void getMapgakcoDetailTest() throws Exception {
    // given
    // when
    ResultActions actions = mockMvc.perform(
      get("/api/v1/mapgakcos/{mapgakcoId}", mapgakcoId)
        .contentType(MediaType.APPLICATION_JSON));

    // then
    actions.andExpect(status().isOk())
      .andDo(print())
      .andDo(document("mapgakcos/mapgakco/getMapgakcoDetail",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        responseFields(
          fieldWithPath("statusCode").type(NUMBER).description("상태 코드"),
          fieldWithPath("serverDatetime").type(STRING).description("서버 시간"),

          fieldWithPath("data.mapgakco.mapgakcoId").type(NUMBER).description("맵각코 ID"),
          fieldWithPath("data.mapgakco.applicantLimit").type(NUMBER).description("맵각코 신청자 수 제한"),
          fieldWithPath("data.mapgakco.applicantCount").type(NUMBER).description("맵각코 현재 신청자 수"),
          fieldWithPath("data.mapgakco.status.status").type(STRING).description("맵각코 상태"),
          fieldWithPath("data.mapgakco.title").type(STRING).description("맵각코 제목"),
          fieldWithPath("data.mapgakco.content").type(STRING).description("맵각코 내용"),
          fieldWithPath("data.mapgakco.location").type(STRING).description("맵각코 위치"),
          fieldWithPath("data.mapgakco.latitude").type(NUMBER).description("맵각코 위도"),
          fieldWithPath("data.mapgakco.longitude").type(NUMBER).description("맵각코 경도"),
          fieldWithPath("data.mapgakco.meetingAt").type(STRING).description("맵각코 모임 시간"),
          fieldWithPath("data.mapgakco.createdAt").type(STRING).description("맵각코 작성일시"),
          fieldWithPath("data.mapgakco.updatedAt").type(STRING).description("맵각코 수정일시"),

          fieldWithPath("data.writer.userId").type(NUMBER).description("맵각코 작성자(유저) ID"),
          fieldWithPath("data.writer.name").type(STRING).description("맵각코 작성자(유저) 이름"),
          fieldWithPath("data.writer.course").type(STRING).description("맵각코 작성자(유저) 코스"),
          fieldWithPath("data.writer.generation").type(NUMBER).description("맵각코 작성자(유저) 기수"),
          fieldWithPath("data.writer.profileImgUrl").type(NULL).description("맵각코 작성자(유저) 프사Url"),
          fieldWithPath("data.writer.role").type(STRING).description("맵각코 작성자(유저) 역할"),

          fieldWithPath("data.applicants.[].userId").type(NUMBER).description("맵각코 신청자(유저) ID"),
          fieldWithPath("data.applicants.[].name").type(STRING).description("맵각코 신청자(유저) 이름"),
          fieldWithPath("data.applicants.[].course").type(STRING).description("맵각코 신청자(유저) 코스"),
          fieldWithPath("data.applicants.[].generation").type(NUMBER).description("맵각코 신청자(유저) 기수"),
          fieldWithPath("data.applicants.[].profileImgUrl").type(NULL)
            .description("맵각코 신청자(유저) 프사Url"),
          fieldWithPath("data.applicants.[].role").type(STRING).description("맵각코 신청자(유저) 역할"),

          fieldWithPath("data.comments.[].commentId").type(NUMBER).description("맵각코 댓글 ID"),
          fieldWithPath("data.comments.[].content").type(STRING).description("맵각코 댓글 내용"),
          fieldWithPath("data.comments.[].status.status").type(STRING).description("맵각코 댓글 상태"),
          fieldWithPath("data.comments.[].createdAt").type(STRING).description("맵각코 댓글 작성일시"),
          fieldWithPath("data.comments.[].updatedAt").type(STRING).description("맵각코 댓글 수정일시"),

          fieldWithPath("data.comments.[].writer.userId").type(NUMBER)
            .description("맵각코 댓글 작성자(유저) ID"),
          fieldWithPath("data.comments.[].writer.name").type(STRING)
            .description("맵각코 댓글 작성자(유저) 이름"),
          fieldWithPath("data.comments.[].writer.course").type(STRING)
            .description("맵각코 댓글 작성자(유저) 코스"),
          fieldWithPath("data.comments.[].writer.generation").type(NUMBER)
            .description("맵각코 댓글 작성자(유저) 기수"),
          fieldWithPath("data.comments.[].writer.profileImgUrl").type(NULL)
            .description("맵각코 댓글 작성자(유저) 프사Url"),
          fieldWithPath("data.comments.[].writer.role").type(STRING)
            .description("맵각코 댓글 작성자(유저) 역할"),

          fieldWithPath("data.comments.[].children.[].commentId").type(NUMBER)
            .description("맵각코 대댓글 ID"),
          fieldWithPath("data.comments.[].children.[].content").type(STRING)
            .description("맵각코 대댓글 내용"),
          fieldWithPath("data.comments.[].children.[].status.status").type(STRING)
            .description("맵각코 대댓글 상태"),
          fieldWithPath("data.comments.[].children.[].createdAt").type(STRING)
            .description("맵각코 대댓글 작성일시"),
          fieldWithPath("data.comments.[].children.[].updatedAt").type(STRING)
            .description("맵각코 대댓글 수정일시"),

          fieldWithPath("data.comments.[].children.[].writer.userId").type(NUMBER)
            .description("맵각코 대댓글 작성자(유저) ID"),
          fieldWithPath("data.comments.[].children.[].writer.name").type(STRING)
            .description("맵각코 대댓글 작성자(유저) 이름"),
          fieldWithPath("data.comments.[].children.[].writer.course").type(STRING)
            .description("맵각코 대댓글 작성자(유저) 코스"),
          fieldWithPath("data.comments.[].children.[].writer.generation").type(NUMBER)
            .description("맵각코 대댓글 작성자(유저) 기수"),
          fieldWithPath("data.comments.[].children.[].writer.profileImgUrl").type(NULL)
            .description("맵각코 대댓글 작성자(유저) 프사Url"),
          fieldWithPath("data.comments.[].children.[].writer.role").type(STRING)
            .description("맵각코 대댓글 작성자(유저) 역할"),

          fieldWithPath("data.comments.[].children.[].children").type(NULL)
            .description("댓글 Depth는 2까지만")
        )
      ));
  }

}