package hei.school.sarisary.endpoint.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class FileResponse {
  @JsonProperty("original_url")
  private URL originalUrl;

  @JsonProperty("transformed_url")
  private URL transformedUrl;
}
