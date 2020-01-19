package com.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.web.rest.TestUtil;

public class SideEffectTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SideEffect.class);
        SideEffect sideEffect1 = new SideEffect();
        sideEffect1.setId(1L);
        SideEffect sideEffect2 = new SideEffect();
        sideEffect2.setId(sideEffect1.getId());
        assertThat(sideEffect1).isEqualTo(sideEffect2);
        sideEffect2.setId(2L);
        assertThat(sideEffect1).isNotEqualTo(sideEffect2);
        sideEffect1.setId(null);
        assertThat(sideEffect1).isNotEqualTo(sideEffect2);
    }
}
