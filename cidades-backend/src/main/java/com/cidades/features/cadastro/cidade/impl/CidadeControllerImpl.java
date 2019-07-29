package com.cidades.features.cadastro.cidade.impl;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cidades.base.controller.AbstractControllerImpl;
import com.cidades.base.exception.ServiceException;
import com.cidades.features.cadastro.cidade.Cidade;
import com.cidades.features.cadastro.cidade.CidadeController;
import com.cidades.features.cadastro.cidade.CidadeDTO;
import com.cidades.features.cadastro.cidade.CidadeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/v1" + "/cidade")
public class CidadeControllerImpl extends AbstractControllerImpl<Long, Cidade, CidadeDTO> implements CidadeController {
	
	private static final Logger logger = LogManager.getLogger(CidadeControllerImpl.class);
	
	@Autowired
	private CidadeService service;
	
	@RequestMapping(value = "/importar", method = RequestMethod.POST, produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success", response = Boolean.class),
		@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 404, message = "Not found") })
	@ResponseBody
	@ApiOperation(value = "Importa as Cidades através de um arquivo CSV, informando seu diretório.")
	public Boolean importCidades(@RequestParam(value = "diretorio") String diretorio) throws ServiceException, IOException {
		try {
			return service.importCidades(diretorio);
		} catch (ServiceException e) {
			logger.error(e);
			throw new ServiceException("Falha ao tentar ao tentar importar o arquivo CSV.");
		}
	}
	
	@RequestMapping(value = "/citiesCapital", method = RequestMethod.GET, produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success", responseContainer = "List"),
		@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 404, message = "Not found") })
	@ResponseBody
	@ApiOperation(value = "Retorna lista das Cidades que possuem capital, ordenadas pelo nome.")
	public List<Cidade> getCitiesCapital() throws ServiceException, IOException {
		return service.getCitiesCapital();
	}
	
	@RequestMapping(value = "/citiesCapital", method = RequestMethod.GET, produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success", responseContainer = "List"),
		@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 404, message = "Not found") })
	@ResponseBody
	@ApiOperation(value = "Retorna os estados com maior e menos numero de cidades.")
	public List<Object[]> getMaxAndMinCitiesOfUf() throws ServiceException, IOException {
		return service.getMaxAndMinCitiesOfUf();
	}
	
	@RequestMapping(value = "/citiesCapital", method = RequestMethod.GET, produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success", responseContainer = "List"),
		@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 404, message = "Not found") })
	@ResponseBody
	@ApiOperation(value = "Retorna a quantidade de cidades por estado.")
	public List<Object[]> getCitiesForUF() throws ServiceException, IOException {
		return service.getCitiesForUF();
	}
	
	@RequestMapping(value = "/countRecords", method = RequestMethod.GET, produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success", response = Integer.class),
		@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 404, message = "Not found") })
	@ResponseBody
	@ApiOperation(value = "Retorna a quantidade total de cidades.")
	public Integer getCountRecords() throws ServiceException, IOException {
		return service.getCountRecords();
	}
	
	@RequestMapping(value = "/findCitiesForUf", method = RequestMethod.GET, produces = "application/json")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success", response = CidadeDTO.class, responseContainer = "List"),
		@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 404, message = "Not found") })
	@ResponseBody
	@ApiOperation(value = "Retorna o nome das Cidades baseado em um estado selecionado.")
	public ResponseEntity<List<CidadeDTO>> findCitiesForUf(@RequestParam(value = "uf") String uf) throws ServiceException, IOException {
		return ResponseEntity.ok(service.findCitiesForUf(uf));
	}
	
}
