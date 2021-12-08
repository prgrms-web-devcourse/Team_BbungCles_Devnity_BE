package com.devnity.devnity.domain.gather.entity;

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

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "gather")
public class Gather {
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
  private Integer view;

  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  private GatherCategory category;

  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  private GatherStatus status;

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
  }

}
