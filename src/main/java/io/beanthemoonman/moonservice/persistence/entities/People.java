package io.beanthemoonman.moonservice.persistence.entities;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
public class People {

  public People() {

  }

  public People(String firstname, String lastname) {
    this.firstname = firstname;
    this.lastname = lastname;
  }

  @Basic
  @Column(name = "firstname", nullable = false, length = -1)
  private String firstname;

  @Basic
  @Column(name = "lastname", nullable = false, length = -1)
  private String lastname;

  @GeneratedValue(strategy = GenerationType.UUID)
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    People people = (People) o;
    return Objects.equals(firstname, people.firstname) && Objects.equals(lastname, people.lastname) && Objects.equals(
        id, people.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstname, lastname, id);
  }
}
