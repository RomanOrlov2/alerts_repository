package com.example.websocketdemo.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id", updatable = false, nullable = false)
    private long role_id;

    @Column(name = "role", nullable = false)
    private String role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<ApplicationUser> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return role_id == role1.role_id &&
                Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role_id, role);
    }
}