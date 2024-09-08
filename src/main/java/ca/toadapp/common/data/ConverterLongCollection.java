package ca.toadapp.common.data;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ConverterLongCollection implements AttributeConverter<Collection<Long>, String> {
	private static final String SPLIT_CHAR = ";";

	@Override
	public String convertToDatabaseColumn( Collection<Long> attribute ) {
		StringBuilder response = new StringBuilder();
		for( Long val : attribute ) {
			response.append( SPLIT_CHAR );
			response.append( val.toString() );
		}
		response.deleteCharAt( 0 );
		return response.toString();
	}

	@Override
	public Collection<Long> convertToEntityAttribute( String dbData ) {
		String[] listOfStrings = dbData.split( SPLIT_CHAR );
		Collection<Long> response = new ArrayList<>();
		for( String val : listOfStrings ) {
			response.add( Long.parseLong( val ) );
		}
		return response;
	}
}
