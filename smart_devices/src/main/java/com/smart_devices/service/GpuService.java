<<<<<<< HEAD
package com.smart_devices.service;

import com.smart_devices.model.Gpu;

public interface GpuService {
	 Gpu findById(int id);
}
=======
package com.smart_devices.service;

import java.util.List;

import com.smart_devices.model.Gpu;

public interface GpuService {

	void deleteAllById(List<Integer> ids);

	Gpu getById(Integer id);

	void deleteById(Integer id);

	List<Gpu> findAll();

	Gpu save(Gpu entity);

}
>>>>>>> branch 'master' of https://github.com/HuyTinh/smart_devices_project.git
