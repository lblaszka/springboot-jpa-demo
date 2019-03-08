package com.github.lblaszka.springbootjpademo.service;

import com.github.lblaszka.springbootjpademo.domain.TestDomain;
import org.springframework.stereotype.Component;

@Component
public class TestDomainValidator implements DomainValidator<TestDomain>
{
    @Override
    public boolean toAdd( TestDomain testDomain )
    {
        return false;
    }


    @Override
    public boolean toUpdate( TestDomain testDomain )
    {
        return false;
    }
}
