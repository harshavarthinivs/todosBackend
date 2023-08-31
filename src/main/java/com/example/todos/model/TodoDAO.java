package com.example.todos.model;

import org.hibernate.annotations.Type;
import com.example.todos.enums.Priority;
import com.example.todos.enums.TodoStatus;
import com.example.todos.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
@Table(name = "todo")
public class TodoDAO {
   
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  @Column(nullable = false)
  private String title;
  
  @Column(length=65535)
  private String description;
  
  @Enumerated(EnumType.STRING)
  private Priority priority;
  
  @Enumerated(EnumType.STRING)
  private TodoStatus status;
  
  @ManyToOne
  @JoinColumn(name = "userid")
  private User user; 
  
  /** 
   * set the default  value for  status and priority before inserting into the database
   * */
  
  @PrePersist
  void preInsert() {
    if(status == null) {
      status = TodoStatus.NOT_STARTED;
    }
    if( priority == null) {
      priority = Priority.LOW;
    }
  }
}
