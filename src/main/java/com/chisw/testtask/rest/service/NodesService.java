package com.chisw.testtask.rest.service;

import com.chisw.testtask.rest.dto.NodeDto;

import java.util.ArrayList;
import java.util.List;

public interface NodesService {

    default NodeDto getOrCreateDto(Long id) {
        return new NodeDto();
    }

    default NodeDto createAndReturnDto(NodeDto nodeDto) {
        return new NodeDto();
    }

    default NodeDto deleteAndReturnDto(Long id) {
        return new NodeDto();
    }

    default NodeDto updateAndReturnDto(NodeDto nodeDto) {
        return new NodeDto();
    }

    default List<NodeDto> getAllParentsByIdDto(Long id) {
        return new ArrayList<>();
    }

    default List<NodeDto> getAllChildrenByIdDto(Long id) {
        return new ArrayList<>();
    }
}
