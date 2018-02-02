package com.chisw.testtask.rest.service;

import com.chisw.testtask.exception.DataRequestException;
import com.chisw.testtask.rest.dto.NodeDto;
import com.chisw.testtask.rest.entity.NodeEntity;
import com.chisw.testtask.rest.repository.NodesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class NodesServiceModel implements NodesService {

    @Autowired
    private NodesRepository repository;

    @Override
    @Transactional
    public NodeDto getOrCreateDto(Long id) {
        NodeEntity nodeEntity = repository.findOne(id);
        if (Objects.isNull(nodeEntity)) {
            nodeEntity = repository.save(NodeEntity.builder().id(id).build());
        }
        return nodeEntity.dto();
    }

    @Override
    @Transactional
    public NodeDto createAndReturnDto(NodeDto nodeDto) {
        return saveOrUpdate(nodeDto);
    }

    @Override
    @Transactional
    public NodeDto deleteAndReturnDto(Long id) {
        NodeEntity nodeEntity = repository.findOne(id);
        if (Objects.isNull(nodeEntity)) {
            throw new DataRequestException(String.format("nodeEntity with id: %s not found", id));
        }
        deleteWithChildren(nodeEntity);
        return nodeEntity.dto();
    }

    private void deleteWithChildren(NodeEntity nodeEntity) {
        List<NodeEntity> children = nodeEntity.getChildren();
        for (NodeEntity child : children) {
            deleteWithChildren(child);
        }
        repository.delete(nodeEntity.getId());
    }

    @Override
    @Transactional
    public NodeDto updateAndReturnDto(NodeDto nodeDto) {
        return saveOrUpdate(nodeDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NodeDto> getAllParentsByIdDto(Long id) {
        if (!repository.exists(id)) {
            throw new DataRequestException(String.format("Node with id: %s not found", id));
        }
        List<NodeEntity> parents = new ArrayList<>();
        fillParents(repository.findOne(id), parents);
        return parents.stream().map(NodeEntity::dto).collect(Collectors.toList());
    }

    private void fillParents(NodeEntity nodeEntity, List<NodeEntity> list) {
        NodeEntity parent = nodeEntity.getParent();
        if (Objects.isNull(parent)) {
            return;
        }
        NodeEntity fetchedParent = repository.findOne(parent.getId());
        list.add(parent);
        fillParents(fetchedParent, list);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NodeDto> getAllChildrenByIdDto(Long id) {
        if (!repository.exists(id)) {
            throw new DataRequestException(String.format("Node with id: %s not found", id));
        }
        List<NodeEntity> children = new ArrayList<>();
        fillChildren(repository.findOne(id), children);
        return children.stream().map(NodeEntity::dto).collect(Collectors.toList());
    }

    private void fillChildren(NodeEntity nodeEntity, List<NodeEntity> list) {
        List<NodeEntity> children = nodeEntity.getChildren();
        if (Objects.isNull(children)) {
            return;
        }
        for (NodeEntity child : children) {
            NodeEntity fetchedChild = repository.findOne(child.getId());
            list.add(child);
            fillChildren(fetchedChild, list);
        }
    }

    private NodeDto saveOrUpdate(NodeDto nodeDto) {
        NodeEntity nodeEntity = NodeEntity.builder().id(nodeDto.getId()).build();
        if (Objects.nonNull(nodeDto.getParentId())) {
            NodeEntity parent = repository.findOne(nodeDto.getParentId());
            if (Objects.isNull(parent)) {
                parent = NodeEntity.builder().id(nodeDto.getParentId()).build();
                repository.save(parent);
            }
            nodeEntity.setParent(parent);
        }
        nodeEntity = repository.save(nodeEntity);
        if (Objects.nonNull(nodeDto.getChildrenIds())) {
            nodeEntity.setChildren(new ArrayList<>());
            for (Long id : nodeDto.getChildrenIds()) {
                NodeEntity child = repository.findOne(id);
                if (Objects.isNull(child)) {
                    child = NodeEntity.builder().id(id).build();
                }
                child.setParent(nodeEntity);
                nodeEntity.getChildren().add(child);
            }
            repository.save(nodeEntity.getChildren());
        }
        return nodeEntity.dto();
    }
}
