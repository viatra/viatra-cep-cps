package hu.karaszi.ec.centralunit.dal.devices;

import hu.karaszi.ec.centralunit.dal.NamedEntity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Device extends NamedEntity {
	@ElementCollection
	@CollectionTable(name = "address_property_map", joinColumns = @JoinColumn(name = "id"))
	protected Map<String, String> addressProperties = new HashMap<String, String>();
	protected String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getAddressProperties() {
		return addressProperties;
	}

	public void setAddressProperties(Map<String, String> addressProperties) {
		this.addressProperties = addressProperties;
	}
}
