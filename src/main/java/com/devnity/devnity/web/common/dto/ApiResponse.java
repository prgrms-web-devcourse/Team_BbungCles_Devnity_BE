package com.devnity.devnity.web.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {

  private int statusCode;
  private T data;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime serverDatetime;

  public ApiResponse(final int statusCode, final T data) {
    this.statusCode = statusCode;
    this.data = data;
    this.serverDatetime = LocalDateTime.now();
  }

  public static <T> ApiResponse<T> ok(final T data) {
    return new ApiResponse<>(200, data);
  }

  public static <T> ApiResponse<String> ok() {
    return new ApiResponse<>(200, "success");
  }

}