package com.redspark.nycl.persistence;

import com.redspark.nycl.domain.SeasonConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SeasonConfigurationRepository extends MongoRepository<SeasonConfiguration, String> {

  SeasonConfiguration findByTag(String tag);
}
