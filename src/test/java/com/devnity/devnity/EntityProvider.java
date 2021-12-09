package com.devnity.devnity;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherApplicant;
import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.user.entity.User;
import java.time.LocalDateTime;

public class EntityProvider {

// -------------------------------------- ( User ) --------------------------------------

//  public static void createUser() {
//    User user = User.builder()
//      .course(new Course("백엔드"))
//      .email("test@mail.com")
//      .generation(new Generation(1))
//      .group(new Group("USER_GROUP"))
//      .password("00000000")
//      .build();
//  }

// -------------------------------------- ( Mapgakco ) --------------------------------------




// -------------------------------------- ( Gather ) --------------------------------------

  // 모집 게시글 하나만 생성해주는 메소드
  public static Gather createGather(User user) {
    return Gather.builder()
      .title("제목 : 스터디 모집해용")
      .content("## 내용 (마크다운)")
      .applicantLimit(5)
      .deadline(LocalDateTime.now().plusDays(2))
      .category(GatherCategory.STUDY)
      .user(user)
      .build();
  }

//  // FIXME : 댓글과 신청자까지 모두 넣어 게시글 생성해주는 메소드
//  public static Gather createGather(User postWriter, User commentWriter) {
//
//  }

  public static GatherComment createGatherComment(Gather gather, User user) {
    return GatherComment.builder()
      .content("모집 댓글 : 스터디 하고싶다~")
      .gather(gather)
      .user(user)
      .build();
  }

  public static GatherComment createGatherCommentChild(Gather gather, GatherComment parent, User user) {
    return GatherComment.builder()
      .content("모집 대댓글 : 코테 스터디가 최고야. 짜릿해.")
      .gather(gather)
      .parent(parent)
      .user(user)
      .build();
  }

  public static GatherApplicant createGatherApplicant(Gather gather, User user) {
    return GatherApplicant.builder()
      .gather(gather)
      .user(user)
      .build();
  }



}
