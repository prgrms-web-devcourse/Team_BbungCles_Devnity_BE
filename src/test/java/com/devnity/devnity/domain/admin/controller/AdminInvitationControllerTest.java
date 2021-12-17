package com.devnity.devnity.domain.admin.controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devnity.devnity.domain.admin.dto.request.InvitationRequest;
import com.devnity.devnity.domain.admin.entity.Invitation;
import com.devnity.devnity.domain.admin.repository.InvitationRepository;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.setting.annotation.WithJwtAuthUser;
import com.devnity.devnity.setting.provider.TestHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser(roles = "ADMIN")
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class AdminInvitationControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  InvitationRepository invitationRepository;

  @Autowired
  TestHelper testHelper;

  @AfterEach
  void tearDown() {
    testHelper.clean();
  }

  //  @WithJwtAuthUser(email = "me_admin@mail.com", role = UserRole.MANAGER)
  @Test
  @DisplayName("초대링크 정보 생성 테스트")
  void testCreateLink() throws Exception {
    // Given
    InvitationRequest request = InvitationRequest.builder()
      .course("BE")
      .generation(1)
      .role(UserRole.STUDENT)
      .deadline(LocalDate.now())
      .build();

    // When + Then
    mockMvc.perform(post("/api/v1/admin/links")
        .header("Authorization", "JSON WEB TOKEN")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
      )
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(document("admin/links/create", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          requestFields(
            fieldWithPath("course").type(JsonFieldType.STRING).description("코스명"),
            fieldWithPath("generation").type(JsonFieldType.NUMBER).description("기수"),
            fieldWithPath("role").type(JsonFieldType.STRING).description("역할"),
            fieldWithPath("deadline").type(JsonFieldType.STRING).description("마감 날짜 (yyyy-MM-dd)")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버응답시간"),

            fieldWithPath("data.uuid").type(JsonFieldType.STRING).description("UUID")
          )
        )
      );
  }

  //  @WithJwtAuthUser(email = "me_admin@mail.com", role = UserRole.MANAGER)
  @Test
  @DisplayName("초대링크 단일 조회 테스트")
  void testGetLink() throws Exception {
    // Given
    Invitation invitation = Invitation.builder()
      .course("BE")
      .generation(1)
      .role(UserRole.STUDENT)
      .deadline(LocalDate.now())
      .build();
    invitationRepository.save(invitation);

    // When + Then
    mockMvc.perform(
        get("/api/v1/admin/links/{uuid}", invitation.getUuid())
          .header("Authorization", "JSON WEB TOKEN")
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "admin/links/get-one", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          pathParameters(
            parameterWithName("uuid").description("UUID")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버응답시간"),

            fieldWithPath("data.course").type(JsonFieldType.STRING).description("코스"),
            fieldWithPath("data.generation").type(JsonFieldType.NUMBER).description("기수"),
            fieldWithPath("data.role").type(JsonFieldType.STRING).description("역할")
          )
        )
      );
  }

  //  @WithJwtAuthUser(email = "me_admin@mail.com", role = UserRole.MANAGER)
  @Test
  @DisplayName("초대링크 전체 조회 테스트")
  void testGetLinks() throws Exception {
    // Given
    invitationRepository.saveAll(
      List.of(
        Invitation.builder()
          .course("BE")
          .generation(1)
          .role(UserRole.STUDENT)
          .deadline(LocalDate.now())
          .build(),
        Invitation.builder()
          .course("FE")
          .generation(1)
          .role(UserRole.MENTOR)
          .deadline(LocalDate.now())
          .build()
      )
    );

    // When + Then
    mockMvc.perform(
        get("/api/v1/admin/links")
          .header("Authorization", "JSON WEB TOKEN")
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "admin/links/get-all", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버응답시간"),

            fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("초대 링크 리스트"),
            fieldWithPath("data[].uuid").type(JsonFieldType.STRING).description("링크 uuid"),
            fieldWithPath("data[].course").type(JsonFieldType.STRING).description("코스"),
            fieldWithPath("data[].generation").type(JsonFieldType.NUMBER).description("기수"),
            fieldWithPath("data[].role").type(JsonFieldType.STRING).description("역할"),
            fieldWithPath("data[].deadline").type(JsonFieldType.STRING).description("마감 날짜")
          )
        )
      );
  }

}