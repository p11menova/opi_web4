package com.example.web4_2.repository;

import com.example.web4_2.models.Point;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PointRepository extends CrudRepository<Point, Long> {
}
