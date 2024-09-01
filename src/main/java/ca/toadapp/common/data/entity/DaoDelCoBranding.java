package ca.toadapp.common.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "deliveryCompanyBranding")
public class DaoDelCoBranding extends BaseEntity {

	private String fontName;
	private String textColor;
	private String fgcolor;
	private String bgColor;
	
	private String uriWeb;
	private String uriMenu;
}
