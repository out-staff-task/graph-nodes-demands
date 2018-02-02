package com.chisw.testtask.rest.controller;

import com.chisw.testtask.rest.dto.NodeDto;
import com.chisw.testtask.rest.service.NodesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/nodes")
@Api(description = "The REST API non-cyclic connected graph")
public class NodesController {

    @Autowired
    private NodesService service;

    @GetMapping("/{id}/parents")
    @ApiOperation("Get nodes by ID")
    public List<NodeDto> getParentsById(@PathVariable Long id) {
        return service.getAllParentsByIdDto(id);
    }

    @GetMapping("/{id}/children")
    @ApiOperation("Get all children of node by own ID")
    public List<NodeDto> getChildrenById(@PathVariable Long id) {
        return service.getAllChildrenByIdDto(id);
    }

    @GetMapping("/{id}")
    @ApiOperation("Create node with specified ID or return stored if exist one")
    public NodeDto getOrCreateNodeById(@PathVariable Long id) {
        return service.getOrCreateDto(id);
    }

    @PostMapping
    @ApiOperation("Create node with specified ID and specified parent and child, if one or parent or any child don't exist then will be created")
    public NodeDto Nodes(@RequestBody @Valid NodeDto nodeDto) {
        return service.createAndReturnDto(nodeDto);
    }

    @PutMapping
    @ApiOperation("Update node with specified ID and specified parent and child, if one or parent or any child don't exist then will be created")
    public NodeDto updateNode(@RequestBody @Valid NodeDto nodeDto) {
        return service.updateAndReturnDto(nodeDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete node with by ID with all children")
    public NodeDto deleteNode(@PathVariable Long id) {
        return service.deleteAndReturnDto(id);
    }
}
