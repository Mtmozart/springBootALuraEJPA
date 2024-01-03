package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeDoAtor, Double avaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);

    List<Serie> findByAvaliacaoGreaterThanEqualAndTotalTemporadasLessThanEqual(Double avaliacao, Integer temporada);

    /*Como passando query direto, posso passar também o JPQL que está como no parâmetro abaixo*/
    @Query("SELECT s from Serie s WHERE s.totalTemporadas <= :temporada AND s.avaliacao >= :avaliacao")
    List<Serie> seriesPorTemporadaEAvaliacao(Double avaliacao, Integer temporada);

    @Query("SELECT e from Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio")
    List<Episodio> epiosdiosPorTrecho(String trechoEpisodio);

   //@Query("SELECT e from Serie s JOIN s.episodios e WHERE s = : serie ORDER BY e.avaliacao DESC LIMIT 5")
   @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> topEpisodiosPorSerie(Serie serie);
}
