package ch.jesc.vasap.core.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.util.Date;
import java.util.List;


/**
 * Aide au mapping
 *
 *
 @Column(name = "bla")

 @Column(name = "...")
 @Type(type = "ch.vd.registre.base.hibernate.type.RegDateUserType")

 @ManyToOne
 @JoinColumn(name = "..._FK")
 public Party getParty() {
 // begin-user-code
 return party;
 // end-user-code
 }
 @OneToMany(mappedBy = "...")
 public List<Contact> getContacts() {
 // begin-user-code
 return contacts;
 // end-user-code
 }

 @Entity
 @Table(name = "PP_...")
 @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
 @DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)

 *
 */



@MappedSuperclass
public abstract class BaseEntity {

	private Long id;
	private Date creationDate = new Date();
	private Integer optLock = 1;



	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "baseEntitySeqGen")
	@SequenceGenerator(name = "baseEntitySeqGen", sequenceName = "MAIN_SEQ")
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	public void setId(Long aId) {
		id = aId;
	}





	@Version
	@Column(name = "OPTLOCK")
	public Integer getOptLock() {
		return optLock;
	}
	public void setOptLock(Integer aOptLock) {
		optLock = aOptLock;
	}



	@Column(name = "CDATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date aCreationDate) {
		creationDate = aCreationDate;
	}

	protected <T extends BaseEntity> void eagerLoad(List<T> list) {
		if (list != null) {
			for (BaseEntity be : list) {
				eagerLoad(be);
			}
		}
	}

	protected <T extends BaseEntity> void eagerLoad(T be) {
		if (be != null) {
			be.eagerLoad();
		}
	}

	@Transient
	private transient boolean isEagerLoaded = false;
	public final void eagerLoad() {
		if (!isEagerLoaded) {
			isEagerLoaded = true;
			doEager();
		}
	}

	protected void doEager() {
		// Rien pour BE
	}

}
