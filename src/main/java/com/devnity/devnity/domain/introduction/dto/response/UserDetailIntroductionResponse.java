package com.devnity.devnity.domain.introduction.dto.response;

import com.devnity.devnity.domain.introduction.dto.IntroductionCommentDto;
import com.devnity.devnity.domain.introduction.dto.IntroductionDto;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.user.dto.UserDto;
import com.devnity.devnity.domain.user.entity.User;
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
