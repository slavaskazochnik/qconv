package by.parfen.disptaxi.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Street extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	@NotNull
	@Size(max = 50)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = City.class)
	private City city;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Street [id=" + id + ", name=" + name + ", city=" + city + "]";
	}

}
