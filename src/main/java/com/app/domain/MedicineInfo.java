package com.app.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A MedicineInfo.
 */
@Entity
@Table(name = "medicine_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MedicineInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "important_info")
    private String importantInfo;

    @Column(name = "jhi_usage")
    private String usage;

    @Column(name = "initial_count")
    private String initialCount;

    @Column(name = "promised")
    private String promised;

    @Column(name = "refill_info")
    private Instant refillInfo;

    @Column(name = "pharmacy_notes")
    private String pharmacyNotes;

    @OneToMany(mappedBy = "medicineInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SideEffect> sideEffects = new HashSet<>();

    @OneToMany(mappedBy = "medicineInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TimeTable> timeTables = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MedicineInfo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImportantInfo() {
        return importantInfo;
    }

    public MedicineInfo importantInfo(String importantInfo) {
        this.importantInfo = importantInfo;
        return this;
    }

    public void setImportantInfo(String importantInfo) {
        this.importantInfo = importantInfo;
    }

    public String getUsage() {
        return usage;
    }

    public MedicineInfo usage(String usage) {
        this.usage = usage;
        return this;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getInitialCount() {
        return initialCount;
    }

    public MedicineInfo initialCount(String initialCount) {
        this.initialCount = initialCount;
        return this;
    }

    public void setInitialCount(String initialCount) {
        this.initialCount = initialCount;
    }

    public String getPromised() {
        return promised;
    }

    public MedicineInfo promised(String promised) {
        this.promised = promised;
        return this;
    }

    public void setPromised(String promised) {
        this.promised = promised;
    }

    public Instant getRefillInfo() {
        return refillInfo;
    }

    public MedicineInfo refillInfo(Instant refillInfo) {
        this.refillInfo = refillInfo;
        return this;
    }

    public void setRefillInfo(Instant refillInfo) {
        this.refillInfo = refillInfo;
    }

    public String getPharmacyNotes() {
        return pharmacyNotes;
    }

    public MedicineInfo pharmacyNotes(String pharmacyNotes) {
        this.pharmacyNotes = pharmacyNotes;
        return this;
    }

    public void setPharmacyNotes(String pharmacyNotes) {
        this.pharmacyNotes = pharmacyNotes;
    }

    public Set<SideEffect> getSideEffects() {
        return sideEffects;
    }

    public MedicineInfo sideEffects(Set<SideEffect> sideEffects) {
        this.sideEffects = sideEffects;
        return this;
    }

    public MedicineInfo addSideEffect(SideEffect sideEffect) {
        this.sideEffects.add(sideEffect);
        sideEffect.setMedicineInfo(this);
        return this;
    }

    public MedicineInfo removeSideEffect(SideEffect sideEffect) {
        this.sideEffects.remove(sideEffect);
        sideEffect.setMedicineInfo(null);
        return this;
    }

    public void setSideEffects(Set<SideEffect> sideEffects) {
        this.sideEffects = sideEffects;
    }

    public Set<TimeTable> getTimeTables() {
        return timeTables;
    }

    public MedicineInfo timeTables(Set<TimeTable> timeTables) {
        this.timeTables = timeTables;
        return this;
    }

    public MedicineInfo addTimeTable(TimeTable timeTable) {
        this.timeTables.add(timeTable);
        timeTable.setMedicineInfo(this);
        return this;
    }

    public MedicineInfo removeTimeTable(TimeTable timeTable) {
        this.timeTables.remove(timeTable);
        timeTable.setMedicineInfo(null);
        return this;
    }

    public void setTimeTables(Set<TimeTable> timeTables) {
        this.timeTables = timeTables;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicineInfo)) {
            return false;
        }
        return id != null && id.equals(((MedicineInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MedicineInfo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", importantInfo='" + getImportantInfo() + "'" +
            ", usage='" + getUsage() + "'" +
            ", initialCount='" + getInitialCount() + "'" +
            ", promised='" + getPromised() + "'" +
            ", refillInfo='" + getRefillInfo() + "'" +
            ", pharmacyNotes='" + getPharmacyNotes() + "'" +
            "}";
    }
}
