package com.devnity.devnity.domain.gather.service;

import static com.slack.api.model.block.Blocks.divider;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static org.springframework.http.ContentDisposition.attachment;

import com.devnity.devnity.domain.gather.dto.SimpleGatherInfoDto;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Attachments;
import com.slack.api.model.Field;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.HeaderBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.webhook.Payload;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
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

  @Value("${slack.redirectUrl}")
  private String redirectUrl;

  private final GatherRetrieveService gatherRetrieveService;

  @Transactional
  public List<Gather> closeExpiredGather() {
    List<Gather> expiredGathers = gatherRetrieveService.getExpiredGathers();
    expiredGathers.forEach(gather -> gather.close());
    return expiredGathers;
  }

  public void sendAlarmToSlack(SimpleUserInfoDto user, SimpleGatherInfoDto gather) {
    HeaderBlock headerBlock = HeaderBlock.builder()
      .text(plainText("🕊 새로운 모집 게시글이 등록되었어요!"))
      .build();

    Attachment attachment = Attachment.builder()
      .color("#FFB266")
      .title("게시글 보러가기 🚀 ")
      .titleLink(redirectUrl + "/gatherlist/" + gather.getGatherId()) // FIXME : 실제 배포서버 url로 변경해야함
      .fields(List.of(
        Field.builder()
          .title("▪ 제목 :")
          .value(gather.getTitle())
          .valueShortEnough(true)
          .build(),
        Field.builder()
          .title("▪ 작성자 :")
          .value(String.format("%s (%s / %d기 / %s)", user.getName(), user.getCourse(), user.getGeneration(), user.getRole()))
          .valueShortEnough(true)
          .build(),
        Field.builder()
          .title("▪ 카테고리 :")
          .value(gather.getCategory().toString())
          .valueShortEnough(true)
          .build(),
        Field.builder()
          .title("▪ 모집인원 :")
          .value(String.format("%d명", gather.getApplicantLimit()))
          .valueShortEnough(true).build(),
        Field.builder()
          .title("▪ 모집기간 :")
          .value(String.format("%s ~ %s", LocalDate.now(), gather.getDeadline().toLocalDate()))
          .build()
      ))
      .build();

    try {
      Slack.getInstance().send(slackUrl,
        Payload.builder()
          .blocks(List.of(headerBlock))
          .attachments(List.of(attachment))
          .build()
      );
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Transactional
  public void increaseCommentCount(Long gatherId) {
    gatherRetrieveService.getGather(gatherId)
      .increaseCommentCount();
  }

  @Transactional
  public void decreaseCommentCount(Long gatherId) {
    gatherRetrieveService.getGather(gatherId)
      .decreaseCommentCount();
  }

  @Transactional
  public void increaseApplicantCount(Long gatherId) {
    gatherRetrieveService.getGather(gatherId)
      .increaseApplicantCount();
  }

  @Transactional
  public void decreaseApplicantCount(Long gatherId) {
    gatherRetrieveService.getGather(gatherId)
      .decreaseApplicantCount();
  }
}
