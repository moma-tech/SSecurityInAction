package top.mmtech.ssinaction.service;

import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.mmtech.ssinaction.entity.ZoffyUserDetails;
import top.mmtech.ssinaction.model.Users;
import top.mmtech.ssinaction.repo.UserRepository;

@Service
public class ZoffyUserDetailsService implements UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Override
        public ZoffyUserDetails loadUserByUsername(String username)
                        throws UsernameNotFoundException {

                Supplier<UsernameNotFoundException> usernameNotFoundException =
                                () -> new UsernameNotFoundException(
                                                "Authentication Faild");

                Users users = userRepository.findUserByUsername(username)
                                .orElseThrow(usernameNotFoundException);

                return new ZoffyUserDetails(users);

        }

}
