package io.beanthemoonman.moonservice.persistence.entities;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
public class ThereGoesATableLikeThis {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Basic
  @Column(name = "id", nullable = false)
  private UUID id;

  @Basic
  @Column(name = "numbah", nullable = false)
  private Long numbah;

  @Basic
  @Column(name = "strang", nullable = false, length = -1)
  private String strang;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Long getNumbah() {
    return numbah;
  }

  public void setNumbah(Long numbah) {
    this.numbah = numbah;
  }

  public String getStrang() {
    return strang;
  }

  public void setStrang(String strang) {
    this.strang = strang;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ThereGoesATableLikeThis that = (ThereGoesATableLikeThis) o;
    return Objects.equals(id, that.id) && Objects.equals(numbah, that.numbah) && Objects.equals(strang, that.strang);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, numbah, strang);
  }
}
