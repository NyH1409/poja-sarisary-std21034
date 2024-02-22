package hei.school.sarisary.endpoint.rest.controller.file;

import hei.school.sarisary.service.FileService;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FileController {
  private final FileService service;

  @PutMapping(
      value = "/black-and-white/{id}",
      consumes = {MediaType.IMAGE_PNG_VALUE})
  public ResponseEntity<?> uploadFile(
      @RequestBody byte[] toUpload, @PathVariable("id") String fileId) {
    try {
      var saved = service.transform(toUpload, fileId);
      return new ResponseEntity<>(saved, HttpStatus.OK);
    } catch (IOException e) {
      return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.OK);
    }
  }

  @GetMapping(value = "/black-and-white/{id}")
  public ResponseEntity<?> getFile(@PathVariable("id") String fileId) {
    var saved = service.download(fileId);
    return new ResponseEntity<>(saved, HttpStatus.OK);
  }
}
