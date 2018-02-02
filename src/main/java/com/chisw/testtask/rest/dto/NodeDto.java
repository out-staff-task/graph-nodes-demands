package com.chisw.testtask.rest.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeDto {

    @NonNull
    private Long id;

    private Long parentId;

    private List<Long> childrenIds;
}
