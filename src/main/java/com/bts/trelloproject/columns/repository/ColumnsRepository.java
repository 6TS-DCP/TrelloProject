package com.bts.trelloproject.columns.repository;

import com.bts.trelloproject.columns.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnsRepository extends JpaRepository<Columns, Long> {
}
