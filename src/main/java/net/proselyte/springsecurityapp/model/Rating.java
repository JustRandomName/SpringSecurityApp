package net.proselyte.springsecurityapp.model;


import javax.persistence.*;

@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "instr_id")
    private Long instrId;
    @Column(name = "mark")
    private int mark;

    public int getRatingId() {
        return id;
    }

    public void setRatingId(int id) {
        this.id = id;
    }

    public Long getUser_id() {
        return userId;
    }

    public void setUser_id(Long user_id) {
        this.userId = user_id;
    }

    public Long getInstr_id() {
        return instrId;
    }

    public void setInstr_id(Long instr_id) {
        this.instrId = instr_id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }



}
