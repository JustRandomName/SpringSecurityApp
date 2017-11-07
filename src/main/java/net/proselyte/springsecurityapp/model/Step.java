package net.proselyte.springsecurityapp.model;

import javax.persistence.*;

@Entity
@Table(name = "step")
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "content")
    private String content;
    @Column(name = "heading")
    private String heading;

    public void setInstructionsId(Long instructionsId) {
        this.instructionsId = instructionsId;
    }

    public Long getInstructionsId() {
        return instructionsId;
    }

    @Column(name = "instructions_id")
    private Long instructionsId;
    @Column(name="number")
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

}