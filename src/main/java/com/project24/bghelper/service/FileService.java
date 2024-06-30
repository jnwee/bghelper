package com.project24.bghelper.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FileService {

  Logger logger = LoggerFactory.getLogger(FileService.class);

  private GridFSBucket gridFSBucket;

  @Autowired
  public FileService(GridFSBucket gridFSBucket) {
    this.gridFSBucket = gridFSBucket;
  }

  public String saveFile(MultipartFile file) throws IOException {

    GridFSUploadOptions options = new GridFSUploadOptions()
        .metadata(new org.bson.Document("contentType", file.getContentType()));

    ObjectId fileId;
    try (InputStream inputStream = file.getInputStream()) {
      fileId = gridFSBucket.uploadFromStream(file.getOriginalFilename(), inputStream, options);
    }
    logger.info("file saved - with ID: {} and ContentType: {}", fileId, file.getContentType());

    GridFSFile gridFSFile = gridFSBucket.find(new org.bson.Document("_id", fileId)).first();
    boolean fileExists = gridFSFile != null;
    logger.info("File with ID: {} exists: {}", fileId, fileExists);

    return fileId.toString();
  }

  public byte[] getFile(String id) {
    ObjectId fileId = new ObjectId(id);
    GridFSFile file = gridFSBucket.find(new org.bson.Document("_id", fileId)).first();
    if (file == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
    }
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    gridFSBucket.downloadToStream(fileId, outputStream);
    return outputStream.toByteArray();
  }

}

