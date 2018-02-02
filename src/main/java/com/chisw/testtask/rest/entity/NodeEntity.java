package com.chisw.testtask.rest.entity;

import com.chisw.testtask.rest.dto.NodeDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "spikes")
public class NodeEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private NodeEntity parent;

    @OneToMany
    @JoinColumn(name = "parent_id")
    private List<NodeEntity> children;

    public NodeDto dto() {
        return NodeDto.builder()
                .id(id)
                .parentId(Objects.nonNull(parent) ? parent.getId() : null)
                .childrenIds(Objects.nonNull(children) ? children.stream().map(NodeEntity::getId).collect(Collectors.toList()) : null)
                .build();
    }
}
