package net.octacomm.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.octacomm.sample.dao.mapper.CountryMapper;
import net.octacomm.sample.domain.Country;

@Service
public class CountryImpl implements CountryService {
	
	@Autowired
	CountryMapper mapper;

	@Override
	public List<Country> getAllCountry( String name ) {
		// TODO Auto-generated method stub
		List<Country> country = mapper.getNameList( name );
		return country;
	}

}
