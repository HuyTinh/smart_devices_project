<<<<<<< HEAD
package com.smart_devices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart_devices.model.Gpu;

@Repository
public interface GpuRepository extends JpaRepository<Gpu, Integer>{

}
=======
package com.smart_devices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_devices.model.Gpu;

public interface GpuRepository extends JpaRepository<Gpu, Integer>{

}
>>>>>>> branch 'master' of https://github.com/HuyTinh/smart_devices_project.git
