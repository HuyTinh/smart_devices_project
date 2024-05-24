<<<<<<< HEAD
package com.smart_devices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart_devices.model.OperatingSystem;

@Repository
public interface OperatingSystemRepository extends JpaRepository<OperatingSystem, Integer> {

}
=======
package com.smart_devices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_devices.model.OperatingSystem;

public interface OperatingSystemRepository extends JpaRepository<OperatingSystem, Integer> {

}
>>>>>>> branch 'master' of https://github.com/HuyTinh/smart_devices_project.git
