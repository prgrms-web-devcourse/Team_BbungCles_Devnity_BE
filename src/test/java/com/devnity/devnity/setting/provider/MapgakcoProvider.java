package com.devnity.devnity.setting.provider;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoComment;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoApplicantRepository;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoCommentRepository;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoRepository;
import com.devnity.devnity.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Component
public class MapgakcoProvider {

  private final MapgakcoRepository mapgakcoRepository;
  private final MapgakcoCommentRepository commentRepository;
  private final MapgakcoApplicantRepository applicantRepository;

  public Mapgakco createMapgakco(User user) {
    return mapgakcoRepository.save(
      Mapgakco.builder()
        .title("맵각코")
        .applicantLimit(5)
        .deadline(LocalDateTime.now())
        .content("맵각코 내용")
        .location("맵각코 위치")
        .latitude(12.5)
        .longitude(12.5)
        .meetingAt(LocalDateTime.now())
        .user(user)
        .build()
    );
  }

  public MapgakcoComment createComment(User user, Mapgakco mapgakco, MapgakcoComment parent) {
    return commentRepository.save(
      MapgakcoComment.builder()
        .content("부모댓글")
        .user(user)
        .mapgakco(mapgakco)
        .parent(parent)
        .build()
    );
  }

  public MapgakcoApplicant createApplicant(User user, Mapgakco mapgakco) {
    return applicantRepository.save(
      MapgakcoApplicant.builder()
        .user(user)
        .mapgakco(mapgakco)
        .build()
    );
  }

}
