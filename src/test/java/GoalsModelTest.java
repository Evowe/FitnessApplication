import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import fitness.app.Metrics.Goals.GoalsModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GoalsModelTest {


    @Test
    void testVerifyDistance() {
        String error = GoalsModel.verifyDistance(1.0);
        if(error != null)
        {
            Assertions.fail(error);
        }
    }

    @Test
    void testVerifyDistanceFail() {
        String error = GoalsModel.verifyDistance(-1.0);
        if(error == null)
            Assertions.fail(error);
    }
}
