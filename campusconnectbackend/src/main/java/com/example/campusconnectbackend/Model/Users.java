package com.example.campusconnectbackend.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String name;
    private String password;
    private String mobileNumber;
    private String role;
    private String lastname;
    private String headline;
    private String about;
    private String website;
    private String linkedIn;
    private String twitter;
    private String faceBook;
    private String youTube;
    private boolean isProfilePublic; // Field to store profile visibility
    
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String userProfileImage; // Field to store Base64 encoded image


    @OneToMany(mappedBy = "blockedPerson", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<BlockedUser> blockedUsers;

    
    @OneToMany(mappedBy = "reportedPerson", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<ReportedUser> reportedUsers;

    
    @ElementCollection
    @CollectionTable(name = "created_courses", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "course_id")
    private Set<Long> createdCourseIds = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "enrolled_courses", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "course_id")
    private Set<Long> enrolledCourseIds = new HashSet<>();

    @Column(name = "cart_id")
    private Long cartId;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return email;
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
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return id.equals(users.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean getProfilePublic() {
        return isProfilePublic;
    }
}
