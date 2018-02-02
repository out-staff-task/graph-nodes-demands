package com.chisw.testtask.rest.service;

import com.chisw.testtask.exception.DataRequestException;
import com.chisw.testtask.rest.dto.NodeDto;
import com.chisw.testtask.rest.entity.NodeEntity;
import com.chisw.testtask.rest.repository.NodesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.chisw.testtask.helper.AssertUtils.assertVertexDtoEquals;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
@Transactional
public class NodesServiceModelTest {
    @Autowired
    private NodesService service;

    @Autowired
    private NodesRepository repository;

    @Test
    @Sql(scripts = "/task-data-ddl.sql")
    public void getOrCreateDtoIfExistRootTest() {
        long id = 3L;
        assertTrue(repository.exists(id));
        NodeDto dto = service.getOrCreateDto(id);
        assertVertexDtoEquals(repository.findOne(id).dto(), dto);
    }

    @Test
    @Sql(scripts = "/task-data-ddl.sql")
    public void getOrCreateDtoIfExistMiddleLeafTest() {
        long id = 4L;
        assertTrue(repository.exists(id));
        NodeDto dto = service.getOrCreateDto(id);
        assertVertexDtoEquals(repository.findOne(id).dto(), dto);
    }

    @Test
    @Sql(scripts = "/task-data-ddl.sql")
    public void getOrCreateDtoIfExistEndLeafTest() {
        long id = 7L;
        assertTrue(repository.exists(id));
        NodeDto dto = service.getOrCreateDto(id);
        assertVertexDtoEquals(repository.findOne(id).dto(), dto);
    }

    @Test
    @Sql(scripts = "/task-data-ddl.sql")
    public void getOrCreateDtoIfNotExistTest() {
        long id = 10L;
        assertFalse(repository.exists(id));
        NodeDto dto = service.getOrCreateDto(id);
        assertVertexDtoEquals(NodeDto.builder().id(10L).build(), dto);
    }

    @Test
    public void createAndReturnDtoIfNotExistTest() {
        long id = 10L;
        assertFalse(repository.exists(id));
        NodeDto dto = service.getOrCreateDto(id);
        assertVertexDtoEquals(repository.findOne(id).dto(), dto);
    }

    @Test
    @Sql(scripts = "/task-data-ddl.sql")
    public void createAndReturnDtoIfExistTest() {
        long id = 3L;
        assertTrue(repository.exists(id));

        NodeDto dto = service.getOrCreateDto(id);
        assertVertexDtoEquals(repository.findOne(id).dto(), dto);
    }

    @Test
    @Sql(scripts = "/task-data-ddl.sql")
    public void deleteAndReturnDtoIfExistDtoTest() {
        long id = 3L;
        assertTrue(repository.exists(id));

        NodeDto dto = service.deleteAndReturnDto(id);

        assertFalse(repository.exists(dto.getId()));
        assertEquals(repository.count(), 0);
    }

    @Test(expected = DataRequestException.class)
    @Sql(scripts = "/task-data-ddl.sql")
    public void deleteAndReturnDtoIfNotExistDtoTest() {
        long id = 10L;
        assertFalse(repository.exists(id));
        service.deleteAndReturnDto(id);
        fail();
    }

    @Test
    @Sql(scripts = "/task-data-ddl.sql")
    public void updateAndReturnDtoIfAllExistTest() {
        long id = 6L;
        Long newParentId = 5L;

        assertTrue(repository.exists(newParentId));
        NodeDto storedVertex = repository.findOne(id).dto();
        assertNotEquals(storedVertex.getParentId(), newParentId);
        storedVertex.setParentId(newParentId);

        NodeDto dto = service.updateAndReturnDto(storedVertex);
        assertVertexDtoEquals(storedVertex, dto);
    }

    @Test
    public void updateAndReturnDtoIfAllNotExistTest() {
        long id = 6L;
        assertEquals(repository.count(), 0);

        NodeEntity nodeEntity = NodeEntity.builder().id(id).parent(NodeEntity.builder().id(1L).build())
                .children(LongStream.range(2, 5).mapToObj(i -> NodeEntity.builder().id(i).build())
                        .collect(Collectors.toList()))
                .build();

        NodeDto dto = service.updateAndReturnDto(nodeEntity.dto());
        assertVertexDtoEquals(nodeEntity.dto(), dto);
        assertEquals(repository.count(), 5);
    }

    @Test
    @Sql(scripts = "/task-data-ddl.sql")
    public void updateAndReturnDtoIfChildrenNotExistTest() {
        long id = 8L;
        long initialAmount = repository.count();
        NodeEntity nodeEntity = repository.findOne(id);
        nodeEntity.setChildren(LongStream.range(10, 15).mapToObj(i -> NodeEntity.builder().id(i).build()).collect(Collectors.toList()));

        NodeDto dto = service.updateAndReturnDto(nodeEntity.dto());

        assertVertexDtoEquals(nodeEntity.dto(), dto);
        assertEquals(repository.count() - initialAmount, 5);
    }

    @Test
    @Sql(scripts = "/task-data-ddl.sql")
    public void updateAndReturnDtoIfParentNotExistTest() {
        long id = 8L;
        long parentId = 10L;
        long initialAmount = repository.count();
        NodeEntity nodeEntity = repository.findOne(id);
        nodeEntity.setParent(NodeEntity.builder().id(parentId).build());

        NodeDto dto = service.updateAndReturnDto(nodeEntity.dto());

        assertVertexDtoEquals(nodeEntity.dto(), dto);
        assertEquals(repository.count() - initialAmount, 1);
    }

    @Test
    @Sql(scripts = "/task-data-ddl.sql")
    public void getAllParentsByIdDtoIfParentExistTest() {
        long id = 8L;
        List<NodeDto> parents = service.getAllParentsByIdDto(id);

        int count = 0;
        for (NodeDto parent : parents) {
            if (parent.getId().equals(6L) ||
                    parent.getId().equals(2L) ||
                    parent.getId().equals(3L)) {
                count++;
            }
        }
        assertEquals(parents.size(), count);
    }

    @Test
    @Sql(scripts = "/task-data-ddl.sql")
    public void getAllParentsByIdDtoIfParentNotExistTest() {
        long id = 3L;
        List<NodeDto> parents = service.getAllParentsByIdDto(id);

        int count = 0;
        assertEquals(parents.size(), count);
    }

    @Test(expected = DataRequestException.class)
    public void getAllParentsByIdDtoIfVertexNotExistTest() {
        long id = 1L;
        service.getAllParentsByIdDto(id);
        fail();
    }

    @Test
    @Sql(scripts = "/task-data-ddl.sql")
    public void getAllChildrenByIdDtoIfChildrenExistTest() {
        long id = 4L;
        List<NodeDto> children = service.getAllChildrenByIdDto(id);

        int count = 0;
        for (NodeDto child : children) {
            if (child.getId().equals(5L) ||
                    child.getId().equals(7L)) {
                count++;
            }
        }
        assertEquals(children.size(), count);
    }

    @Test
    @Sql(scripts = "/task-data-ddl.sql")
    public void getAllChildrenByIdDtoIfChildrenNotExistsTest() {
        long id = 7L;

        List<NodeDto> children = service.getAllChildrenByIdDto(id);
        int count = 0; //count matched
        assertEquals(children.size(), count);
    }

    @Test(expected = DataRequestException.class)
    public void getAllChildrenByIdDtoIfVertexNotExistsTest() {
        long id = 1L;
        service.getAllChildrenByIdDto(id);
        fail();
    }
}