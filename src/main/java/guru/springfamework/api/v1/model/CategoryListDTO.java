package guru.springfamework.api.v1.model;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListDTO {
    List<CategoryDTO> categories;
}
