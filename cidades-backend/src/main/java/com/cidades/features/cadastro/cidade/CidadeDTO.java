package com.cidades.features.cadastro.cidade;

import java.math.BigDecimal;

import com.cidades.base.dto.AbstractDTO;

public class CidadeDTO implements AbstractDTO<Long, Cidade>{
	
	private Long id;

	private Integer ibgeId;

	private String uf;

	private String name;

	private String capital;

	private BigDecimal lon;

	private BigDecimal lat;

	private String noAccents;

	private String alternativeNames;

	private String microregion;

	private String mesoregion;
	
	public CidadeDTO() { }
	
	public CidadeDTO(Long id) {
		this.setId(id);
	}
	
	public CidadeDTO(Long id, Integer ibgeId, String uf, String name,
					 String capital, BigDecimal lon, BigDecimal lat,
					 String noAccents, String alternativeNames, 
					 String microregion, String mesoregion) {
		this.setId(id);
		this.setIbgeId(ibgeId);
		this.setUf(uf);
		this.setName(name);
		this.setCapital(capital);
		this.setLon(lon);
		this.setLat(lat);
		this.setNoAccents(noAccents);
		this.setAlternativeNames(alternativeNames);
		this.setMicroregion(microregion);
		this.setMesoregion(mesoregion);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIbgeId() {
		return ibgeId;
	}

	public void setIbgeId(Integer ibgeId) {
		this.ibgeId = ibgeId;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public BigDecimal getLon() {
		return lon;
	}

	public void setLon(BigDecimal lon) {
		this.lon = lon;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public String getNoAccents() {
		return noAccents;
	}

	public void setNoAccents(String noAccents) {
		this.noAccents = noAccents;
	}

	public String getAlternativeNames() {
		return alternativeNames;
	}

	public void setAlternativeNames(String alternativeNames) {
		this.alternativeNames = alternativeNames;
	}

	public String getMicroregion() {
		return microregion;
	}

	public void setMicroregion(String microregion) {
		this.microregion = microregion;
	}

	public String getMesoregion() {
		return mesoregion;
	}

	public void setMesoregion(String mesoregion) {
		this.mesoregion = mesoregion;
	}
	
}
