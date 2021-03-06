package com.devnity.devnity.domain.gather.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devnity.devnity.web.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.web.gather.dto.request.UpdateGatherRequest;
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
import java.time.LocalDate;
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
  void ??????_?????????_??????() throws Exception {
    // Given
    String request = objectMapper.writeValueAsString(
      CreateGatherRequest.builder()
        .title("?????? : ?????? ????????? ????????????!!!")
        .applicantLimit(5)
        .deadline(LocalDate.now().plusDays(1))
        .content("### ????????? ?????? (????????????)")
        .category(GatherCategory.STUDY)
        .build()
    );

    // When
    ResultActions result = mockMvc.perform(
      post("/api/v1/gathers")
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
          "gathers/create", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          requestFields(
            fieldWithPath("title").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("applicantLimit").type(JsonFieldType.NUMBER).description("?????? ??????"),
            fieldWithPath("deadline").type(JsonFieldType.STRING).description("?????? ?????? (yyyy-MM-dd)"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("????????? ??????(????????????)"),
            fieldWithPath("category").type(JsonFieldType.STRING).description("?????? ??????")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("????????????"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("????????????"),
            fieldWithPath("data.gatherId").type(JsonFieldType.NUMBER).description("?????? ????????? ID"),
            fieldWithPath("data.status").type(JsonFieldType.STRING).description("????????? ??????")
          )
        )
      );
  }

  @WithJwtAuthUser(email = "me@mail.com", role = UserRole.STUDENT)
  @Test
  void ??????_?????????_??????() throws Exception {
    // Given
    User me = userProvider.findMe("me@mail.com");
    Gather gather = gatherProvider.createGather(me);

    String request = objectMapper.writeValueAsString(
      UpdateGatherRequest.builder()
        .title("????????? ??????~~")
        .deadline(LocalDate.now().plusDays(1))
        .content("????????? ????????? ?????? ?????????????????????")
        .category(GatherCategory.PROJECT)
        .build()
    );

    // When
    ResultActions result = mockMvc.perform(
      patch("/api/v1/gathers/{gatherId}", gather.getId())
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
          "gathers/update", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          pathParameters(
            parameterWithName("gatherId").description("????????? ?????? ????????? ID")
          ),
          requestFields(
            fieldWithPath("title").type(JsonFieldType.STRING).description("????????? ??????"),
            fieldWithPath("deadline").type(JsonFieldType.STRING).description("????????? ?????? ?????? (yyyy-MM-dd)"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("????????? ????????? ??????(????????????)"),
            fieldWithPath("category").type(JsonFieldType.STRING).description("????????? ????????????")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("????????????"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("????????????"),
            fieldWithPath("data.gatherId").type(JsonFieldType.NUMBER).description("????????? ????????? ID"),
            fieldWithPath("data.status").type(JsonFieldType.STRING).description("????????? ??????")
          )
        )
      );
  }

  @WithJwtAuthUser(email = "me@mail.com", role = UserRole.STUDENT)
  @Test
  void ??????_?????????_??????() throws Exception {
    // Given
    User me = userProvider.findMe("me@mail.com");
    Gather gather = gatherProvider.createGather(me);

    // When
    ResultActions result = mockMvc.perform(
      delete("/api/v1/gathers/{gatherId}", gather.getId())
        .header("Authorization", "JSON WEB TOKEN")
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Then
    result
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "gathers/delete", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          pathParameters(
            parameterWithName("gatherId").description("????????? ?????? ????????? ID")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("????????????"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("????????????"),
            fieldWithPath("data.gatherId").type(JsonFieldType.NUMBER).description("????????? ????????? ID"),
            fieldWithPath("data.status").type(JsonFieldType.STRING).description("????????? ??????")
          )
        )
      );
  }

  @WithJwtAuthUser(email = "me@mail.com", role = UserRole.STUDENT)
  @Test
  void ??????_??????() throws Exception {
    // Given
    User me = userProvider.findMe("me@mail.com");
    Gather gather = gatherProvider.createGather(me);

    // When
    ResultActions result = mockMvc.perform(
      patch("/api/v1/gathers/{gatherId}/close", gather.getId())
        .header("Authorization", "JSON WEB TOKEN")
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Then
    result
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "gathers/close", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          pathParameters(
            parameterWithName("gatherId").description("????????? ?????? ????????? ID")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("????????????"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("????????????"),
            fieldWithPath("data.gatherId").type(JsonFieldType.NUMBER).description("????????? ????????? ID"),
            fieldWithPath("data.status").type(JsonFieldType.STRING).description("????????? ??????")
          )
        )
      );
  }

  @WithJwtAuthUser(email = "me@mail.com", role = UserRole.STUDENT)
  @Test
  void ??????_??????_??????() throws Exception {
    // Given
    User user = userProvider.createUser();
    gatherProvider.createGather(user);
    gatherProvider.createGather(user, GatherStatus.CLOSED);
    gatherProvider.createGather(user, GatherStatus.DELETED);

    // When
    ResultActions result = mockMvc.perform(
      get("/api/v1/gathers/suggest")
        .header("Authorization", "JSON WEB TOKEN")
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
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("????????????"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("????????????"),

            fieldWithPath("data.gathers[]").type(JsonFieldType.ARRAY).description("?????? ????????? ?????? ?????????"),
            fieldWithPath("data.gathers[].gatherId").type(JsonFieldType.NUMBER).description("?????? ????????? ID"),
            fieldWithPath("data.gathers[].status").type(JsonFieldType.STRING).description("????????? ??????"),
            fieldWithPath("data.gathers[].title").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.gathers[].category").type(JsonFieldType.STRING).description("????????????"),
            fieldWithPath("data.gathers[].deadline").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
            fieldWithPath("data.gathers[].createdAt").type(JsonFieldType.STRING).description("?????? ??????"),
            fieldWithPath("data.gathers[].applicantLimit").type(JsonFieldType.NUMBER).description("?????? ??????"),
            fieldWithPath("data.gathers[].view").type(JsonFieldType.NUMBER).description("?????????"),
            fieldWithPath("data.gathers[].applicantCount").type(JsonFieldType.NUMBER).description("????????? ???"),
            fieldWithPath("data.gathers[].commentCount").type(JsonFieldType.NUMBER).description("?????? ???"),

            fieldWithPath("data.gathers[].author").type(JsonFieldType.OBJECT).description("?????? ????????? ????????? ??????"),
            fieldWithPath("data.gathers[].author.userId").type(JsonFieldType.NUMBER).description("????????? ID"),
            fieldWithPath("data.gathers[].author.name").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.gathers[].author.course").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.gathers[].author.generation").type(JsonFieldType.NUMBER).description("??????"),
            fieldWithPath("data.gathers[].author.role").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.gathers[].author.profileImgUrl").type(JsonFieldType.NULL).description("????????? ?????? URL")
          )
        )
      );
  }


  @WithJwtAuthUser(email = "me@mail.com", role = UserRole.STUDENT)
  @Test
  void ??????_?????????_??????() throws Exception {
    // Given
    User user = userProvider.createUser();
    gatherProvider.createGather(user);
    gatherProvider.createGather(user, GatherStatus.CLOSED);
    gatherProvider.createGather(user, GatherStatus.DELETED);

    // When
    ResultActions result = mockMvc.perform(
      get("/api/v1/gathers")
        .header("Authorization", "JSON WEB TOKEN")
        .contentType(MediaType.APPLICATION_JSON)
        .param("title", "")
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
            parameterWithName("title").description("????????? ??????"),
            parameterWithName("category").description("?????? ???????????? (?????? ??????????????? ?????? null)"),
            parameterWithName("lastId").description("?????? ????????? ????????? gatherId (??? ???????????? null)"),
            parameterWithName("size").description("????????? ?????????")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("????????????"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("????????????"),

            fieldWithPath("data.values[]").type(JsonFieldType.ARRAY).description("????????? ?????? ?????????"),
            fieldWithPath("data.values[].gatherId").type(JsonFieldType.NUMBER).description("?????? ????????? ID"),
            fieldWithPath("data.values[].status").type(JsonFieldType.STRING).description("????????? ??????"),
            fieldWithPath("data.values[].title").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.values[].category").type(JsonFieldType.STRING).description("????????????"),
            fieldWithPath("data.values[].deadline").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
            fieldWithPath("data.values[].createdAt").type(JsonFieldType.STRING).description("?????? ??????"),
            fieldWithPath("data.values[].applicantLimit").type(JsonFieldType.NUMBER).description("?????? ??????"),
            fieldWithPath("data.values[].view").type(JsonFieldType.NUMBER).description("?????????"),
            fieldWithPath("data.values[].applicantCount").type(JsonFieldType.NUMBER).description("????????? ???"),
            fieldWithPath("data.values[].commentCount").type(JsonFieldType.NUMBER).description("?????? ???"),

            fieldWithPath("data.values[].author").type(JsonFieldType.OBJECT).description("?????? ????????? ????????? ??????"),
            fieldWithPath("data.values[].author.userId").type(JsonFieldType.NUMBER).description("????????? ID"),
            fieldWithPath("data.values[].author.name").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.values[].author.course").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.values[].author.generation").type(JsonFieldType.NUMBER).description("??????"),
            fieldWithPath("data.values[].author.role").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.values[].author.profileImgUrl").type(JsonFieldType.NULL).description("????????? ?????? URL"),

            fieldWithPath("data.nextLastId").type(JsonFieldType.NUMBER).description("?????? ???????????? ?????? ????????? gatherId")
          )
        )
      );
  }

  @WithJwtAuthUser(email = "me@mail.com", role = UserRole.STUDENT)
  @Test
  void ??????_?????????_??????_??????() throws Exception {
    // Given
    User user = userProvider.createUser();
    gatherProvider.createGather(user);
    gatherProvider.createGather(user, GatherStatus.CLOSED);
    gatherProvider.createGather(user, GatherStatus.DELETED);
    gatherProvider.createGather(user, "??? ????????? ???????????????");

    // When
    ResultActions result = mockMvc.perform(
      get("/api/v1/gathers")
        .header("Authorization", "JSON WEB TOKEN")
        .contentType(MediaType.APPLICATION_JSON)
        .param("title", "??????")
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
          "gathers/search", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          requestParameters(
            parameterWithName("title").description("????????? ??????"),
            parameterWithName("category").description("?????? ???????????? (?????? ??????????????? ?????? null)"),
            parameterWithName("lastId").description("?????? ????????? ????????? gatherId (??? ???????????? null)"),
            parameterWithName("size").description("????????? ?????????")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("????????????"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("????????????"),

            fieldWithPath("data.values[]").type(JsonFieldType.ARRAY).description("????????? ?????? ?????????"),
            fieldWithPath("data.values[].gatherId").type(JsonFieldType.NUMBER).description("?????? ????????? ID"),
            fieldWithPath("data.values[].status").type(JsonFieldType.STRING).description("????????? ??????"),
            fieldWithPath("data.values[].title").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.values[].category").type(JsonFieldType.STRING).description("????????????"),
            fieldWithPath("data.values[].deadline").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
            fieldWithPath("data.values[].createdAt").type(JsonFieldType.STRING).description("?????? ??????"),
            fieldWithPath("data.values[].applicantLimit").type(JsonFieldType.NUMBER).description("?????? ??????"),
            fieldWithPath("data.values[].view").type(JsonFieldType.NUMBER).description("?????????"),
            fieldWithPath("data.values[].applicantCount").type(JsonFieldType.NUMBER).description("????????? ???"),
            fieldWithPath("data.values[].commentCount").type(JsonFieldType.NUMBER).description("?????? ???"),

            fieldWithPath("data.values[].author").type(JsonFieldType.OBJECT).description("?????? ????????? ????????? ??????"),
            fieldWithPath("data.values[].author.userId").type(JsonFieldType.NUMBER).description("????????? ID"),
            fieldWithPath("data.values[].author.name").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.values[].author.course").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.values[].author.generation").type(JsonFieldType.NUMBER).description("??????"),
            fieldWithPath("data.values[].author.role").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.values[].author.profileImgUrl").type(JsonFieldType.NULL).description("????????? ?????? URL"),

            fieldWithPath("data.nextLastId").type(JsonFieldType.NUMBER).description("?????? ???????????? ?????? ????????? gatherId")
          )
        )
      );
  }

  @WithJwtAuthUser(email = "me@mail.com", role = UserRole.STUDENT)
  @Test
  void ??????_?????????_????????????() throws Exception {
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
        .header("Authorization", "JSON WEB TOKEN")
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
            parameterWithName("gatherId").description("?????? ????????? ID")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("????????????"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("????????????"),

            // ????????? ??????
            fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????? ??????"),
            fieldWithPath("data.gatherId").type(JsonFieldType.NUMBER).description("?????? ????????? ID"),
            fieldWithPath("data.status").type(JsonFieldType.STRING).description("????????? ??????"),
            fieldWithPath("data.title").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.content").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.category").type(JsonFieldType.STRING).description("????????????"),
            fieldWithPath("data.deadline").type(JsonFieldType.STRING).description("?????? ????????????"),
            fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("?????? ??????"),
            fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("?????? ??????"),
            fieldWithPath("data.applicantLimit").type(JsonFieldType.NUMBER).description("?????? ??????"),
            fieldWithPath("data.view").type(JsonFieldType.NUMBER).description("?????????"),
            fieldWithPath("data.applicantCount").type(JsonFieldType.NUMBER).description("????????? ???"),
            fieldWithPath("data.commentCount").type(JsonFieldType.NUMBER).description("?????? ???"),

            // '???'??? ????????????
            fieldWithPath("data.isApplied").type(JsonFieldType.BOOLEAN).description("?????? ?????? ?????? ?????? ??????"),

            // ????????? ????????? ??????
            fieldWithPath("data.author").type(JsonFieldType.OBJECT).description("????????? ????????? ??????"),
            fieldWithPath("data.author.userId").type(JsonFieldType.NUMBER).description("????????? ????????? ID"),
            fieldWithPath("data.author.name").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.author.course").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.author.generation").type(JsonFieldType.NUMBER).description("??????"),
            fieldWithPath("data.author.profileImgUrl").type(JsonFieldType.NULL).description("????????? ?????? URL"),
            fieldWithPath("data.author.role").type(JsonFieldType.STRING).description("??????"),

            // ????????? ?????????
            fieldWithPath("data.participants[]").type(JsonFieldType.ARRAY).description("?????? ????????? ?????????"),
            fieldWithPath("data.participants[].userId").type(JsonFieldType.NUMBER).description("????????? ID"),
            fieldWithPath("data.participants[].name").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.participants[].course").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.participants[].generation").type(JsonFieldType.NUMBER).description("??????"),
            fieldWithPath("data.participants[].profileImgUrl").type(JsonFieldType.NULL).description("????????? ?????? URL"),
            fieldWithPath("data.participants[].role").type(JsonFieldType.STRING).description("??????"),

            // ?????? ?????????
            fieldWithPath("data.comments[]").type(JsonFieldType.ARRAY).description("?????? ?????????"),
            fieldWithPath("data.comments[].commentId").type(JsonFieldType.NUMBER).description("?????? ID"),
            fieldWithPath("data.comments[].content").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.comments[].createdAt").type(JsonFieldType.STRING).description("?????? ??????"),
            fieldWithPath("data.comments[].modifiedAt").type(JsonFieldType.STRING).description("?????? ??????"),
            fieldWithPath("data.comments[].status").type(JsonFieldType.STRING).description("?????? ??????"),

            fieldWithPath("data.comments[].author").type(JsonFieldType.OBJECT).description("?????? ????????? ??????"),
            fieldWithPath("data.comments[].author.userId").type(JsonFieldType.NUMBER).description("?????? ????????? ID"),
            fieldWithPath("data.comments[].author.name").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.comments[].author.course").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.comments[].author.generation").type(JsonFieldType.NUMBER).description("??????"),
            fieldWithPath("data.comments[].author.profileImgUrl").type(JsonFieldType.NULL).description("????????? ?????? URL"),
            fieldWithPath("data.comments[].author.role").type(JsonFieldType.STRING).description("??????"),
            // ????????? ?????????
            fieldWithPath("data.comments[].children[]").type(JsonFieldType.ARRAY).description("????????? ?????????"),
            fieldWithPath("data.comments[].children[].commentId").type(JsonFieldType.NUMBER).description("????????? ID"),
            fieldWithPath("data.comments[].children[].parentId").type(JsonFieldType.NUMBER).description("?????? ?????? ID"),
            fieldWithPath("data.comments[].children[].content").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.comments[].children[].createdAt").type(JsonFieldType.STRING).description("?????? ??????"),
            fieldWithPath("data.comments[].children[].modifiedAt").type(JsonFieldType.STRING).description("?????? ??????"),

            fieldWithPath("data.comments[].children[].author").type(JsonFieldType.OBJECT).description("????????? ????????? ??????"),
            fieldWithPath("data.comments[].children[].author.userId").type(JsonFieldType.NUMBER).description("????????? ????????? ID"),
            fieldWithPath("data.comments[].children[].author.name").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.comments[].children[].author.course").type(JsonFieldType.STRING).description("??????"),
            fieldWithPath("data.comments[].children[].author.generation").type(JsonFieldType.NUMBER).description("??????"),
            fieldWithPath("data.comments[].children[].author.profileImgUrl").type(JsonFieldType.NULL).description("????????? ?????? URL"),
            fieldWithPath("data.comments[].children[].author.role").type(JsonFieldType.STRING).description("??????")
          )
        )
      );
  }

}