package de.viadee.k8s.testapp.counter;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CounterEntity {

    @Id
    private String id;

    private Long counter;

    private Timestamp created;

    public CounterEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(final Long counter) {
        this.counter = counter;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(final Timestamp created) {
        this.created = created;
    }
}
