package com.chisw.testtask.helper;

import com.chisw.testtask.rest.dto.NodeDto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AssertUtils {

    private AssertUtils() {

    }

    public static void assertVertexDtoEquals(@NotNull NodeDto expected, @NotNull NodeDto actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getParentId(), actual.getParentId());

        if (!assertAndIsBothNull(expected.getChildrenIds(), actual.getChildrenIds())) {
            assertArrayEquals(expected.getChildrenIds().toArray(), actual.getChildrenIds().toArray());
        }
    }

    private static boolean assertAndIsBothNull(Object expected, Object actual) {
        if (Objects.isNull(expected)) {
            if (Objects.nonNull(actual)) {
                fail();
            }
            return true;
        } else {
            if (Objects.isNull(actual)) {
                fail();
            }
            return false;
        }
    }
}
