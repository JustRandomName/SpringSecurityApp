package net.proselyte.springsecurityapp.model;

import javax.persistence.*;

@Entity
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "comment_id")
    private Long comment_id;
    @Column(name = "user_id")
    private Long user_id;

    public int getLikeId() {
        return id;
    }

    public void setLikeId(int id) {
        this.id = id;
    }

    public Long getComment_id() {
        return comment_id;
    }

    public void setComment_id(Long comment_id) {
        this.comment_id = comment_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
