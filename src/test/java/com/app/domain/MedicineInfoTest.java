package com.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.web.rest.TestUtil;

public class MedicineInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicineInfo.class);
        MedicineInfo medicineInfo1 = new MedicineInfo();
        medicineInfo1.setId(1L);
        MedicineInfo medicineInfo2 = new MedicineInfo();
        medicineInfo2.setId(medicineInfo1.getId());
        assertThat(medicineInfo1).isEqualTo(medicineInfo2);
        medicineInfo2.setId(2L);
        assertThat(medicineInfo1).isNotEqualTo(medicineInfo2);
        medicineInfo1.setId(null);
        assertThat(medicineInfo1).isNotEqualTo(medicineInfo2);
    }
}
