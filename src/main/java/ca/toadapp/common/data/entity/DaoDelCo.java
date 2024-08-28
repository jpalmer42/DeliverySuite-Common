package ca.toadapp.common.data.entity;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ca.toadapp.common.data.ConverterLongCollection;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "deliveryCompanies")
public class DaoDelCo extends BaseEntity {

	@Column(unique = true)
	private String companyCode; // Used as part of the agent signon.

	private String name;
	private String address;
	private String phone;

	@Column(nullable= false, columnDefinition = "bool default 'false'")
	private Boolean hasDeliveryOnDemand = false;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dispatcherId")
	private DaoAgent dispatcher;

	@Column(name = "dispatcherId", updatable = false, insertable = false)
	private Long dispatcherId;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "delCoId")
	private Collection<DaoDelCoLocation> serviceAreas;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "delCoId")
	private Collection<DaoDelCoHour> hours;

	@Convert(converter = ConverterLongCollection.class)
	private Collection<Long> deliveryTypes;

}
