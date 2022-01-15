package net.javaguides.springboot.model;

import lombok.Data;
import net.javaguides.springboot.enums.RolesName;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "role")
@Data
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private RolesName name;


	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private List<User> users;

	public Role() {
		
	}
	
	public Role(RolesName name) {
		super();
		this.name = name;
	}
	

}
