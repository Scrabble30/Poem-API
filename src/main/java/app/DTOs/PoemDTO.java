package app.DTOs;

import app.enums.PoemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoemDTO {
    private String author;
    private String title;
    private PoemType type;
    private String poem;
}
