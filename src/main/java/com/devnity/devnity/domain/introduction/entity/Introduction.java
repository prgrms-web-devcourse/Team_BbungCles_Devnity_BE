package com.devnity.devnity.domain.introduction.entity;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

import com.devnity.devnity.domain.base.BaseEntity;
import com.devnity.devnity.domain.user.entity.Mbti;
import com.devnity.devnity.domain.user.entity.User;
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

  @Column(name = "blog_url", length = 300)
  private String blogUrl;

  @Column(name = "github_url", length = 300)
  private String githubUrl;

  @Column(name = "summary", length = 100)
  private String summary;

  private Double latitude;
  private Double longitude;

  @OneToOne(mappedBy = "introduction")
  private User user;

  @Lob
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private IntroductionStatus status;

  public Introduction(User user) {
    this.user = user;
    this.status = IntroductionStatus.POSTED;
  }

  @Builder
  public Introduction(String profileImgUrl, Mbti mbti, String blogUrl, String githubUrl,
      String summary, Double latitude, Double longitude, String description) {
    this.profileImgUrl = profileImgUrl;
    this.mbti = mbti;
    this.blogUrl = blogUrl;
    this.githubUrl = githubUrl;
    this.summary = summary;
    this.latitude = latitude;
    this.longitude = longitude;
    this.description = description;
    this.status = IntroductionStatus.POSTED;
  }

  public void update(Introduction update) {
    this.profileImgUrl = update.profileImgUrl;
    this.mbti = update.mbti;
    this.blogUrl = update.blogUrl;
    this.githubUrl = update.githubUrl;
    this.summary = update.summary;
    this.latitude = update.latitude;
    this.longitude = update.longitude;
    this.description = update.description;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Introduction{");
    sb.append("id=").append(id);
    sb.append(", profileImgUrl='").append(profileImgUrl).append('\'');
    sb.append(", mbti=").append(mbti);
    sb.append(", blogUrl='").append(blogUrl).append('\'');
    sb.append(", githubUrl='").append(githubUrl).append('\'');
    sb.append(", summary='").append(summary).append('\'');
    sb.append(", latitude=").append(latitude);
    sb.append(", longitude=").append(longitude);
    sb.append(", user.email=").append(user.getEmail());
    sb.append(", user.name=").append(user.getName());
    sb.append(", content='").append(description).append('\'');
    sb.append(", status=").append(status);
    sb.append('}');
    return sb.toString();
  }
}
