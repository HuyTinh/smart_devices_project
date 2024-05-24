<<<<<<< HEAD
package com.smart_devices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart_devices.model.Ram;

@Repository
public interface RamRepository extends JpaRepository<Ram, Integer> {

}
=======
package com.smart_devices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_devices.model.Ram;

public interface RamRepository extends JpaRepository<Ram, Integer> {

}
>>>>>>> branch 'master' of https://github.com/HuyTinh/smart_devices_project.git
