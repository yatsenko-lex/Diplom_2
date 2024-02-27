package praktikum.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrdersRequest {

    @JsonProperty("ingredients")
    private List<String> ingredients;
}
