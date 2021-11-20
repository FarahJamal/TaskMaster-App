package com.example.asac_test.lambda;


import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

public interface MyInterface {

        /**
         * Invoke the Lambda function "AndroidBackendLambdaFunction".
         * The function name is the method name.
         */
        @LambdaFunction
        ResponseClass AndroidBackendLambdaFunction2(RequestClass request);

    }

