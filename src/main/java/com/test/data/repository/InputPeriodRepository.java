package com.test.data.repository;

import com.test.data.entity.InputPeriod;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "period", path = "period")
public interface InputPeriodRepository extends PagingAndSortingRepository<InputPeriod, String> {

}
