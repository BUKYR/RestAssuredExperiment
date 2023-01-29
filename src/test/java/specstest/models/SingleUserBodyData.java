package specstest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleUserBodyData {
    private SingleUserData data;
    private SingleUserData support;
}
