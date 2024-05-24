<<<<<<< HEAD
package com.smart_devices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart_devices.model.Storage;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Integer> {

}
=======
package com.smart_devices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_devices.model.Storage;

public interface StorageRepository  extends JpaRepository<Storage, Integer>{

}
>>>>>>> branch 'master' of https://github.com/HuyTinh/smart_devices_project.git
