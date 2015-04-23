package hu.karaszi.ec.centralunit.data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class NamedEntity {
	@Id
	@GeneratedValue
	protected long id;
	@Column(unique=true)
	protected String name;

	public long getId() {
		return id;
	}

	protected void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
