package tuleuov.space.railway.service;

import jakarta.persistence.EntityNotFoundException;
import tuleuov.space.railway.entity.Role;
import tuleuov.space.railway.entity.User;
import tuleuov.space.railway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    public User getUserById(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с таким id не найден: " + userId));
    }
    public User save(User user) {
        return repository.save(user);
    }

    public User create(User user) {
        try {
            if (repository.existsByUsername(user.getUsername())) {
                throw new RuntimeException("Пользователь с таким именем уже существует");
            }

            if (repository.existsByEmail(user.getEmail())) {
                throw new RuntimeException("Пользователь с таким email уже существует");
            }

            return save(user);


        } catch (Exception e) {
            throw new RuntimeException("Error creating user", e);
        }

    }

    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }
    public User getUserByPhone(String phone) {
        return repository.findByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("User with phone " + phone + " not found"));
    }
    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }
}
