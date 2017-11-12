package net.proselyte.springsecurityapp.model;

import javax.persistence.*;

@Entity
@Table(name = "userAchivings")
public class UserAchivings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "achiv")
    private String achiv;
    @Column(name = "userId")
    private int userId;

    public String getAchiv() {
        return achiv;
    }

    public void setAchiv(String achiv) {
        this.achiv = achiv;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
