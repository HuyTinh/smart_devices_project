<<<<<<< HEAD
package com.smart_devices.service;

import com.smart_devices.model.Ram;

public interface RamService {
	Ram findById(int id);
}
=======
package com.smart_devices.service;

import java.util.List;

import com.smart_devices.model.Ram;

public interface RamService {

	void deleteAllById(List<Integer> ids);

	Ram getById(Integer id);

	void deleteById(Integer id);

	List<Ram> findAll();

	Ram save(Ram entity);

}
>>>>>>> branch 'master' of https://github.com/HuyTinh/smart_devices_project.git
