package com.example.campusconnectbackend.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "blocked_users", uniqueConstraints = @UniqueConstraint(columnNames = "blockedPersonId"))
@Getter
@Setter
@NoArgsConstructor
public class BlockedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer blockedId;

    @ManyToOne
    @JoinColumn(name = "blockedPersonId", referencedColumnName = "id")
    @JsonBackReference
    private Users blockedPerson;

    private String blockedOn;

    private String name;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockedUser that = (BlockedUser) o;
        return blockedId.equals(that.blockedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockedId);
    }
}
