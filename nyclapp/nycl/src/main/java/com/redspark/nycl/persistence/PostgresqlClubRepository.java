package com.redspark.nycl.persistence;

import com.redspark.nycl.domain.Club;
import org.springframework.data.repository.CrudRepository;

public interface PostgresqlClubRepository extends CrudRepository<Club, String> {

  Club findClubByClubName(String clubName);

  Club findByMainContactUsername(String contactName);

}
