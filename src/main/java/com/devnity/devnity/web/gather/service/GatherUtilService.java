package com.devnity.devnity.web.gather.service;

import static com.slack.api.model.block.Blocks.divider;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;

import com.devnity.devnity.web.gather.dto.SimpleGatherInfoDto;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.service.GatherRetrieveService;
import com.devnity.devnity.web.user.dto.SimpleUserInfoDto;
import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import com.slack.api.model.block.HeaderBlock;
import com.slack.api.webhook.Payload;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
      .text(plainText("π μλ‘μ΄ λͺ¨μ§ κ²μκΈμ΄ λ±λ‘λμμ΄μ!"))
      .build();

    Attachment attachment = Attachment.builder()
      .color("#FFB266")
      .title("κ²μκΈ λ³΄λ¬κ°κΈ° π ")
      .titleLink(redirectUrl + "/gatherlist/" + gather.getGatherId())
      .fields(List.of(
        Field.builder()
          .title("βͺ μ λͺ© :")
          .value(gather.getTitle())
          .valueShortEnough(true)
          .build(),
        Field.builder()
          .title("βͺ μμ±μ :")
          .value(String.format("%s (%s / %dκΈ° / %s)", user.getName(), user.getCourse(), user.getGeneration(), user.getRole()))
          .valueShortEnough(true)
          .build(),
        Field.builder()
          .title("βͺ μΉ΄νκ³ λ¦¬ :")
          .value(gather.getCategory().toString())
          .valueShortEnough(true)
          .build(),
        Field.builder()
          .title("βͺ λͺ¨μ§μΈμ :")
          .value(String.format("%dλͺ", gather.getApplicantLimit()))
          .valueShortEnough(true).build(),
        Field.builder()
          .title("βͺ λͺ¨μ§κΈ°κ° :")
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
