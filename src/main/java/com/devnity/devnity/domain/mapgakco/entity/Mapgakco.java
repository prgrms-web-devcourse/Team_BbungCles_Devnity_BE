package com.devnity.devnity.domain.mapgakco.entity;

import com.devnity.devnity.domain.base.BaseEntity;
import com.devnity.devnity.domain.user.entity.User;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "mapgakco")
public class Mapgakco extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String title;

  @Lob
  @Column(nullable = false)
  private String content;

  @Column(nullable = false, length = 100)
  private String location;

  @Column(nullable = false, name = "applicant_limit")
  private int applicantLimit;

  @Column(nullable = false, name = "applicant_count")
  private int applicantCount;

  @Column(nullable = false)
  private Double latitude;
  @Column(nullable = false)
  private Double longitude;

  @Column(name = "meeting_at", nullable = false)
  private LocalDateTime meetingAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  private MapgakcoStatus status;

  private Integer view;

  @Builder
  public Mapgakco(String title, Integer applicantLimit, String content, String location,
    Double latitude, Double longitude, LocalDateTime meetingAt, User user
  ) {
    this.title = title;
    this.applicantLimit = applicantLimit;
    this.content = content;
    this.location = location;
    this.latitude = latitude;
    this.longitude = longitude;
    this.meetingAt = meetingAt;
    this.user = user;
    this.status = MapgakcoStatus.GATHERING;
    this.applicantCount = 1;
    this.view = 0;
  }

  public Mapgakco update(String title, Integer applicantLimit, String content, String location,
    Double northEastX, Double northEastY, Double southWestX, Double southWestY, LocalDateTime meetingAt
  ) {
    this.title = title;
    this.applicantLimit = applicantLimit;
    this.content = content;
    this.location = location;
    this.latitude = latitude;
    this.longitude = longitude;
    this.meetingAt = meetingAt;
    return this;
  }

  public void delete() {
    this.status = MapgakcoStatus.DELETED;
  }

  public void addApplicant() {
    this.applicantCount += 1;
    if (applicantCount >= applicantLimit) {
      this.status = MapgakcoStatus.FULL;
    }
  }

  public void subApplicant() {
    this.applicantCount -= 1;
    if (MapgakcoStatus.FULL.equals(this.status) && applicantCount < applicantLimit) {
      this.status = MapgakcoStatus.GATHERING;
    }
  }
}
