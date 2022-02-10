package com.patitosoft.encora.patitosoftapp.repository;

import com.patitosoft.encora.patitosoftapp.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, String>, QuerydslPredicateExecutor<Position> {
}
