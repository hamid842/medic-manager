package com.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A SideEffect.
 */
@Entity
@Table(name = "side_effect")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SideEffect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "side_effect")
    private String sideEffect;

    @ManyToOne
    @JsonIgnoreProperties("sideEffects")
    private MedicineInfo medicineInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSideEffect() {
        return sideEffect;
    }

    public SideEffect sideEffect(String sideEffect) {
        this.sideEffect = sideEffect;
        return this;
    }

    public void setSideEffect(String sideEffect) {
        this.sideEffect = sideEffect;
    }

    public MedicineInfo getMedicineInfo() {
        return medicineInfo;
    }

    public SideEffect medicineInfo(MedicineInfo medicineInfo) {
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
        if (!(o instanceof SideEffect)) {
            return false;
        }
        return id != null && id.equals(((SideEffect) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SideEffect{" +
            "id=" + getId() +
            ", sideEffect='" + getSideEffect() + "'" +
            "}";
    }
}
