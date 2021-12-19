package com.devnity.devnity.domain.gather.service;

import com.devnity.devnity.domain.gather.dto.SimpleGatherInfoDto;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class GatherUtilService {

  @Value("${slack.webhookUrl}")
  private String slackUrl;

  private final GatherRetrieveService gatherRetrieveService;

  @Transactional
  public List<Gather> closeExpiredGather(){
    List<Gather> expiredGathers = gatherRetrieveService.getExpiredGathers();
    for(Gather gather : expiredGathers){
      gather.close();
    }
    return expiredGathers;
  }

  public void sendMessageToSlack(SimpleUserInfoDto user, SimpleGatherInfoDto gather){
    RestTemplate restTemplate = new RestTemplate();

    Map<String,Object> request = new HashMap<>();
    request.put("text", String.format("작성자 : %s / 게시글 제목 : %s", user.getName(), gather.getTitle()));

    HttpEntity<Map<String,Object>> entity = new HttpEntity<>(request);
    restTemplate.exchange(slackUrl, HttpMethod.POST, entity, String.class);
  }

  @Transactional
  public void increaseCommentCount(Long gatherId){
    gatherRetrieveService.getGather(gatherId)
      .increaseCommentCount();
  }

  @Transactional
  public void decreaseCommentCount(Long gatherId){
    gatherRetrieveService.getGather(gatherId)
      .decreaseCommentCount();
  }

  @Transactional
  public void increaseApplicantCount(Long gatherId){
    gatherRetrieveService.getGather(gatherId)
      .increaseApplicantCount();
  }

  @Transactional
  public void decreaseApplicantCount(Long gatherId){
    gatherRetrieveService.getGather(gatherId)
      .decreaseApplicantCount();
  }
}
