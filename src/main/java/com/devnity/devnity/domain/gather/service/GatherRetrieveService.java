package com.devnity.devnity.domain.gather.service;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.exception.GatherNotFoundException;
import com.devnity.devnity.domain.gather.repository.GatherRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GatherRetrieveService {

  private final GatherRepository gatherRepository;

  public Gather getGather(Long gatherId){
    return gatherRepository.findById(gatherId)
      .orElseThrow(GatherNotFoundException::new);
  }

}
