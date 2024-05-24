<<<<<<< HEAD
package com.smart_devices.service;

import com.smart_devices.model.OperatingSystem;

public interface OperatingSystemService {
	OperatingSystem findById(int id);
}
=======
package com.smart_devices.service;

import java.util.List;

import com.smart_devices.model.OperatingSystem;

public interface OperatingSystemService {

	void deleteAllById(List<Integer> ids);

	OperatingSystem getById(Integer id);

	void deleteById(Integer id);

	List<OperatingSystem> findAll();

	OperatingSystem save(OperatingSystem entity);

}
>>>>>>> branch 'master' of https://github.com/HuyTinh/smart_devices_project.git
