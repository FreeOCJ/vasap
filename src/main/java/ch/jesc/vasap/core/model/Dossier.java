package ch.jesc.vasap.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "DOSSIER")
public class Dossier extends BaseEntity {

	private User owner;
	private Date from;
	private String customer;
	private String description;


	@Column(name = "CUSTOMER", nullable = false)
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	@Column(name = "DESCR", nullable = false)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn(name = "OWNER_FK", nullable = false, unique = false)
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}



	@Column(name = "DATE_FROM", nullable = false)
	@Temporal(TemporalType.DATE)
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}






	@Override
	public void doEager() {
		super.doEager();
		eagerLoad(getOwner());
	}

	@Override
	public String toString() {
		String str = owner + " ("+from+")";
		return str;
	}

}
