package ca.toadapp.common.data;

import java.util.ArrayList;
import java.util.Collection;

import ca.toadapp.common.data.enumeration.AgentRoles;
import jakarta.persistence.AttributeConverter;

public class ConverterAgentRolesCollection implements AttributeConverter<Collection<AgentRoles>, String> {
	private static final String SPLIT_CHAR = ";";

	@Override
	public String convertToDatabaseColumn( Collection<AgentRoles> attribute ) {
		StringBuilder response = new StringBuilder();
		for( AgentRoles val : attribute ) {
			response.append( SPLIT_CHAR );
			response.append( val.name() );
		}
		response.deleteCharAt( 0 );
		return response.toString();
	}

	@Override
	public Collection<AgentRoles> convertToEntityAttribute( String dbData ) {
		String[] listOfStrings = dbData.split( SPLIT_CHAR );
		Collection<AgentRoles> response = new ArrayList<>();
		for( String val : listOfStrings ) {
			response.add( AgentRoles.valueOf( val ) );
		}
		return response;
	}
}
