package com.devnity.devnity.domain.mapgakco.repository.mapgakcocomment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoComment;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoCommentStatus;
import com.devnity.devnity.domain.mapgakco.repository.mapgakco.MapgakcoRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.setting.provider.MapgakcoProvider;
import com.devnity.devnity.setting.provider.TestHelper;
import com.devnity.devnity.setting.provider.UserProvider;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MapgakcoCommentRepositoryTest {

  @Autowired
  MapgakcoCommentRepository commentRepository;
  @Autowired
  MapgakcoRepository mapgakcoRepository;
  @Autowired
  MapgakcoProvider mapgakcoProvider;
  @Autowired
  UserProvider userProvider;
  @Autowired
  TestHelper testHelper;
  @Autowired
  EntityManager em;

  private Long commentId1;
  private Long commentId2;
  private Long mapgakcoId;

  @BeforeEach
  public void setUp() {
    User user = userProvider.createUser();
    Mapgakco mapgakco = mapgakcoProvider.createMapgakco(user);
    MapgakcoComment comment1 = mapgakcoProvider.createComment(user, mapgakco, null);
    mapgakcoProvider.createComment(user, mapgakco, comment1);
    mapgakcoProvider.createComment(user, mapgakco, comment1);

    MapgakcoComment comment2 = mapgakcoProvider.createComment(user, mapgakco, null);
    mapgakcoProvider.createComment(user, mapgakco, comment2);
    mapgakcoProvider.createComment(user, mapgakco, comment2);
    mapgakcoProvider.createComment(user, mapgakco, comment2);
    mapgakcoProvider.createComment(user, mapgakco, comment2);

    commentId1 = comment1.getId();
    commentId2 = comment2.getId();
    mapgakcoId = mapgakco.getId();

    em.flush();
    em.clear();
  }

  @AfterEach
  void tearDown() {
    testHelper.clean();
  }

  @Test
  @DisplayName("쿼리메소드 findByIdAndStatusNotTest가 잘 동작한다.")
  void findByIdAndStatusNotTest() {
    Optional<MapgakcoComment> foundComment = commentRepository.findByIdAndStatusNot(commentId1,
      MapgakcoCommentStatus.DELETED);
    assertNotEquals(foundComment, Optional.empty());

    MapgakcoComment comment = foundComment.get();
    comment.delete();
    em.flush();
    em.clear();

    Optional<MapgakcoComment> foundComment2 = commentRepository.findByIdAndStatusNot(commentId1,
      MapgakcoCommentStatus.DELETED);
    assertEquals(foundComment2, Optional.empty());
  }

  @Test
  @DisplayName("쿼리메소드 getByMapgakcoAndParentIsNull가 잘 동작한다.")
  void getByMapgakcoAndParentIsNullTest() {
    Optional<Mapgakco> foundMapgakco = mapgakcoRepository.findById(mapgakcoId);
    assertNotEquals(foundMapgakco, Optional.empty());
    Mapgakco mapgakco = foundMapgakco.get();

    List<MapgakcoComment> parents = commentRepository.getByMapgakcoAndParentIsNull(mapgakco);
    assertEquals(parents.size(), 2);
  }

  @Test
  @DisplayName("쿼리메소드 getByParent가 잘 동작한다.")
  void getByParentTest() {
    Optional<MapgakcoComment> foundComment1 = commentRepository.findById(commentId1);
    assertNotEquals(foundComment1, Optional.empty());
    MapgakcoComment comment1 = foundComment1.get();

    Optional<MapgakcoComment> foundComment2 = commentRepository.findById(commentId2);
    assertNotEquals(foundComment2, Optional.empty());
    MapgakcoComment comment2 = foundComment2.get();

    List<MapgakcoComment> children1 = commentRepository.getByParent(comment1);
    List<MapgakcoComment> children2 = commentRepository.getByParent(comment2);

    assertEquals(children1.size(), 2);
    assertEquals(children2.size(), 4);

  }

}