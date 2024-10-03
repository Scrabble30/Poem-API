package app.security;

import app.security.entities.User;
import app.security.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;

/**
 * Purpose: To handle security with the database
 * Author: Thomas Hartmann
 */
public interface ISecurityDAO {
    UserDTO getVerifiedUser(String username, String password) throws ValidationException;

    User createUser(String username, String password);
}
