package net.proselyte.springsecurityapp.model;

import javax.persistence.*;

@Entity
@Table(name = "intrTags")
public class InstrTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public int id;

    @Column(name = "tagName")
    public String tagName;

    @Column(name = "instrId")
    public int instrId;

    public int getInstrTagId() {
        return id;
    }

    public void setInstrTagId(int id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getInstrId() {
        return instrId;
    }

    public void setInstrId(int instrId) {
        this.instrId = instrId;
    }
}
