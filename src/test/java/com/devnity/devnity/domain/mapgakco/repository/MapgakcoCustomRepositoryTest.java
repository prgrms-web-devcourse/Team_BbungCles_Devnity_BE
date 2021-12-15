package com.devnity.devnity.domain.mapgakco.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.setting.config.TestConfig;
import com.devnity.devnity.setting.provider.MapgakcoProvider;
import com.devnity.devnity.setting.provider.UserProvider;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(TestConfig.class)
@DataJpaTest
class MapgakcoCustomRepositoryTest {

  /***
   * List<Mapgakco> findMapgakcosHostedBy(User user);
   * List<Mapgakco> findMapgakcosAppliedBy(User user);
   */

  @Autowired private MapgakcoRepository mapgakcoRepository;

  @Autowired private MapgakcoApplicantRepository mapgakcoApplicantRepository;

  @Autowired private UserProvider userProvider;

  @Autowired private MapgakcoProvider mapgakcoProvider;

  @DisplayName("findMapgakcosHostedBy() 테스트")
  @Test
  public void testFindMapgakcosHostedBy() throws Exception {
    // given
    User user = userProvider.createUser();

    int size = 5;
    for (int i = 0; i < size; i++) {
      mapgakcoProvider.createMapgakco(user);
    }

    Mapgakco mapgakco = mapgakcoProvider.createMapgakco(user);
    mapgakco.delete();

    // when
    List<Mapgakco> result = mapgakcoRepository.findMapgakcosHostedBy(user);

    // then
    assertThat(result).hasSize(size);
  }

  @DisplayName("findMapgakcosAppliedBy() 테스트")
  @Test
  public void testFindMapgakcosAppliedBy() throws Exception {
    // given
    User host = userProvider.createUser("host@gmail.com");
    User applicant = userProvider.createUser("applicant@gmail.com");

    int size = 5;
    for (int i = 0; i < size; i++) {
      Mapgakco mapgakco = mapgakcoProvider.createMapgakco(host);
      MapgakcoApplicant ma = MapgakcoApplicant.builder()
        .mapgakco(mapgakco)
        .user(applicant)
        .build();

      mapgakcoApplicantRepository.save(ma);
    }

    Mapgakco mapgakco = mapgakcoProvider.createMapgakco(host);
    MapgakcoApplicant ma = MapgakcoApplicant.builder()
      .mapgakco(mapgakco)
      .user(applicant)
      .build();

    mapgakcoApplicantRepository.save(ma);
    mapgakco.delete();

    // when
    List<Mapgakco> result1 = mapgakcoRepository.findMapgakcosAppliedBy(host);
    List<Mapgakco> result2 = mapgakcoRepository.findMapgakcosAppliedBy(applicant);

    // then
    assertThat(result1).isEmpty();
    assertThat(result2).hasSize(size);
  }
}
