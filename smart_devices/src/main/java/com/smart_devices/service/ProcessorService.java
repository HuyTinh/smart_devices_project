<<<<<<< HEAD
package com.smart_devices.service;

import com.smart_devices.model.Processor;

public interface ProcessorService {
	Processor findById(int id);
}
=======
package com.smart_devices.service;

import java.util.List;

import com.smart_devices.model.Processor;

public interface ProcessorService {

	void deleteAllById(List<Integer> ids);

	Processor getById(Integer id);

	void deleteById(Integer id);

	List<Processor> findAll();

	Processor save(Processor entity);

}
>>>>>>> branch 'master' of https://github.com/HuyTinh/smart_devices_project.git
