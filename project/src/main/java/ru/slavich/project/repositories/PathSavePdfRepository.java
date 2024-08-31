package ru.slavich.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.slavich.project.models.PathSavePdf;

public interface PathSavePdfRepository extends JpaRepository<PathSavePdf, Long> {
}
