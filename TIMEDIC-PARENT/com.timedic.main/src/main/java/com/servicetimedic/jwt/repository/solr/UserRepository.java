package com.servicetimedic.jwt.repository.solr;

import org.springframework.data.solr.repository.SolrCrudRepository;
import com.servicetimedic.jwt.domain.solr.User;

public interface UserRepository extends SolrCrudRepository<User, String> {
	
	User findByUsername (String username);	
	
	void deleteById(Long id);
}
