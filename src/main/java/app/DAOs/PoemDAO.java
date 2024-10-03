package app.DAOs;

import app.DTOs.PoemDTO;
import app.entities.Poem;
import jakarta.persistence.*;

import java.util.Set;
import java.util.stream.Collectors;

public class PoemDAO {
    private EntityManagerFactory emf;

    public PoemDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public PoemDTO getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Poem poem = em.find(Poem.class, id);

            if (poem == null) {
                throw new EntityNotFoundException(String.format("Poem with id %d could not be found", id));
            }

            return new PoemDTO(poem);
        } catch (NullPointerException e) {
            throw new EntityNotFoundException(String.format("Poem with id %d could not be found.", id));
        }
    }

    public Set<PoemDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Poem> query = em.createQuery("SELECT p FROM Poem p", Poem.class);
            return query.getResultStream().map(PoemDTO::new).collect(Collectors.toSet());
        }
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

    public PoemDTO update(PoemDTO poemDTO) {
        Poem poem = poemDTO.getAsEntity();

        try (EntityManager em = emf.createEntityManager()) {
            Poem existingPoem = em.find(Poem.class, poem.getId());
            if (existingPoem == null) {
                throw new EntityNotFoundException("Could not find poem with id: " + poem.getId());
            }
            em.getTransaction().begin();

            if (poem.getTitle() != null) {
                existingPoem.setTitle(poem.getTitle());
            }
            if (poem.getAuthor() != null) {
                existingPoem.setAuthor(poem.getAuthor());
            }
            if (poem.getType() != null) {
                existingPoem.setType(poem.getType());
            }
            if (poem.getPoem() != null) {
                existingPoem.setPoem(poem.getPoem());
            }

            em.getTransaction().commit();
            return new PoemDTO(existingPoem);

        } catch (
                RollbackException e) {
            throw new RollbackException(String.format("Unable to update poem, with id: %d : %s", poem.getId(), e.getMessage()));
        }
    }

    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Poem poem = em.find(Poem.class, id);

            if (poem == null) {
                throw new EntityNotFoundException("Couldn't find poem with id: " + id);
            }

            em.getTransaction().begin();
            em.remove(poem);
            em.getTransaction().commit();
        } catch (RollbackException e) {
            throw new RollbackException(String.format("Unable to delete hotel, with id: %d : %s", id, e.getMessage()));
        }
    }
}
