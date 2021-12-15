package com.devnity.devnity.domain.gather.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devnity.devnity.domain.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.setting.annotation.WithJwtAuthUser;
import com.devnity.devnity.setting.provider.GatherProvider;
import com.devnity.devnity.setting.provider.TestHelper;
import com.devnity.devnity.setting.provider.UserProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class GatherControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  TestHelper testHelper;
  @Autowired
  UserProvider userProvider;
  @Autowired
  GatherProvider gatherProvider;

  @AfterEach
  void tearDown() {
    testHelper.clean();
  }

  @WithJwtAuthUser(email = "me@mail.com", role = UserRole.STUDENT)
  @Test
  void 모집_게시글_등록() throws Exception {
    // Given
    String request = objectMapper.writeValueAsString(
      CreateGatherRequest.builder()
        .title("제목 : 코테 스터디 모집해용!!!")
        .applicantLimit(5)
        .deadline(LocalDateTime.now())
        .content("### 게시글 내용 (마크다운)")
        .category(GatherCategory.STUDY)
        .build()
    );

    // When
    ResultActions result = mockMvc.perform(
      post("/api/v1/gathers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(request)
    );

    // Then
    result
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "gathers/create", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          requestFields(
            fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
            fieldWithPath("applicantLimit").type(JsonFieldType.NUMBER).description("마감 시각"),
            fieldWithPath("deadline").type(JsonFieldType.STRING).description("마감 일자"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용(마크다운)"),
            fieldWithPath("category").type(JsonFieldType.STRING).description("모집 유형")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버시간"),
            fieldWithPath("data.status").type(JsonFieldType.STRING).description("게시물 상태")
          )
        )
      );
  }

  @WithJwtAuthUser(email = "me@mail.com", role = UserRole.STUDENT)
  @Test
  void 모집_추천_조회() throws Exception {
    // Given
    User user = userProvider.createUser();
    gatherProvider.createGather(user);
    gatherProvider.createGather(user, GatherStatus.CLOSED);
    gatherProvider.createGather(user, GatherStatus.DELETED);

    // When
    ResultActions result = mockMvc.perform(
      get("/api/v1/gathers/suggest")
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Then
    result
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "gathers/suggest", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버시간"),

            fieldWithPath("data.gathers[]").type(JsonFieldType.ARRAY).description("모집 게시글 추천 리스트"),
            fieldWithPath("data.gathers[].gatherId").type(JsonFieldType.NUMBER).description("모집 게시글 ID"),
            fieldWithPath("data.gathers[].status").type(JsonFieldType.STRING).description("게시글 상태"),
            fieldWithPath("data.gathers[].title").type(JsonFieldType.STRING).description("제목"),
            fieldWithPath("data.gathers[].category").type(JsonFieldType.STRING).description("카테고리"),
            fieldWithPath("data.gathers[].deadline").type(JsonFieldType.STRING).description("모집 마감 기한"),
            fieldWithPath("data.gathers[].createdAt").type(JsonFieldType.STRING).description("작성 시간"),
            fieldWithPath("data.gathers[].applicantLimit").type(JsonFieldType.NUMBER).description("마감 인원"),
            fieldWithPath("data.gathers[].view").type(JsonFieldType.NUMBER).description("조회수"),
            fieldWithPath("data.gathers[].applicantCount").type(JsonFieldType.NUMBER).description("신청자 수"),
            fieldWithPath("data.gathers[].commentCount").type(JsonFieldType.NUMBER).description("댓글 수"),

            fieldWithPath("data.gathers[].author").type(JsonFieldType.OBJECT).description("모집 게시글 작성자 정보"),
            fieldWithPath("data.gathers[].author.userId").type(JsonFieldType.NUMBER).description("작성자 ID"),
            fieldWithPath("data.gathers[].author.name").type(JsonFieldType.STRING).description("이름"),
            fieldWithPath("data.gathers[].author.course").type(JsonFieldType.STRING).description("코스"),
            fieldWithPath("data.gathers[].author.generation").type(JsonFieldType.NUMBER).description("기수"),
            fieldWithPath("data.gathers[].author.role").type(JsonFieldType.STRING).description("역할"),
            fieldWithPath("data.gathers[].author.profileImgUrl").type(JsonFieldType.NULL).description("프로필 사진 URL")
          )
        )
      );
  }


  @WithJwtAuthUser(email = "me@mail.com", role = UserRole.STUDENT)
  @Test
  void 모집_게시판_조회() throws Exception {
    // Given
    User user = userProvider.createUser();
    gatherProvider.createGather(user);
    gatherProvider.createGather(user, GatherStatus.CLOSED);
    gatherProvider.createGather(user, GatherStatus.DELETED);

    // When
    ResultActions result = mockMvc.perform(
      get("/api/v1/gathers")
        .contentType(MediaType.APPLICATION_JSON)
        .param("category", "")
        .param("lastId", "")
        .param("size", String.valueOf(10))
    );

    // Then
    result
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "gathers/board", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          requestParameters(
            parameterWithName("category").description("모집 카테고리 (모든 카테고리일 경우 null)"),
            parameterWithName("lastId").description("이전 응답의 마지막 gatherId (첫 요청시엔 null)"),
            parameterWithName("size").description("페이지 사이즈")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버시간"),

            fieldWithPath("data.values[]").type(JsonFieldType.ARRAY).description("페이징 결과 리스트"),
            fieldWithPath("data.values[].gatherId").type(JsonFieldType.NUMBER).description("모집 게시글 ID"),
            fieldWithPath("data.values[].status").type(JsonFieldType.STRING).description("게시글 상태"),
            fieldWithPath("data.values[].title").type(JsonFieldType.STRING).description("제목"),
            fieldWithPath("data.values[].category").type(JsonFieldType.STRING).description("카테고리"),
            fieldWithPath("data.values[].deadline").type(JsonFieldType.STRING).description("모집 마감 기한"),
            fieldWithPath("data.values[].createdAt").type(JsonFieldType.STRING).description("작성 시간"),
            fieldWithPath("data.values[].applicantLimit").type(JsonFieldType.NUMBER).description("마감 인원"),
            fieldWithPath("data.values[].view").type(JsonFieldType.NUMBER).description("조회수"),
            fieldWithPath("data.values[].applicantCount").type(JsonFieldType.NUMBER).description("신청자 수"),
            fieldWithPath("data.values[].commentCount").type(JsonFieldType.NUMBER).description("댓글 수"),

            fieldWithPath("data.values[].author").type(JsonFieldType.OBJECT).description("모집 게시글 작성자 정보"),
            fieldWithPath("data.values[].author.userId").type(JsonFieldType.NUMBER).description("작성자 ID"),
            fieldWithPath("data.values[].author.name").type(JsonFieldType.STRING).description("이름"),
            fieldWithPath("data.values[].author.course").type(JsonFieldType.STRING).description("코스"),
            fieldWithPath("data.values[].author.generation").type(JsonFieldType.NUMBER).description("기수"),
            fieldWithPath("data.values[].author.role").type(JsonFieldType.STRING).description("역할"),
            fieldWithPath("data.values[].author.profileImgUrl").type(JsonFieldType.NULL).description("프로필 사진 URL"),

            fieldWithPath("data.nextLastId").type(JsonFieldType.NUMBER).description("다음 페이징을 위한 마지막 gatherId")
          )
        )
      );
  }

  @WithJwtAuthUser(email = "me@mail.com", role = UserRole.STUDENT)
  @Test
  void 모집_게시글_상세조회() throws Exception {
    // Given
    User user = userProvider.createUser();
    Gather gather = gatherProvider.createGather(user);

    User me = userProvider.findMe("me@mail.com");
    GatherComment parent = gatherProvider.createParentComment(me, gather);
    gatherProvider.createChildComment(me, gather, parent);
    gatherProvider.createApplicant(me, gather);

    // When
    ResultActions result = mockMvc.perform(
      get("/api/v1/gathers/{gatherId}", gather.getId())
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Then
    result
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "gathers/detail", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          pathParameters(
            parameterWithName("gatherId").description("모집 게시글 ID")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버시간"),

            // 게시물 정보
            fieldWithPath("data").type(JsonFieldType.OBJECT).description("모집 상세 정보"),
            fieldWithPath("data.gatherId").type(JsonFieldType.NUMBER).description("모집 게시글 ID"),
            fieldWithPath("data.status").type(JsonFieldType.STRING).description("게시글 상태"),
            fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
            fieldWithPath("data.category").type(JsonFieldType.STRING).description("카테고리"),
            fieldWithPath("data.deadline").type(JsonFieldType.STRING).description("모집 마감기한"),
            fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("작성 시간"),
            fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정 시간"),
            fieldWithPath("data.applicantLimit").type(JsonFieldType.NUMBER).description("마감 인원"),
            fieldWithPath("data.view").type(JsonFieldType.NUMBER).description("조회수"),
            fieldWithPath("data.applicantCount").type(JsonFieldType.NUMBER).description("신청자 수"),
            fieldWithPath("data.commentCount").type(JsonFieldType.NUMBER).description("댓글 수"),

            // '나'의 신청여부
            fieldWithPath("data.isApplied").type(JsonFieldType.BOOLEAN).description("나의 해당 모집 신청 여부"),

            // 신청자 리스트
            fieldWithPath("data.participants[]").type(JsonFieldType.ARRAY).description("모집 신청자 리스트"),
            fieldWithPath("data.participants[].userId").type(JsonFieldType.NUMBER).description("신청자 ID"),
            fieldWithPath("data.participants[].name").type(JsonFieldType.STRING).description("이름"),
            fieldWithPath("data.participants[].course").type(JsonFieldType.STRING).description("코스"),
            fieldWithPath("data.participants[].generation").type(JsonFieldType.NUMBER).description("기수"),
            fieldWithPath("data.participants[].profileImgUrl").type(JsonFieldType.NULL).description("프로필 사진 URL"),
            fieldWithPath("data.participants[].role").type(JsonFieldType.STRING).description("역할"),

            // 댓글 리스트
            fieldWithPath("data.comments[]").type(JsonFieldType.ARRAY).description("댓글 리스트"),
            fieldWithPath("data.comments[].commentId").type(JsonFieldType.NUMBER).description("댓글 ID"),
            fieldWithPath("data.comments[].content").type(JsonFieldType.STRING).description("내용"),
            fieldWithPath("data.comments[].createdAt").type(JsonFieldType.STRING).description("작성 시간"),
            fieldWithPath("data.comments[].modifiedAt").type(JsonFieldType.STRING).description("수정 시간"),
            fieldWithPath("data.comments[].status").type(JsonFieldType.STRING).description("댓글 상태"),

            fieldWithPath("data.comments[].author").type(JsonFieldType.OBJECT).description("댓글 작성자 정보"),
            fieldWithPath("data.comments[].author.userId").type(JsonFieldType.NUMBER).description("댓글 작성자 ID"),
            fieldWithPath("data.comments[].author.name").type(JsonFieldType.STRING).description("이름"),
            fieldWithPath("data.comments[].author.course").type(JsonFieldType.STRING).description("코스"),
            fieldWithPath("data.comments[].author.generation").type(JsonFieldType.NUMBER).description("기수"),
            fieldWithPath("data.comments[].author.profileImgUrl").type(JsonFieldType.NULL).description("프로필 사진 URL"),
            fieldWithPath("data.comments[].author.role").type(JsonFieldType.STRING).description("역할"),
            // 대댓글 리스트
            fieldWithPath("data.comments[].children[]").type(JsonFieldType.ARRAY).description("대댓글 리스트"),
            fieldWithPath("data.comments[].children[].commentId").type(JsonFieldType.NUMBER).description("대댓글 ID"),
            fieldWithPath("data.comments[].children[].parentId").type(JsonFieldType.NUMBER).description("부모 댓글 ID"),
            fieldWithPath("data.comments[].children[].content").type(JsonFieldType.STRING).description("내용"),
            fieldWithPath("data.comments[].children[].createdAt").type(JsonFieldType.STRING).description("작성 시간"),
            fieldWithPath("data.comments[].children[].modifiedAt").type(JsonFieldType.STRING).description("수정 시간"),

            fieldWithPath("data.comments[].children[].author").type(JsonFieldType.OBJECT).description("대댓글 작성자 정보"),
            fieldWithPath("data.comments[].children[].author.userId").type(JsonFieldType.NUMBER).description("대댓글 작성자 ID"),
            fieldWithPath("data.comments[].children[].author.name").type(JsonFieldType.STRING).description("이름"),
            fieldWithPath("data.comments[].children[].author.course").type(JsonFieldType.STRING).description("코스"),
            fieldWithPath("data.comments[].children[].author.generation").type(JsonFieldType.NUMBER).description("기수"),
            fieldWithPath("data.comments[].children[].author.profileImgUrl").type(JsonFieldType.NULL).description("프로필 사진 URL"),
            fieldWithPath("data.comments[].children[].author.role").type(JsonFieldType.STRING).description("역할")
          )
        )
      );
  }

}