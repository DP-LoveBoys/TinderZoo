package com.dploveboys.TinderZoo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="interests")
public class Interest {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //@Column(name = "user_id")
    private Long user_id;
    private String interest_tag;

    public Interest(Long user_id, String interest) {
        this.user_id = user_id;
        this.interest_tag = interest;
    }

    public Interest() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interest interest = (Interest) o;

        return id != null ? id.equals(interest.id) : interest.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Long getInterest_id() {
        return id;
    }

    public void setInterest_id(Long interest_id) {
        this.id = interest_id;
    }

    public String getInterest_tag() {
        return interest_tag;
    }

    public void setInterest_tag(String interest_tag) {
        this.interest_tag = interest_tag;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Interest{" +
                "id=" + id +
                ", interest_tag='" + interest_tag + '\'' +
                '}';
    }
}
