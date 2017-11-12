package net.proselyte.springsecurityapp.model;

import javax.persistence.*;
import java.util.Set;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column (name = "enabled")
    private Boolean enabled;

    @Column(name = "numderLikes")
    private int numderLikes = 0;

    @Column(name = "numberComments")
    private int numberComments = 0;

    @Column(name = "numderInstr")
    private  int numderInstr = 0;

    public int getNumderLikes() {
        return numderLikes = 0;
    }

    public void setNumderLikes(int numderLikes) {
        this.numderLikes = numderLikes;
    }

    public int getNumberComments() {
        return numberComments;
    }

    public void setNumberComments(int numberComments) {
        this.numberComments = numberComments;
    }

    public int getNumderInstr() {
        return numderInstr;
    }

    public void setNumderInstr(int numderInstr) {
        this.numderInstr = numderInstr;
    }

    @Transient
    private String confirmPassword;

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
