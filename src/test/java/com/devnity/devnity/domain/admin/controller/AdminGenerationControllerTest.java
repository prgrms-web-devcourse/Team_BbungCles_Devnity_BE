package com.devnity.devnity.domain.admin.controller;

import com.devnity.devnity.domain.admin.controller.dto.GenerationRequest;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.setting.provider.TestHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
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

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminGenerationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    GenerationRepository generationRepository;

    @Autowired
    EntityManager em;

    @Autowired
    TestHelper testHelper;

    @BeforeAll
    public void setup() {
        generationRepository.deleteAll();
    }

    @AfterAll
    public void tearDown(){
        testHelper.clean();
    }

    @Test
    @Order(1)
    @DisplayName("기수 생성 테스트")
    void testCreateCourse() throws Exception {
        var dto = new GenerationRequest(1);

        mockMvc.perform(post("/api/v1/admin/generations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("admin/generation/create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NULL).description("기수 아이디"),
                                fieldWithPath("sequence").type(JsonFieldType.NUMBER).description("기수")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버응답시간")
                        )));

        var generation = generationRepository.findById(1L);
        assertThat(generation.isEmpty()).isFalse();
        assertThat(generation.get().getSequence()).isEqualTo(dto.getSequence());
    }

    @Test
    @Order(2)
    @DisplayName("기수 조회 테스트")
    void testGetCourses() throws Exception {
        mockMvc.perform(get("/api/v1/admin/generations"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("admin/generation/get", preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.generations").type(JsonFieldType.ARRAY).description("기수들"),
                                fieldWithPath("data.generations[0].generationId").type(JsonFieldType.NUMBER).description("기수 아이디"),
                                fieldWithPath("data.generations[0].sequence").type(JsonFieldType.NUMBER).description("기수"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버응답시간")
                        )));

        var generations = generationRepository.findAll();
        assertThat(generations.isEmpty()).isFalse();
    }

    @Test
    @Order(3)
    @DisplayName("기수 수정 테스트")
    void testUpdateCourse() throws Exception {
        var id = 1L;
        var dto = new GenerationRequest(2);

        mockMvc.perform(put("/api/v1/admin/generations/{generationId}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("admin/generation/update", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("generationId").description("기수 아이디")),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NULL).description("기수 아이디"),
                                fieldWithPath("sequence").type(JsonFieldType.NUMBER).description("기수")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버응답시간")
                        )));

        var generation = generationRepository.findById(id);
        assertThat(generation.isEmpty()).isFalse();
        assertThat(generation.get().getSequence()).isEqualTo(dto.getSequence());
    }

    @Test
    @Order(4)
    @DisplayName("기수 삭제 테스트")
    void testDeleteCourse() throws Exception {
        var id = 1L;
        var generationOptional = generationRepository.findById(id);
        assertThat(generationOptional.isPresent()).isTrue();
        var generation = generationOptional.get();

        mockMvc.perform(delete("/api/v1/admin/generations/{generationId}", generation.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("admin/generation/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("generationId").description("기수 아이디")),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버응답시간")

                        )));

        var generations = generationRepository.findById(id);
        assertThat(generations.isEmpty()).isTrue();
    }

}