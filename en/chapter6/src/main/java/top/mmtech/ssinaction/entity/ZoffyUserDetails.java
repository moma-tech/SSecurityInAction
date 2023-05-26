package top.mmtech.ssinaction.entity;

import java.util.Collection;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.mmtech.ssinaction.model.Users;

@Data
public class ZoffyUserDetails implements UserDetails {

  private final Users users;

  public ZoffyUserDetails(Users users) {
    this.users = users;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return users.getAuthorities().stream()
        .map(authority -> new SimpleGrantedAuthority(authority.getName()))
        .toList();
  }

  @Override
  public String getPassword() {
    return users.getPassword();
  }

  @Override
  public String getUsername() {
    return users.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return users.getEnabled().equals(1);
  }
}
