package hei.school.sarisary.service;

import hei.school.sarisary.endpoint.response.FileResponse;
import hei.school.sarisary.file.BucketComponent;
import hei.school.sarisary.repository.handler.FileHandler;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import javax.imageio.ImageIO;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FileService {
  private static final Duration EXPIRATION = Duration.of(300, ChronoUnit.SECONDS);
  private final FileHandler handler;
  private final BucketComponent bucketComponent;
  private final String TRANSFORMED_PREFIX = "tr-";

  public FileResponse transform(byte[] imgByte, String fileId) throws IOException {
    var original = generatePresignedUrl(imgByte, fileId);
    var inpByte = new ByteArrayInputStream(imgByte);
    BufferedImage bufferImg = ImageIO.read(inpByte);
    BufferedImage newBufferedImg = handler.changeColor(bufferImg);
    var temp = new ByteArrayOutputStream();
    ImageIO.write(newBufferedImg, "jpg", temp);
    byte[] result = temp.toByteArray();
    var transformed = generatePresignedUrl(result, TRANSFORMED_PREFIX + fileId);
    return FileResponse.builder().originalUrl(original).transformedUrl(transformed).build();
  }

  @SneakyThrows
  private URL generatePresignedUrl(byte[] toUpload, String key) {
    var file = Files.write(Path.of("/temp/temp.png"), toUpload);
    bucketComponent.upload(file.toFile(), key);
    var url = bucketComponent.presign(key, EXPIRATION);
    return url;
  }

  public FileResponse download(String fileId) {
    var original = bucketComponent.presign(fileId, EXPIRATION);
    var transformed = bucketComponent.presign(TRANSFORMED_PREFIX + fileId, EXPIRATION);
    return FileResponse.builder().originalUrl(original).transformedUrl(transformed).build();
  }
}
