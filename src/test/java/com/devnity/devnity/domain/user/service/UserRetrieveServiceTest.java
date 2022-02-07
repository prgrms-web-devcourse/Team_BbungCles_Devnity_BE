package com.devnity.devnity.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;
import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.web.user.dto.SimpleUserInfoDto;
import com.devnity.devnity.web.user.dto.response.MyInfoResponse;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.devnity.devnity.web.user.service.UserRetrieveService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserRetrieveServiceTest {

  @InjectMocks private UserRetrieveService userRetrieveService;

  @Mock private UserRepository userRepository;
  
  @DisplayName("getUser() 성공 테스트")
  @Test 
  public void testGetUser() throws Exception {
    // given
    User user = User.builder()
      .password("password")
      .role(UserRole.STUDENT)
      .email("email@gmail.com")
      .generation(new Generation(1))
      .name("함승훈")
      .course(new Course("FE"))
      .build();

    given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

    // when
    User found = userRetrieveService.getUser(1L);

    // then
    verify(userRepository).findById(anyLong());
    assertThat(user.getEmail()).isEqualTo(found.getEmail());
  }
  
  @DisplayName("getUser() 실패 테스트")
  @Test 
  public void testGetUserFail() throws Exception {
    // given
    User user = User.builder()
      .password("password")
      .role(UserRole.STUDENT)
      .email("email@gmail.com")
      .generation(new Generation(1))
      .name("함승훈")
      .course(new Course("FE"))
      .build();

    given(userRepository.findById(anyLong())).willReturn(Optional.empty());

    // when // then
    assertThatThrownBy(() -> userRetrieveService.getUser(1L))
        .isInstanceOf(EntityNotFoundException.class);
  }
  
  @DisplayName("getAllByCourseAndGenerationLimit() 성공 테스트")
  @Test 
  public void testGetAllByCourseAndGenerationLimit() throws Exception {
    //given
    User user = User.builder()
      .password("password")
      .role(UserRole.STUDENT)
      .email("email@gmail.com")
      .generation(new Generation(1))
      .name("함승훈")
      .course(new Course("FE"))
      .build();

    List<User> users = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      User temp = User.builder()
        .password("password")
        .role(UserRole.STUDENT)
        .email("email@gmail.com")
        .generation(user.getGeneration())
        .name("함승훈" + i)
        .course(user.getCourse())
        .build();

      users.add(temp);
    }

    given(userRepository.findAllByCourseAndGenerationLimit(user, 10)).willReturn(users);
    // when
    List<User> results = userRetrieveService.getAllByCourseAndGenerationLimit(
      user, 10);

    // then
    verify(userRepository).findAllByCourseAndGenerationLimit(any(), anyInt());
    assertThat(users.size()).isEqualTo(results.size());
  }

  @DisplayName("fetchUserInfo() 성공 테스트")
  @Test 
  public void testFetchUserInfo() throws Exception {
    //given
    User user = User.builder()
      .password("password")
      .role(UserRole.STUDENT)
      .email("email@gmail.com")
      .generation(new Generation(1))
      .name("함승훈")
      .course(new Course("FE"))
      .build();

    given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

    // when
    MyInfoResponse response = userRetrieveService.getMyInfo(1L);

    // then
    assertThat(response.getUser().getEmail()).isEqualTo(user.getEmail());
  } 

  @DisplayName("fetchUserInfo() 실패 테스트")
  @Test
  public void testFetchUserInfoUserIdIsNull() throws Exception {
    //given
    User user = User.builder()
      .password("password")
      .role(UserRole.STUDENT)
      .email("email@gmail.com")
      .generation(new Generation(1))
      .name("함승훈")
      .course(new Course("FE"))
      .build();

    // when    // then
    assertThatThrownBy(() -> userRetrieveService.getMyInfo(null))
        .isInstanceOf(InvalidValueException.class);
  }

  @DisplayName("getSimpleUserInfo() 테스트")
  @Test 
  public void testSimpleUserInfo() throws Exception {
    //given
    User user = User.builder()
      .password("password")
      .role(UserRole.STUDENT)
      .email("email@gmail.com")
      .generation(new Generation(1))
      .name("함승훈")
      .course(new Course("FE"))
      .build();

    Introduction introduction = user.getIntroduction();
    introduction.update(Introduction.builder().profileImgUrl("profile").build());

    given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
    // when
    SimpleUserInfoDto simpleUserInfo = userRetrieveService.getSimpleUserInfo(1L);

    // then
    assertThat(simpleUserInfo.getProfileImgUrl()).isEqualTo(introduction.getProfileImgUrl());
  } 
  
  
}