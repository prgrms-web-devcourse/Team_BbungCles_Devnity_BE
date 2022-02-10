package com.devnity.devnity.web.introduction.dto.response;

import com.devnity.devnity.web.introduction.dto.IntroductionCommentDto;
import com.devnity.devnity.web.introduction.dto.IntroductionDto;
import com.devnity.devnity.web.user.dto.UserDto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserDetailIntroductionResponse {

  private UserDto user;
  private IntroductionDto introduction;
  private List<IntroductionCommentDto> comments;
  private boolean isLiked;

  @Builder
  public UserDetailIntroductionResponse(UserDto user,
    IntroductionDto introduction,
    List<IntroductionCommentDto> comments, boolean isLiked) {
    this.user = user;
    this.introduction = introduction;
    this.comments = comments;
    this.isLiked = isLiked;
  }

  public static UserDetailIntroductionResponse of(
      UserDto user,
      IntroductionDto introduction,
      List<IntroductionCommentDto> comments,
      boolean isLiked) {
    return UserDetailIntroductionResponse.builder()
      .user(user)
      .comments(comments)
      .introduction(introduction)
      .isLiked(isLiked)
      .build();

  }
}
