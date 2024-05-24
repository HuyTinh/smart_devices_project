<<<<<<< HEAD
package com.smart_devices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart_devices.model.Processor;

@Repository
public interface ProcessorRepository extends JpaRepository<Processor, Integer> {

}
=======
package com.smart_devices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_devices.model.Processor;

public interface ProcessorRepository extends JpaRepository<Processor, Integer> {

}
>>>>>>> branch 'master' of https://github.com/HuyTinh/smart_devices_project.git
