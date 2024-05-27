import lombok.SneakyThrows;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class MainTest {

    @Test
    @Timeout(22)
    @SneakyThrows
    @Disabled
    void whenAssertingTimeout() {
        Main.main(ArrayUtils.EMPTY_STRING_ARRAY);
    }
}
