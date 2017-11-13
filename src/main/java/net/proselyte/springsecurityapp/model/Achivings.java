package net.proselyte.springsecurityapp.model;

import javax.persistence.*;

@Entity
@Table(name = "achivings")
public class Achivings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "achivName")
    private String achivName;
    @Column(name = "threshold")
    private int threshold;
    @Column(name="achivImg")
    private String achivImg;

    public String getAchivImg() {
        return achivImg;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAchivName() {
        return achivName;
    }

    public void setAchivName(String achivName) {
        this.achivName = achivName;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
