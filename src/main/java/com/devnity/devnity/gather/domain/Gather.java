package com.devnity.devnity.gather.domain;

import com.devnity.devnity.user.domain.User;
import java.time.LocalDateTime;
import javax.persistence.Basic;
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

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "gather_post")
public class Gather {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String title;

  @Lob @Column(nullable = false)
  @Basic(fetch = FetchType.LAZY)
  private String content;

  @Column(nullable = false, name = "applicant_limit")
  private int applicantLimit;

  @Column(nullable = false)
  private LocalDateTime deadline;

  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  private GatherCategory category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  private GatherStatus status;

  private int view;

  @Builder
  public Gather(String title, String content, int applicantLimit, LocalDateTime deadline,
      GatherCategory category, User user) {
    this.title = title;
    this.content = content;
    this.applicantLimit = applicantLimit;
    this.deadline = deadline;
    this.category = category;
    this.user = user;
    this.status = GatherStatus.GATHERING;
    this.view = 0;
  }
}
