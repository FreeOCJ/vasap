package ch.jesc.vasap.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User extends BaseEntity {

	private String email;
	private String password;
	private String nom;
	private String prenom;
	private Boolean admin;

	private List<Dossier> dossiers;


	@Column(name = "EMAIL", nullable = false, unique = true)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PASSWORD", nullable = false, unique = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "NOM", nullable = false, unique = false)
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	@Column(name = "PRENOM", nullable = false, unique = false)
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Column(name = "IS_ADMIN", nullable = false, unique = false)
	public Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	@OneToMany(mappedBy = "owner")
	public List<Dossier> getDossiers() {
		return dossiers;
	}
	public void setDossiers(List<Dossier> dossiers) {
		this.dossiers = dossiers;
	}


//	public void doEager() {
//		super.doEager();
//		eagerLoad(getDossiers());
//	}

	@Override
	public String toString() {
		return email;
	}
}
