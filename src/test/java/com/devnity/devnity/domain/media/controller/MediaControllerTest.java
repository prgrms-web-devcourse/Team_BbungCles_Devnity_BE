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

import com.devnity.devnity.setting.config.MockAwsS3UploaderConfig;
import com.devnity.devnity.setting.provider.MediaProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
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

  @Autowired
  MediaProvider mediaProvider;

  @Test
  public void ??????????????????_URL???_????????????() throws Exception {
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
            partWithName("media").description("????????? ??????")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("????????????"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("????????????"),
            fieldWithPath("data.mediaUrl").type(JsonFieldType.STRING)
              .description("???????????? CloudFront url")
          )
        )
      );
  }

  @Test
  public void ??????????????????_?????????_URL???_????????????() throws Exception {
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
            partWithName("profileImage").description("????????? ????????? ??????")
          ),
          responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("????????????"),
            fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("????????????"),
            fieldWithPath("data.imageUrl").type(JsonFieldType.STRING)
              .description("???????????? CloudFront url")
          )
        )
      );
  }

}