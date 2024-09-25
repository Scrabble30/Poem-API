package app.DAOs;

import app.DTOs.PoemDTO;
import app.entities.Poem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class PoemDAO {
    private EntityManagerFactory emf;

    public PoemDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public PoemDTO create(PoemDTO poemDTO) {
        Poem poem = poemDTO.getAsEntity();

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(poem);
            em.getTransaction().commit();
        }
        return new PoemDTO(poem);
    }
}
