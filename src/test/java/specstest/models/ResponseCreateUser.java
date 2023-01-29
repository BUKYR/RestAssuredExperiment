package specstest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseCreateUser {
    private String
            name,
            job,
            id,
            createdAt;
}
