package com.cidades.features.cadastro.cidade.impl;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.cidades.base.exception.ServiceException;
import com.cidades.base.service.AbstractServiceImpl;
import com.cidades.features.cadastro.cidade.Cidade;
import com.cidades.features.cadastro.cidade.CidadeDTO;
import com.cidades.features.cadastro.cidade.CidadeRepository;
import com.cidades.features.cadastro.cidade.CidadeService;

@Service
@Validated
public class CidadeServiceImpl extends AbstractServiceImpl<Long, Cidade> implements CidadeService{
	
	@Autowired
	private CidadeRepository repository;
	
	private CidadeService service;
	
	private static ModelMapper mapper = new ModelMapper();
	
	@Override
	public Boolean importCidades(String diretorio_csv) throws ServiceException, IOException {

		Boolean retorno = false;
		if(diretorio_csv.isEmpty()) {
			diretorio_csv = "/cidadelist.csv";
		}
		Reader reader = Files.newBufferedReader(Paths.get(diretorio_csv));
		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(',') 
				.withFirstRecordAsHeader()
				.withIgnoreHeaderCase()
                .withTrim());

		for (CSVRecord csvRecord : csvParser) {
			int ibgeId = Integer.valueOf(csvRecord.get("ibge_id"));
			String uf = csvRecord.get("uf");
			String name = csvRecord.get("name");
			String capital = csvRecord.get("capital");

			System.out.println("IbgeId: " + ibgeId);
			System.out.println("UF: " + uf);
			System.out.println("Name: " + name);
			System.out.println("Capital: " + capital);
			System.out.println("--------------------------");
			
			Cidade cidade = new Cidade();
			cidade.setIbgeId(Integer.valueOf(csvRecord.get("ibge_id")));
			cidade.setUf(csvRecord.get("uf"));
			cidade.setName(csvRecord.get("name"));
			if (csvRecord.get("capital").equals("true")) {
				cidade.setCapital(true);				
			} else {
				cidade.setCapital(false);
			}
			cidade.setLon(csvRecord.get("lon"));
			cidade.setLat(csvRecord.get("lat"));
			cidade.setNoAccents(csvRecord.get("no_accents"));
			cidade.setAlternativeNames(csvRecord.get("alternative_names"));
			cidade.setMesoregion(csvRecord.get("mesoregion"));
			cidade.setMicroregion(csvRecord.get("microregion"));

			repository.save(cidade);
			retorno = true;
		}
		
		csvParser.close();
		
		return retorno;

	}

	@Override
	public List<Cidade> getCitiesCapital() {
		return this.repository.getCitiesCapital();
	}
	
	@Override
	public List<Object[]> getCitiesForUF() {
		return this.repository.getCitiesForUF();
	}
		
	@Override
	public List<Object[]> getMaxAndMinCitiesOfUf() {
		return this.repository.getMaxAndMinCitiesOfUf();
	}
	
	@Override
	public Integer getCountRecords() {
		return this.repository.getCountRecords();
	}
	
	@Override
	public List<CidadeDTO> findCitiesForUf(String uf) {
		if (uf == null || uf.isEmpty()) {
			return Collections.emptyList();
		}
		List<Cidade> cidade = this.repository.findCitiesForUf(uf.toUpperCase());
		return this.convertToDto(cidade);
	}
	
	private List<CidadeDTO> convertToDto(List<Cidade> lista) {
		if (lista == null)
			return new ArrayList<>();
		if (lista.isEmpty())
			return new ArrayList<>();
		return lista.stream().map(CidadeServiceImpl::toDto).collect(Collectors.toList());
	}

	private static CidadeDTO toDto(Cidade cidade) {
		return cidade == null ? null : mapper.map(cidade, CidadeDTO.class);
	}
}
