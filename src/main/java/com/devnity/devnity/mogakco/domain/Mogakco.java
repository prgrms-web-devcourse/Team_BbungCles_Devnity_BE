package com.devnity.devnity.mogakco.domain;

import com.devnity.devnity.user.domain.User;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "mogakco_post")
public class Mogakco {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String title;

  @Column(nullable = false, length = 255)
  private String content;

  @Column(nullable = false, length = 100)
  private String location;

  @Column(nullable = false, name = "applicant_limit")
  private int applicantLimit;

  @Column(nullable = false)
  private LocalDateTime deadline;

  @Column(nullable = false)
  private double latitude;

  @Column(nullable = false)
  private double longitude;

  @Column(name = "meeting_date_time", nullable = false)
  private LocalDateTime meetingAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  private MogakcoStatus status;

  private int view;

  @Builder
  public Mogakco(String title, String content, String location, int applicantLimit,
      LocalDateTime deadline, double latitude, double longitude, LocalDateTime meetingAt,
      User user) {
    this.title = title;
    this.content = content;
    this.location = location;
    this.applicantLimit = applicantLimit;
    this.deadline = deadline;
    this.latitude = latitude;
    this.longitude = longitude;
    this.meetingAt = meetingAt;
    this.user = user;
    this.status = MogakcoStatus.GATHERING;
    this.view = 0;
  }
}
