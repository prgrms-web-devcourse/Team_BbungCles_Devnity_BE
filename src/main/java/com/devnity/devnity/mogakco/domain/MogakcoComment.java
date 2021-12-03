package com.devnity.devnity.mogakco.domain;

import com.devnity.devnity.user.domain.User;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "mogakco_comment")
public class MogakcoComment {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 200)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mogakco_id")
  private Mogakco mogakco;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private MogakcoComment parent;

  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  private MogakcoCommentStatus status;

  public MogakcoComment(String content, User user, Mogakco mogakco,
      MogakcoComment parent) {
    this.content = content;
    this.user = user;
    this.mogakco = mogakco;
    this.parent = parent;
    this.status = MogakcoCommentStatus.POSTED;
  }
}
