package top.mmtech.ssinaction.service;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import top.mmtech.ssinaction.entity.SimpleUser;

public class InMemoryuserDetailsService implements UserDetailsService {

    private final List<SimpleUser> users;

    
    public InMemoryuserDetailsService(List<SimpleUser> users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return users.stream()
                .filter(user -> user.getUsername().equals(username)).findFirst()
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not Found"));
    }

}
