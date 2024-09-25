package app.DTOs;

import app.entities.Poem;
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

    public PoemDTO(Poem poem) {
        this.author = poem.getAuthor();
        this.title = poem.getTitle();
        this.type = poem.getType();
        this.poem = poem.getPoem();
    }

    public Poem getAsEntity(){
        return Poem.builder()
                .author(author)
                .title(title)
                .type(type)
                .poem(poem)
                .build();
    }
}
