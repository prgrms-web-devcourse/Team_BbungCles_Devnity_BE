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
  void 모집_게시글_페이징_조회() throws Exception {
    // Given
    User user = userProvider.createUser();
    for (int i = 0; i < 3; i++) {
      gatherProvider.createGather(user);
      gatherProvider.createGather(user, GatherStatus.CLOSED);
      gatherProvider.createGather(user, GatherStatus.FULL);
      gatherProvider.createGather(user, GatherStatus.DELETED);
    }

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
          "gathers/paging", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          requestParameters(
            parameterWithName("category").description("모집 카테고리"),
            parameterWithName("lastId").description("이전 응답의 마지막 gatherId"),
            parameterWithName("size").description("페이지 사이즈")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버시간"),

            fieldWithPath("data.nextLastId").type(JsonFieldType.NUMBER).description("다음 페이징을 위한 마지막 gatherId"),

            fieldWithPath("data.values[]").type(JsonFieldType.ARRAY).description("페이징 결과 리스트"),
            fieldWithPath("data.values[].gatherId").type(JsonFieldType.NUMBER).description("모집 게시글 ID"),
            fieldWithPath("data.values[].status").type(JsonFieldType.STRING).description("게시글 상태"),
            fieldWithPath("data.values[].title").type(JsonFieldType.STRING).description("제목"),
            fieldWithPath("data.values[].category").type(JsonFieldType.STRING).description("카테고리"),
            fieldWithPath("data.values[].deadline").type(JsonFieldType.STRING).description("모집 마감 기한"),
            fieldWithPath("data.values[].applicantLimit").type(JsonFieldType.NUMBER).description("마감 인원"),
            fieldWithPath("data.values[].view").type(JsonFieldType.NUMBER).description("조회수"),
            fieldWithPath("data.values[].applicantCount").type(JsonFieldType.NUMBER).description("신청자 수"),
            fieldWithPath("data.values[].commentCount").type(JsonFieldType.NUMBER).description("댓글 수"),

            fieldWithPath("data.values[].simpleUserInfo").type(JsonFieldType.OBJECT).description("모집 게시글 작성자 정보"),
            fieldWithPath("data.values[].simpleUserInfo.userId").type(JsonFieldType.NUMBER).description("작성자 ID"),
            fieldWithPath("data.values[].simpleUserInfo.name").type(JsonFieldType.STRING).description("이름"),
            fieldWithPath("data.values[].simpleUserInfo.course").type(JsonFieldType.STRING).description("코스"),
            fieldWithPath("data.values[].simpleUserInfo.generation").type(JsonFieldType.STRING).description("기수"),
            fieldWithPath("data.values[].simpleUserInfo.role").type(JsonFieldType.STRING).description("역할"),
            fieldWithPath("data.values[].simpleUserInfo.profileImgUrl").type(JsonFieldType.STRING).description("프로필 사진 URL")
          )
        )
      );


  }

}