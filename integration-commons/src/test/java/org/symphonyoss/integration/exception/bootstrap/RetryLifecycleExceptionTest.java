package org.symphonyoss.integration.exception.bootstrap;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link RetryLifecycleException}.
 * Created by crepache on 23/06/17.
 */
public class RetryLifecycleExceptionTest {

    @Test
    public void testRetryLifecycleException(){
        Throwable cause = new Throwable("cause");
        RetryLifecycleException exception = new RetryLifecycleException("message",cause);
        String resultMessage = exception.getMessage();
        String expectedMessage = "message";
        Throwable resultCause = exception.getCause();

        Assert.assertEquals(expectedMessage,resultMessage);
        Assert.assertEquals(cause,resultCause);
    }
}
