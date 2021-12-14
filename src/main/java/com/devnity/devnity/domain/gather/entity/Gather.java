package com.devnity.devnity.domain.gather.entity;

import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.domain.base.BaseEntity;
import com.devnity.devnity.domain.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.user.entity.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "gather")
public class Gather extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String title;

  @Lob
  @Column(nullable = false)
  @Basic(fetch = FetchType.LAZY)
  private String content;

  @Column(name = "applicant_limit", nullable = false)
  private Integer applicantLimit;

  @Column(nullable = false)
  private LocalDateTime deadline;

  @Column(nullable = false)
  private int view;

  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  private GatherCategory category;

  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  private GatherStatus status;

  @Column(name = "applicant_count", nullable = false)
  private int applicantCount;

  @Column(name = "comment_count", nullable = false)
  private int commentCount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;

  @OneToMany(mappedBy = "gather", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<GatherComment> comments = new ArrayList<>();

  @OneToMany(mappedBy = "gather", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<GatherApplicant> applicants = new ArrayList<>();

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
    this.applicantCount = 0;
    this.commentCount = 0;
  }

// ---------------------------- ( 연관관계 편의 메소드 ) ----------------------------

  public void addComment(GatherComment comment) {
    comment.setGather(this);
  }

  public void addApplicant(GatherApplicant applicant) {
    // 상태 검사
    if (!status.equals(GatherStatus.GATHERING)) {
      throw new InvalidValueException(
        String.format("Gather 상태가 모집중이 아닙니다. (gatherId : %d, status : %s)", this.id, this.status),
        ErrorCode.ALREADY_CLOSED_GATHER
      );
    }
    // 신청 추가
    applicants.add(applicant);
    // 상태 변경
    if (applicants.size() == applicantLimit) {
      this.status = GatherStatus.GATHERING;
    }
  }

  public void deleteApplicant(GatherApplicant applicant) {
    if (this.applicants.remove(applicant)) {
      this.status = GatherStatus.GATHERING;
    }
  }

// ---------------------------- ( 팩토리 메소드 ) ----------------------------

  public static Gather of(User user, CreateGatherRequest request) {
    return Gather.builder()
      .user(user)
      .title(request.getTitle())
      .applicantLimit(request.getApplicantLimit())
      .deadline(request.getDeadline())
      .content(request.getContent())
      .category(request.getCategory())
      .build();
  }

// ---------------------------- ( 비즈니스 메소드 ) ----------------------------

  public boolean isWrittenBy(User user) {
    return this.user.getId().equals(user.getId());
  }

  public Gather updateStatus(GatherStatus status){
    this.status = status;
    return this;
  }


}
