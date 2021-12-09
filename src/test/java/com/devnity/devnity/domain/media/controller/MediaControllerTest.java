package com.devnity.devnity.domain.media.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devnity.devnity.test.config.MockAwsS3UploaderConfig;
import com.devnity.devnity.test.provider.MediaProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Import(MockAwsS3UploaderConfig.class)
@WithMockUser(roles = "USER")
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class MediaControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;

  //  @Autowired
//  private ResourceLoader resourceLoader;
  @Autowired
  MediaProvider mediaProvider;

  @Test
  public void 미디어파일을_URL로_변환한다() throws Exception {
    // Given
    ResultActions resultActions = mockMvc.perform(
      multipart("/api/v1/media")
        .file(mediaProvider.createMockFile("media"))
        .contentType(MediaType.MULTIPART_FORM_DATA)
    );

    // When + Then
    resultActions
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "media/file-upload", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          requestParts(
            partWithName("media").description("미디어 파일")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버시간"),
            fieldWithPath("data.mediaUrl").type(JsonFieldType.STRING)
              .description("업로드된 CloudFront url")
          )
        )
      );
  }

  @Test
  public void 프로필이미지_파일을_URL로_변환한다() throws Exception {
    // Given
    ResultActions resultActions = mockMvc.perform(
      multipart("/api/v1/media/image/profile")
        .file(mediaProvider.createMockFile("profileImage"))
        .contentType(MediaType.MULTIPART_FORM_DATA)
    );

    // When + Then
    resultActions
      .andExpect(status().isOk())
      .andDo(print())
      .andDo(
        document(
          "media/image-upload", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
          requestParts(
            partWithName("profileImage").description("프로필 이미지 파일")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버시간"),
            fieldWithPath("data.imageUrl").type(JsonFieldType.STRING)
              .description("업로드된 CloudFront url")
          )
        )
      );
  }

}