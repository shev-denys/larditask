package spring.boot.web.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
//import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import spring.boot.web.configuration.DataSourceConfig;
import spring.boot.web.entities.Address;
import spring.boot.web.entities.User;

public abstract class FileOperator {
	protected Logger logger = Logger.getLogger(FileOperator.class);
	@Autowired
	DataSourceConfig datasourceConfig;

	private Map<Long,User> users = new HashMap<>();

	private ObjectMapper mapper = new ObjectMapper();

	protected Map<Long,User> getUsers() {
		return users;
	}

	protected void setUsers(Map<Long,User> users) {
		this.users = users;
	}

	protected Map<Long,User> readData() {
		Set<User> userToRead = null;
		File file = new File(datasourceConfig.getFileDbPath());
		if (!file.exists()) {
			return null;
		} else {
			try (FileInputStream fis = new FileInputStream(file);) {
				userToRead = mapper.readValue(fis,
						mapper.getTypeFactory().constructCollectionType(Set.class, User.class));

				userToRead.stream().forEach(user -> {
					if (User.getCount() < user.getId())
						User.setCount(user.getId());
					if (user.getAddresses() != null)
						user.getAddresses().stream().forEach(address -> {
							address.setUser(user);
							if (Address.getCount() < address.getId())
								Address.setCount(address.getId());
						});
				});
				users.clear();
				userToRead.forEach(user->{users.put(user.getId(), user);});
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			return users;
		}
	}

	protected void writeData() {
		File file = new File(datasourceConfig.getFileDbPath());
		logger.info("Data writed to" + file.getAbsolutePath());
		try (FileOutputStream fos = new FileOutputStream(file)) {
			mapper.writeValue(fos, users.values());

		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
