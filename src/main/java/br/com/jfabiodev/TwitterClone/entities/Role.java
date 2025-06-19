package br.com.jfabiodev.TwitterClone.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Role {

    @Column(name = "role_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Getter
    public enum Values{
        ADMIN(1L),
        BASIC(2L);


        Long roleID;
        Values(Long roleID){
            this.roleID = roleID;
        }

    }
}
