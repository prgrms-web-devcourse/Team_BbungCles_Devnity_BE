package com.devnity.devnity.domain.gather.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devnity.devnity.web.gather.dto.request.CreateGatherCommentRequest;
import com.devnity.devnity.web.gather.dto.request.UpdateGatherCommentRequest;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.setting.annotation.WithJwtAuthUser;
import com.devnity.devnity.setting.provider.GatherProvider;
import com.devnity.devnity.setting.provider.TestHelper;
import com.devnity.devnity.setting.provider.UserProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
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
class GatherCommentControllerTest {

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
  void tearDown(){
    testHelper.clean();
  }

  @WithJwtAuthUser(email = "me@mail.com", role = UserRole.STUDENT)
  @Test
  void 모집_게시글_댓글_생성() throws Exception {
    // Given
    User author = userProvider.createUser();
    Gather gather = gatherProvider.createGather(author);
    String request = objectMapper.writeValueAsString(
      CreateGatherCommentRequest.builder()
        .parentId(null)
        .content("댓글댓글댓글")
        .build()
    );

    // When
    ResultActions result = mockMvc.perform(
      post("/api/v1/gathers/{gatherId}/comments", gather.getId())
        .header("Authorization", "JSON WEB TOKEN")
        .contentType(MediaType.APPLICATION_JSON)
        .content(request)
    );

    // Then
    result
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "gathers/comments/create", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          pathParameters(
            parameterWithName("gatherId").description("모집 게시글 ID")
          ),
          requestFields(
            fieldWithPath("parentId").type(JsonFieldType.NULL).description("부모 댓글 ID"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버시간"),
            fieldWithPath("data.commentId").type(JsonFieldType.NUMBER).description("댓글 ID"),
            fieldWithPath("data.parentId").type(JsonFieldType.NULL).description("부모 댓글 ID"),
            fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("댓글 작성 시간")
          )
        )
      );
  }

  @WithJwtAuthUser(email = "me@mail.com", role = UserRole.STUDENT)
  @Test
  void 모집_게시글_대댓글_생성() throws Exception {
    // Given
    User author = userProvider.createUser();
    Gather gather = gatherProvider.createGather(author);
    GatherComment parent = gatherProvider.createParentComment(author, gather);

    String request = objectMapper.writeValueAsString(
      CreateGatherCommentRequest.builder()
        .parentId(parent.getId())
        .content("나는야 대댓글")
        .build()
    );

    // When
    ResultActions result = mockMvc.perform(
      post("/api/v1/gathers/{gatherId}/comments", gather.getId())
        .header("Authorization", "JSON WEB TOKEN")
        .contentType(MediaType.APPLICATION_JSON)
        .content(request)
    );

    // Then
    result
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "gathers/comments/create-sub", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          pathParameters(
            parameterWithName("gatherId").description("모집 게시글 ID")
          ),
          requestFields(
            fieldWithPath("parentId").type(JsonFieldType.NUMBER).description("부모 댓글 ID"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("대댓글 내용")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버시간"),
            fieldWithPath("data.commentId").type(JsonFieldType.NUMBER).description("대댓글 ID"),
            fieldWithPath("data.parentId").type(JsonFieldType.NUMBER).description("부모 댓글 ID"),
            fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("댓글 작성 시간")
          )
        )
      );
  }

  @WithJwtAuthUser(email = "me@mail.com", role = UserRole.STUDENT)
  @Test
  void 모집_게시글_댓글_수정() throws Exception {
    // Given
    User me = userProvider.findMe("me@mail.com");
    Gather gather = gatherProvider.createGather(me);
    GatherComment comment = gatherProvider.createParentComment(me, gather);

    String request = objectMapper.writeValueAsString(
      UpdateGatherCommentRequest.builder()
        .content("댓글 수정할거임")
        .build()
    );

    // When
    ResultActions result = mockMvc.perform(
      patch("/api/v1/gathers/{gatherId}/comments/{commentId}", gather.getId(), comment.getId())
        .header("Authorization", "JSON WEB TOKEN")
        .contentType(MediaType.APPLICATION_JSON)
        .content(request)
    );

    // Then
    result
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "gathers/comments/update", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          pathParameters(
            parameterWithName("gatherId").description("모집 게시글 ID"),
            parameterWithName("commentId").description("댓글 ID")
          ),
          requestFields(
            fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 댓글 내용")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버시간"),
            fieldWithPath("data").type(JsonFieldType.STRING).description("성공 여부")
          )
        )
      );
  }


  @WithJwtAuthUser(email = "me@mail.com", role = UserRole.STUDENT)
  @Test
  void 모집_게시글_댓글_삭제() throws Exception {
    // Given
    User me = userProvider.findMe("me@mail.com");
    Gather gather = gatherProvider.createGather(me);
    GatherComment comment = gatherProvider.createParentComment(me, gather);

    // When
    ResultActions result = mockMvc.perform(
      delete("/api/v1/gathers/{gatherId}/comments/{commentId}", gather.getId(), comment.getId())
        .header("Authorization", "JSON WEB TOKEN")
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Then
    result
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "gathers/comments/delete", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          pathParameters(
            parameterWithName("gatherId").description("모집 게시글 ID"),
            parameterWithName("commentId").description("댓글 ID")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버시간"),
            fieldWithPath("data.content").type(JsonFieldType.STRING).description("삭제된 댓글")
          )
        )
      );
  }

}