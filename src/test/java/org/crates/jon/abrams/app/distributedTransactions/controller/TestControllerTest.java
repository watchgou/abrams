package org.crates.jon.abrams.app.distributedTransactions.controller;

import org.crates.jon.abrams.app.distributedTransactions.service.TestService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;


@SpringBootTest
class TestControllerTest {

    @Autowired
    private TestController testController;

    @MockBean
    private TestService testService;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        //when(testService.getImport()).thenReturn("Hello, Mock");
        when(testService.getInfo(Mockito.any())).thenReturn("hehehe");
        testController.imports();
    }


}