package de.sharetrip.core.secutiry;

import de.sharetrip.core.exception.ResourceNotFoundException;
import de.sharetrip.user.domain.User;
import de.sharetrip.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    //shame we are using real db here.
    //should be cached
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String email)
            throws UsernameNotFoundException {
        final User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with email [%s]", email)));

        return UserPrincipal.create(user);
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserById(final Long id) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        return UserPrincipal.create(user);
    }
}
