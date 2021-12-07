package com.devnity.devnity.domain.admin.controller;

import com.devnity.devnity.domain.admin.controller.dto.CourseRequestDto;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.repository.CourseRepository;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminCourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    EntityManager em;

    @BeforeAll
    public void setup() {
        courseRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("코스 생성 테스트")
    void testCreateCourse() throws Exception {
        var dto = new CourseRequestDto("backend");

        mockMvc.perform(post("/api/v1/admin/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("create course", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NULL).description("코스 아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("코스 이름")
                        )));

        var course = courseRepository.findById(1L);
        assertThat(course.isEmpty()).isFalse();
        assertThat(course.get().getName()).isEqualTo(dto.getName());
    }

    @Test
    @Order(2)
    @DisplayName("코스 조회 테스트")
    void testGetCourses() throws Exception {
        Course course = new Course("test");
        courseRepository.save(course);

        mockMvc.perform(get("/api/v1/admin/courses"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get courses", preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("courses").type(JsonFieldType.ARRAY).description("코스들"),
                                fieldWithPath("courses[0].id").type(JsonFieldType.NUMBER).description("코스 아이디"),
                                fieldWithPath("courses[0].name").type(JsonFieldType.STRING).description("코스 이름")
                        )));

        var courses = courseRepository.findAll();
        assertThat(courses.isEmpty()).isFalse();
    }

    @Test
    @Order(3)
    @DisplayName("코스 업데이트 테스트")
    void testUpdateCourse() throws Exception {
        var id = 1L;
        var dto = new CourseRequestDto(id, "nameAfter");

        mockMvc.perform(put("/api/v1/admin/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("update course", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("코스 아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("코스 이름")
                        )));

        var course = courseRepository.findById(id);
        assertThat(course.isEmpty()).isFalse();
        assertThat(course.get().getName()).isEqualTo("nameAfter");
    }

    @Test
    @Order(4)
    @DisplayName("코스 삭제 테스트")
    void testDeleteCourse() throws Exception {
        var id = 1L;
        var course = courseRepository.findById(id).get();

        mockMvc.perform(delete("/api/v1/admin/courses/{courseId}", course.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("delete course",
                        pathParameters(parameterWithName("courseId").description("course id"))
                ));

        var courses = courseRepository.findById(id);
        assertThat(courses.isEmpty()).isTrue();
    }

}