package com.devnity.devnity.setting.provider;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.repository.GatherCommentRepository;
import com.devnity.devnity.domain.gather.repository.GatherRepository;
import com.devnity.devnity.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GatherProvider {

  private final GatherRepository gatherRepository;
  private final GatherCommentRepository gatherCommentRepository;

  public Gather createGather(User user){
    return gatherRepository.save(
      Gather.builder()
        .user(user)
        .title("제목제목제목")
        .content("내용내용내용(마크다운)")
        .applicantLimit(5)
        .deadline(LocalDateTime.now())
        .category(GatherCategory.STUDY)
        .build()
    );
  }

  public GatherComment createComment(User user, Gather gather){
    return gatherCommentRepository.save(
      GatherComment.builder()
        .content("부모댓글")
        .user(user)
        .gather(gather)
        .build()
    );
  }

}
