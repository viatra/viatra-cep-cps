package hu.karaszi.ec.centralunit.data.persistence;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import hu.karaszi.ec.centralunit.data.dto.management.UnitDTO;

@SuppressWarnings("serial")
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
public class Unit extends NamedEntity {
	private String unit;

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public UnitDTO toDTO(){
		UnitDTO dto = new UnitDTO();
		dto.name = name;
		dto.unit = unit;
		return dto;
	}
}
