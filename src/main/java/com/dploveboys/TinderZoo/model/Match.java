package com.dploveboys.TinderZoo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="matches")
public class Match {
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

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "match_id")
    private Long match_id;

    public Match() {
    }

    public Match(Long user_id, Long match_id) {
        this.user_id = user_id;
        this.match_id = match_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return Objects.equals(id, match.id);
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", match_id=" + match_id +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatch_id() {
        return match_id;
    }

    public void setMatch_id(Long match_id) {
        this.match_id = match_id;
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

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

}
