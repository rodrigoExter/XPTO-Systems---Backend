package com.cidades.features.cadastro.cidade;

import java.io.IOException;
import java.util.List;

import com.cidades.base.exception.ServiceException;
import com.cidades.base.service.AbstractService;

public interface CidadeService extends AbstractService<Long, Cidade>{
	
	Boolean importCidades(String diretorio) throws ServiceException, IOException;
	
	List<Cidade> getCitiesCapital();
	
	List<Object[]> getMaxAndMinCitiesOfUf();
	
	List<Object[]> getCitiesForUF();
	
	Integer getCountRecords();
	
	List<CidadeDTO> findCitiesForUf(String uf);
}
