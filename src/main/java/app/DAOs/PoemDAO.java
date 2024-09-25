package app.DAOs;

import app.DTOs.PoemDTO;
import app.entities.Poem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;

import java.util.Set;
import java.util.stream.Collectors;

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

    public PoemDTO getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Poem poem = em.find(Poem.class, id);

            if (poem == null) {
                throw new EntityNotFoundException(String.format("Poem with id %d could not be found", id));
            }

            return new PoemDTO(poem);
        }
    }

    public Set<PoemDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Poem> query = em.createQuery("SELECT p FROM Poem p", Poem.class);
            return query.getResultStream().map(PoemDTO::new).collect(Collectors.toSet());
        }
    }
}
