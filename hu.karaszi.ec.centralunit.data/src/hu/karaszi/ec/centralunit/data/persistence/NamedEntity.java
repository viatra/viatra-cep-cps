package hu.karaszi.ec.centralunit.data.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class NamedEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="id_sequence")
	@SequenceGenerator(name = "id_sequence", allocationSize=1)
	protected long id;
	@Column(unique=true)
	protected String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
