package com.devnity.devnity.domain.admin.controller;

import com.devnity.devnity.domain.admin.controller.dto.InvitationRequest;
import com.devnity.devnity.domain.admin.entity.Invitation;
import com.devnity.devnity.domain.admin.repository.InvitationRepository;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(roles = "ADMIN")
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
class AdminInvitationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    InvitationRepository invitationRepository;

    @Test
    @DisplayName("초대링크 정보 생성 테스트")
    void testCreateCourse() throws Exception {
        var dto = new InvitationRequest("backend", 1, "STUDENT");

        mockMvc.perform(post("/api/v1/admin/link")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("admin/link/create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("course").type(JsonFieldType.STRING).description("코스명"),
                                fieldWithPath("generation").type(JsonFieldType.NUMBER).description("기수"),
                                fieldWithPath("role").type(JsonFieldType.STRING).description("역할")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.uuid").type(JsonFieldType.STRING).description("UUID"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버응답시간")
                        )));
    }

    @Test
    @DisplayName("초대링크 조회 테스트")
    void testGetCourses() throws Exception {
        var invitation = new Invitation("backend", 1, UserRole.STUDENT);
        invitationRepository.save(invitation);

        mockMvc.perform(get("/api/v1/admin/link/{uuid}", invitation.getUuid()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("admin/link/get",
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("uuid").description("UUID")),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.course").type(JsonFieldType.STRING).description("코스"),
                                fieldWithPath("data.generation").type(JsonFieldType.NUMBER).description("기수"),
                                fieldWithPath("data.role").type(JsonFieldType.STRING).description("역할"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버응답시간")
                        )));
    }

}