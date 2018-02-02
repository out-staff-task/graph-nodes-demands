package com.chisw.testtask.rest.service;


import com.chisw.testtask.rest.dto.NodeDto;

import java.util.List;

public interface NodesService {

    NodeDto getOrCreateDto(Long id);

    NodeDto createAndReturnDto(NodeDto nodeDto);

    NodeDto deleteAndReturnDto(Long id);

    NodeDto updateAndReturnDto(NodeDto nodeDto);

    List<NodeDto> getAllParentsByIdDto(Long id);

    List<NodeDto> getAllChildrenByIdDto(Long id);
}
