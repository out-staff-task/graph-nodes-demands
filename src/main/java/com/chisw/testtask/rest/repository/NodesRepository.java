package com.chisw.testtask.rest.repository;

import com.chisw.testtask.rest.entity.NodeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodesRepository extends JpaRepository<NodeEntity, Long> {
}
