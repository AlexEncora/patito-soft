package com.patitosoft.encora.patitosoftapp.repository;


import com.patitosoft.encora.patitosoftapp.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path = "country")
public interface CountryRepository extends JpaRepository<Country, String> {
}
