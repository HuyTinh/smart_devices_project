package com.smart_devices.repository;

import com.smart_devices.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT u FROM User u " +
	           "WHERE (:keyword IS NULL OR " +
	           "LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(u.phone) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(u.address) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "CAST(u.birthDay AS string) LIKE :keyword OR " +
	           "CAST(u.id AS string) LIKE :keyword OR " +
	           "LOWER(CAST(u.gender AS string)) LIKE LOWER(CONCAT('%', :keyword, '%')))")
	Page<User> searchUsers(@Param("keyword") String keyword, Pageable pageable);
	User findByEmail(String email);
	
	Optional<User> findByEmailAndProviderId(String email, String providerId);

}
