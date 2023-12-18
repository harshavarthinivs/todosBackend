package com.example.todos.model;


import java.util.Objects;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class CategoryDAO {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @Column
  private String category;
  
  @OneToMany(mappedBy = "categoryDao",fetch = FetchType.EAGER)
  Set<TodoDAO> todos; 
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CategoryDAO categoryObj = (CategoryDAO) o;
    return Objects.equals(id, categoryObj.id);
  }
  
  @Override
  public int hashCode() {
    
    return Objects.hash(id);
  }

}
