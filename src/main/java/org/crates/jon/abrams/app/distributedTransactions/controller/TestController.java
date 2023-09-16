package org.crates.jon.abrams.app.distributedTransactions.controller;

import org.crates.jon.abrams.app.distributedTransactions.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jon
 * @date 2023/9/17
 */

@RestController
@RequestMapping("/app/v1")
public class TestController {

    final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping(value = "/import")
    public void imports() {
        testService.getImport();
    }

}
