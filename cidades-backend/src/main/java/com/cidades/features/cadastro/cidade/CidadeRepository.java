package com.cidades.features.cadastro.cidade;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
	@Query(value = "select c.noAccents as cidade from Cidade c where c.capital = true order by name asc")
	public List<Cidade> getCitiesCapital();
	
	@Query(value = "select uf, qtdCidades from (select a.uf , count(1) as qtdCidades from cidade a group by a.uf order by 2 desc) where rownum < 2 "
			+ " union "
			+ "select uf, qtdCidades from (select a.uf , count(1) as qtdCidades from cidade a group by a.uf order by 2 asc) where rownum  < 2")
	List<Object[]> getMaxAndMinCitiesOfUf();
	
	@Query(value = "select count(1) as qtdCidades, a.uf from cidade a group by a.uf order by a.uf desc")
	List<Object[]> getCitiesForUF();
	
	@Query(value = "select count(1) as qtdRecords from cidade")
	Integer getCountRecords();
	
	@Query(value = "select c.name from cidade c where c.uf = :uf order by c.name")
	public List<Cidade> findCitiesForUf(@Param("uf") String uf);
}
