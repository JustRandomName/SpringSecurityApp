package net.proselyte.springsecurityapp.model;


import javax.persistence.*;
import java.util.List;



@Entity
@Table(name = "instructions")
public class Instructions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "instructionsId")
    private Long id;
    @Column(name = "content")
    private String content;
    @Column(name = "heading")
    private String heading;
    @Column(name = "owner_id")
    private int ownerId;
    @Column(name = "rating")
    private double rating;


    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }



    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}