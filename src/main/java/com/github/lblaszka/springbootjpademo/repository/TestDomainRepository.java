package com.github.lblaszka.springbootjpademo.repository;

import com.github.lblaszka.springbootjpademo.domain.TestDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestDomainRepository extends JpaRepository<TestDomain, Long >
{
}
