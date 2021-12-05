package com.devnity.devnity.domain.auth.entity;

import com.devnity.devnity.domain.user.entity.User;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "refresh_token")
public class RefreshToken {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  private String token;

  private Date expiryDate;

  @Builder
  public RefreshToken(User user, String token, Date expiryDate) {
    this.user = user;
    this.token = token;
    this.expiryDate = expiryDate;
  }

  public boolean isExpired(Date date) {
    return this.getExpiryDate().after(date);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("RefreshToken{");
    sb.append("id=").append(id);
    sb.append(", user=").append(user);
    sb.append(", token='").append(token).append('\'');
    sb.append(", expiryDate=").append(expiryDate);
    sb.append('}');
    return sb.toString();
  }
}
