package br.com.bossini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bossini.model.beans.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{

}
