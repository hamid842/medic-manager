package com.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A TimeTable.
 */
@Entity
@Table(name = "time_table")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TimeTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Column(name = "is_taken")
    private String isTaken;

    @ManyToOne
    @JsonIgnoreProperties("timeTables")
    private MedicineInfo medicineInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public TimeTable date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getIsTaken() {
        return isTaken;
    }

    public TimeTable isTaken(String isTaken) {
        this.isTaken = isTaken;
        return this;
    }

    public void setIsTaken(String isTaken) {
        this.isTaken = isTaken;
    }

    public MedicineInfo getMedicineInfo() {
        return medicineInfo;
    }

    public TimeTable medicineInfo(MedicineInfo medicineInfo) {
        this.medicineInfo = medicineInfo;
        return this;
    }

    public void setMedicineInfo(MedicineInfo medicineInfo) {
        this.medicineInfo = medicineInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeTable)) {
            return false;
        }
        return id != null && id.equals(((TimeTable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", isTaken='" + getIsTaken() + "'" +
            "}";
    }
}
