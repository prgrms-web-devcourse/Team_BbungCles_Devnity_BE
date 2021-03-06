package com.devnity.devnity.domain.admin.controller;

import com.devnity.devnity.web.admin.dto.request.CourseRequest;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.setting.provider.AdminProvider;
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
class AdminCourseControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  TestHelper testHelper;
  @Autowired
  AdminProvider adminProvider;

  @AfterEach
  public void tearDown() {
    testHelper.clean();
  }

  @Test
  @DisplayName("?????? ?????? ?????????")
  void testCreateCourse() throws Exception {
    // Given
    var dto = new CourseRequest("BE");

    // When + Then
    mockMvc.perform(post("/api/v1/admin/courses")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto))
      )
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(document("admin/course/create", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
        requestFields(
          fieldWithPath("id").type(JsonFieldType.NULL).description("?????? ?????????"),
          fieldWithPath("name").type(JsonFieldType.STRING).description("?????? ??????")
        ),
        responseFields(
          fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("????????????"),
          fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("?????????"),
          fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("??????????????????")
        )));
  }

  @Test
  @DisplayName("?????? ?????? ?????????")
  void testGetCourses() throws Exception {
    // Given
    adminProvider.createCourse("BE");

    // When + Then
    mockMvc.perform(get("/api/v1/admin/courses"))
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(document("admin/course/get", preprocessResponse(prettyPrint()),
        responseFields(
          fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("????????????"),
          fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????????"),
          fieldWithPath("data.courses").type(JsonFieldType.ARRAY).description("?????????"),
          fieldWithPath("data.courses[0].id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
          fieldWithPath("data.courses[0].name").type(JsonFieldType.STRING).description("?????? ??????"),
          fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("??????????????????")
        )));
  }

  @Test
  @DisplayName("?????? ?????? ?????????")
  void testUpdateCourse() throws Exception {
    // Given
    Course course = adminProvider.createCourse("BE");
    Long id = course.getId();
    var dto = new CourseRequest(id, "??????????????????");

    // When + Then
    mockMvc.perform(put("/api/v1/admin/courses/{courseId}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto))
      )
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(document("admin/course/update", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
        pathParameters(parameterWithName("courseId").description("?????? ?????????")),
        requestFields(
          fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
          fieldWithPath("name").type(JsonFieldType.STRING).description("?????? ??????")
        ),
        responseFields(
          fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("????????????"),
          fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("?????????"),
          fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("??????????????????")
        )));
  }

  @Test
  @DisplayName("?????? ?????? ?????????")
  void testDeleteCourse() throws Exception {
    // Given
    Course course = adminProvider.createCourse("BE");

    // When + Then
    mockMvc.perform(delete("/api/v1/admin/courses/{courseId}", course.getId()))
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(document("admin/course/delete", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
        pathParameters(parameterWithName("courseId").description("?????? ?????????")),
        responseFields(
          fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("????????????"),
          fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("?????????"),
          fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("??????????????????")

        )));
  }

}