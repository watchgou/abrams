package org.crates.jon.abrams.app;

import java.util.concurrent.StructuredTaskScope;

/**
 * @author jon
 * @date 2023/9/17
 */

public class WebMvcConfig {


    public static void main(String[] args) throws Exception {

        var scope = new StructuredTaskScope.ShutdownOnFailure();

        StructuredTaskScope.Subtask<String> fork = scope.fork(() -> {
            return "LSLLSLS";
        });

        scope.join();
        System.out.printf("", fork.get());
    }

}
