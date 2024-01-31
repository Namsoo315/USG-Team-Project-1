package com.example.loc.repository;

import com.example.loc.domain.Image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
