package org.crates.jon.abrams.app.distributedTransactions.service.impl;

import org.crates.jon.abrams.app.distributedTransactions.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @author jon
 * @date 2023/9/17
 */

@Service
public class TestServiceImpl implements TestService {


    @Override
    public String getImport() {
        return "hello Mock";
    }
}
