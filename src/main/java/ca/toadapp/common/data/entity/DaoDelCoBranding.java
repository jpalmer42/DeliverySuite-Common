package ca.toadapp.common.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "deliveryCompanyBranding")
public class DaoDelCoBranding extends BaseEntity {

	private String fontName;
	private String textColour;
	private String fgColour;
	private String bgColour;
	
	private String urlLogo;

	private String uriWeb;
	private String uriMenu;
}
