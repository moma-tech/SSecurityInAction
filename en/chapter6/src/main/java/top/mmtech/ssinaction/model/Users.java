package top.mmtech.ssinaction.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import top.mmtech.ssinaction.enums.EncryptionAlgorithm;

@Entity(name = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Users implements java.io.Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String username;

  private String password;

  @Enumerated(EnumType.STRING)
  private EncryptionAlgorithm algorithm;

  private Integer enabled;

  @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
  private List<Authorities> authorities;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Users users = (Users) o;
    return id != null && Objects.equals(id, users.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
