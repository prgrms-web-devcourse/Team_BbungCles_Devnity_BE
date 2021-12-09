package com.devnity.devnity.domain.introduction.entity;

import com.devnity.devnity.common.entity.BaseEntity;
import com.devnity.devnity.domain.user.entity.Mbti;
import com.devnity.devnity.domain.user.entity.User;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "introduction")
public class Introduction extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String profileImgUrl;

  @Enumerated(EnumType.STRING)
  private Mbti mbti;

  @Column(name = "blog_url", length = 100)
  private String blogUrl;

  @Column(name = "github_url", length = 100)
  private String githubUrl;

  @Column(name = "summary", length = 200)
  private String summary;

  private Double latitude;
  private Double longitude;

  @OneToOne(fetch = FetchType.LAZY)
  private User user;

  @Lob @Basic(fetch = FetchType.LAZY)
  private String content;

  @Enumerated(EnumType.STRING)
  private IntroductionStatus status;

  public Introduction(User user) {
    this.user = user;
    this.status = IntroductionStatus.POSTED;
  }

  @Builder
  public Introduction(String profileImgUrl, Mbti mbti, String blogUrl, String githubUrl,
      String summary, Double latitude, Double longitude, String content,
      IntroductionStatus status) {
    this.profileImgUrl = profileImgUrl;
    this.mbti = mbti;
    this.blogUrl = blogUrl;
    this.githubUrl = githubUrl;
    this.summary = summary;
    this.latitude = latitude;
    this.longitude = longitude;
    this.content = content;
    this.status = status;
  }

  public void update(Introduction update) {
    this.profileImgUrl = update.profileImgUrl;
    this.mbti = update.mbti;
    this.blogUrl = update.blogUrl;
    this.githubUrl = update.githubUrl;
    this.summary = update.summary;
    this.latitude = update.latitude;
    this.longitude = update.longitude;
    this.content = update.content;
  }
}
