package com.project24.bghelper.service;

import com.project24.bghelper.model.Class;
import com.project24.bghelper.repository.ClassRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ClassService {

  private static final Logger logger = LoggerFactory.getLogger(ClassService.class);

  ClassRepository classRepository;

  public ClassService(ClassRepository classRepository) {
    this.classRepository = classRepository;
  }

  public Optional<Class> getClassById(String id) {
    return classRepository.findById(id);
  }

  public Class addClass(Class charClass) {
    return classRepository.save(charClass);
  }

  public void deleteClass(String id) {
    classRepository.deleteById(id);
    logger.info("Deleted Class with ID - {}", id);
  }
}
