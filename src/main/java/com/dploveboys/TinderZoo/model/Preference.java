package com.dploveboys.TinderZoo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="preferences")
public class Preference {
    @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Long id;

    private Long userId;
    private Boolean closeAge;
    private Boolean sameBreed;
    private Boolean nearby;

    public Preference() {
    }

    @Override
    public String toString() {
        return "Preference{" +
                "id=" + id +
                ", userId=" + userId +
                ", closeAge=" + closeAge +
                ", sameBreed=" + sameBreed +
                ", nearby=" + nearby +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Preference that = (Preference) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getCloseAge() {
        return closeAge;
    }

    public void setCloseAge(Boolean closeAge) {
        this.closeAge = closeAge;
    }

    public Boolean getSameBreed() {
        return sameBreed;
    }

    public void setSameBreed(Boolean sameBreed) {
        this.sameBreed = sameBreed;
    }

    public Boolean getNearby() {
        return nearby;
    }

    public void setNearby(Boolean nearby) {
        this.nearby = nearby;
    }
}
